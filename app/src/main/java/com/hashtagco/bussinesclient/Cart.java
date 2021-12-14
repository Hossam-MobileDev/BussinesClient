package com.hashtagco.bussinesclient;

import android.app.Dialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hashtagco.bussinesclient.Common.Common;
import com.hashtagco.bussinesclient.Database.Database;
import com.hashtagco.bussinesclient.Helper.GpsTracker;
import com.hashtagco.bussinesclient.Model.MyResponse;
import com.hashtagco.bussinesclient.Model.Notification;
import com.hashtagco.bussinesclient.Model.Order;
import com.hashtagco.bussinesclient.Model.Requests;
import com.hashtagco.bussinesclient.Model.Sender;
import com.hashtagco.bussinesclient.Model.Token;
import com.hashtagco.bussinesclient.Model.User;
import com.hashtagco.bussinesclient.Remote.ApiService;
import com.hashtagco.bussinesclient.Remote.IGoogleServicecs;
import com.hashtagco.bussinesclient.ViewHolder.CartAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cart extends AppCompatActivity  {

  /*  private static final int PAYPAL_REQUEST_CODE=9999;

    private static final int MY_PERMISSIONS_REQUEST_LOCATION =144455 ;*/

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    //public TextView textTotalPrice;
    private Button   buttonPlaceOrder , buttonReturnshop;

    //-------------------------------
    private FirebaseDatabase database;
    private DatabaseReference requestesToFirebase;
    //-------------------------------
    private List<Order> cartList=new ArrayList<Order>();
    private CartAdapter cartAdabter;
    //-----------------------------
    private Place shappingAddress;
    //---------my Location-----------

    private GpsTracker gpsTracker; //Class Get GPS  and open Location

    //Decleration
    private IGoogleServicecs mIGoogleServicecs;
    // Notification
    private ApiService mApiService;
/*
    //paypal payment
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.Paypal_Client_ID);//YOUR CLIENT ID*/
    private String address=null;
    private String comment=null;
String nameclient;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private RelativeLayout rootLayoutCart;
    //------------------------------------------------


    private static double latitude;
    private static double longitude;

    /*//Library Custom font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }*/

    //First الكلاس ده فايدته هيجلب البيانات المتخزنه فى الاس كيولايت عشان يعرضها فى الريسيكلر فيو
    //Seconed تانى وظيفه هجلب البيانات من الاس كيولايت وهبعتها لل الفيربيز
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Library Custom font
       /* CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/restaurant_font.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );*/

        setContentView(R.layout.activity_cart);

        //----------------Id-------------------------//
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView_ListCart);

        buttonPlaceOrder=(Button)findViewById(R.id.btn_cart_place_order);
//        rootLayoutCart=(RelativeLayout)findViewById(R.id.rootLayoutCart);
buttonReturnshop = (Button)findViewById(R.id.returnshoping);
buttonReturnshop.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Cart.this,Home.class);
        startActivity(intent);
    }
});


        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED )
            {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }








     /*   //Init PayPal
        Intent intent=new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);*/

        //Init Current Name Address
        mIGoogleServicecs= Common.getGoogleMapsApi();
        //Init Service Notification
        mApiService=Common.getFCMClinet();

        //Save your Location when Move to Activity --> paypal
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor=sharedPreferences.edit();

        //----------Recycler View------------------//
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //--------------------Firebase----------------//
        database=FirebaseDatabase.getInstance();
        requestesToFirebase=database.getReference("Requests");// جدول الطلبات اللى المستخدم عاوزها
        loadListFood();

        //-------------------Event------------------//
        buttonPlaceOrder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

              /*  gpsTracker = new GpsTracker(Cart.this);
                if(gpsTracker.canGetLocation())
                {
                    latitude = gpsTracker.getLatitude();
                    longitude = gpsTracker.getLongitude();
                    //---------showAlertDialog---------------------

                    if (cartList.size()>0)
                    {*/
                showAlertDialog();

                   /* }else
                    {
                        Toast.makeText(Cart.this, "Your Carts is empty", Toast.LENGTH_SHORT).show();
                    }
                    //----------------------------------


                }else{
                    gpsTracker.showSettingsAlert();
                }

            }*/
            }});
        }
      /*  //Swipe to delete
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback=new RecyclerItemTouchHeleper
                (0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);*/








    //Method Load Order from sqlite to Recyclerview
    //هتقوم  بالوظيفه الاولى وهيا ارسال الطلبات للRecyclerView  لعرضها فقط--ثم حساب السعر حسب الكميه وعرضه للمستخدم
    private void loadListFood()
    {
        cartList=new Database(this).getCarts(Common.currentUser.getPhone());// get Data From Sqlite

        cartAdabter=new CartAdapter(cartList,Cart.this,this);//Adabter
        cartAdabter.notifyDataSetChanged();
        recyclerView.setAdapter(cartAdabter);


        //Calculate Total Price
       /* int total=0;

        for (Order order:cartList)
        {
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuentity()));

            Locale locale=new Locale("ar","EG");
           NumberFormat fmt=NumberFormat.getInstance(locale);
          //  textTotalPrice.setText( fmt.format(total) + " جنيه"); //Send Price
           // textTotalPrice.setText( String.format("جنيه %s", total));
          //  String.format("LE %s", model.getPrice())
            //textTotalPrice.setText(total);
        }*/


    }

    //Method Show Dialog
    // هتظهر AlertDialog  تدخل فيها عنوانك ثم يتم تخزينها فى جدول الطلبات فى الFirebae مع الاطعمه المختاره
    private void showAlertDialog()
    {
        final Dialog dialog=new Dialog(this);
        dialog.setCancelable(false);
/*
        AlertDialog.Builder alertDailog=new AlertDialog.Builder(Cart.this);
                            alertDailog.setTitle("One More Step !");
                            alertDailog.setMessage("Enter Your Address : ");

                            alertDailog.setIcon(R.drawable.shopping_cart_black_24dp);

        LayoutInflater layoutInflater=this.getLayoutInflater();
        View viewInflater=layoutInflater.inflate(R.layout.order_adress_comment_dialog,null);
*/

        dialog.setContentView(R.layout.order_adress_comment_dialog);


        // final TextInputEditText editAdress=(TextInputEditText)viewInflater.findViewById(R.id.adress_dialog_cart);
/*
        final TextInputEditText editComment=(TextInputEditText)dialog.findViewById(R.id.comment_dialog_cart);
*/
        EditText edtname = (EditText)dialog.findViewById(R.id.nameclient);
        //Radio
        final RadioButton radioShipToAddress=(RadioButton)dialog.
                findViewById(R.id.radio_shipaddress);
     // final RadioButton radioHomeAddress=(RadioButton)dialog.findViewById(R.id.radio_home_address);
        final RadioButton radioPaymentCash=(RadioButton)dialog.findViewById(R.id.radio_payment_cashOnDelivery);
       // final RadioButton radioPaymentPaypal=(RadioButton)dialog.findViewById(R.id.radio_payment_PAYPAL);
        final RadioGroup  radioGroubPayment=(RadioGroup)dialog.findViewById(R.id.radioGroub_Payment);
        Button yesButton=(Button)dialog.findViewById(R.id.button_yes_dialog_cart);
        ImageView cancelButton=(ImageView)dialog.findViewById(R.id.button_cancel_dialog_cart);
        final TextInputEditText editAddress=(TextInputEditText)dialog.findViewById(R.id.address_dialog_cart);

        //Event Home Address
/*
        radioHomeAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {

                if (b)
                {
                    if (!TextUtils.isEmpty(Common.currentUser.getHomeAddress())
                            ||Common.currentUser.getHomeAddress()!=null)
                    {
                        address=Common.currentUser.getHomeAddress();
                        editAddress.setText(address);
                    }else
                    {
                        showHomeAddressDialog()               ;     }

                }

            }
        });
*/


        //Event Your Location Address
        radioShipToAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {

                if (b)//b == true
                {


                    if (latitude!=0 &&longitude!=0) {

                        Geocoder geocoder;
                        List<Address> addressesList = null;
                        geocoder = new Geocoder(Cart.this, Locale.getDefault());
                        try {

                            addressesList = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        address = addressesList.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
             /*       String city = addressesList.get(0).getLocality();
                    String state = addressesList.get(0).getAdminArea();
                    String country = addressesList.get(0).getCountryName();
                    String postalCode = addressesList.get(0).getPostalCode();
                    String knownName = addressesList.get(0).getFeatureName(); // Only if available else return NULL*/


                        //set this address to edit
                        editAddress.setText(address);

                /*    Toast.makeText(getApplicationContext(), "address"+address, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "city"+city, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "state"+state, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "country"+country, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "postalCode"+postalCode, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "knownName"+knownName, Toast.LENGTH_SHORT).show();*/

                    }else
                    {
                        //Toast.makeText(getApplicationContext(), " Please Try Again !", Toast.LENGTH_SHORT).show();
                    }



                }
            }
        });


        //choose Done
        yesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                //Add Check condition here
                //If user select address from place fragment ,just use it
                //If user select ship to this address , get Address from Location and user it
                //If user select Home Address , get Home Address Profile and use it
                if (!radioShipToAddress.isChecked()) {
                    //If both radio is not selected
                    if (shappingAddress != null) {
                        address = shappingAddress.getAddress().toString();

                    } else {
                       /* Toast.makeText(Cart.this,
                                "ادخل العنوان من فضلك", Toast.LENGTH_SHORT).show();*/

                        return;
                    }
                }
               /* if (address.isEmpty() || address.equals(" ")) {
                    Toast.makeText(Cart.this,
                            "Please Enter Address or select option address",
                            Toast.LENGTH_SHORT).show();


                    return;
                }*/

                //Show PayPal to Payment
                //First get Address ,Comment from AlertDialog
                //   address=shappingAddress.getAddress().toString();
               // comment = editComment.getText().toString();

                //Check payment
               /* if (!radioPaymentCash.isChecked()&&!radioPaymentPaypal.isChecked()
                       ) //If both cash and Paypal and Balance is not Chicked
                {
                    Toast.makeText(Cart.this, "Please Select Payment Option !", Toast.LENGTH_LONG).show();


                    return;
                }
                else if (radioPaymentPaypal.isChecked())
                {

                    String formatAccount = textTotalPrice.getText().toString()
                            .replace("$", "")
                            .replace(",", "");
                    //Payment method
                    Intent serviceConfig = new Intent(getApplicationContext(), PayPalService.class);
                    serviceConfig.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
                    startService(serviceConfig);


                    PayPalPayment payment = new PayPalPayment(new BigDecimal(formatAccount),//Price
                            "USD",
                            "My Awesome Item",
                            PayPalPayment.PAYMENT_INTENT_SALE);

                    Intent paymentConfig = new Intent(getApplicationContext(), PaymentActivity.class);
                    paymentConfig.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
                    paymentConfig.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
                    startActivityForResult(paymentConfig, PAYPAL_REQUEST_CODE);


                }else*/
                if (radioPaymentCash.isChecked()) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();
                    String currentdate = formatter.format(date);
                    nameclient =  edtname.getText().toString();

                    Requests requests = new Requests(currentdate, Common.currentUser.getPhone(),
                            nameclient,
                            address, //Name Place
                            //textTotalPrice.getText().toString(),
                            "0",
                            cartList//الاطعمه
                            ,
                            String.format("%s,%s", latitude, longitude)// خطوط الطول والعرض لمكان العميل //shappingAddress.getLatLng().longitude

                            ,
                            "Cash",
                            "Unpaid" //State Pay
                            , ""
                    );

                    //we will using current.Millis to Key
                    String orderNumber = String.valueOf(System.currentTimeMillis());
                    //Submit to Firebase
                    requestesToFirebase.child(orderNumber).
                            setValue(requests);
                    //بعد ما ابعت البيانات للفيربيز وتتخزن همسحها من SQlite
                    //Delete From Cart to SQlite
                    new Database(getBaseContext()).cleanCart(Common.currentUser.getPhone());
                   sendNotificationOrder(orderNumber);

                    Toast.makeText(Cart.this, "تم ارسال طلبك بنجاح ! ", Toast.LENGTH_LONG).show();
                    finish();
                }

            }

        });

        //chose No
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.dismiss();



            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id
        dialog.show();
    }
    /*  private void sendOrderStatusToUser(final String orderNumber)
        {
            final DatabaseReference referenceToken=database.getReference("Tokens");


            referenceToken.orderByKey().equalTo(Common.currentUser.getPhone())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {

                                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {
                                    Token token = postsnapshot.getValue(Token.class);
                                    Notification notification = new Notification("hossam",
                                            "your order "+orderNumber+"was updated");
                                    Sender sender = new Sender(token.getToken(),notification);




                                    mApiService.sendNotification(sender).enqueue(new Callback<MyResponse>()
                                    {
                                        @Override
                                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response)
                                        {
                                            //
                                            if (response.code()==200) {
                                                if (response.body().success == 1) {
                                                    Toast.makeText(Cart.this,
                                                            "order updated sucess ", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    Toast.makeText(Cart.this, "Failed !!! ", Toast.LENGTH_SHORT).show();

                                                }

                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<MyResponse> call, Throwable t)
                                        {
                                            Log.e("Error ", t.getMessage() );
                                        }
                                    });
                                }





                            }



                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError)
                        {

                        }
                    });


        }*/
    private void sendNotificationOrder(final String orderNumber)
    {
        final DatabaseReference referenceTokens=FirebaseDatabase.getInstance().
                getReference("Tokens");

        Query queryData =referenceTokens.orderByChild("serverToken")
                .equalTo(true);// get All node isServerToken is True


        queryData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Token serverToken=snapshot.getValue(Token.class);
                    Notification notification = new Notification(
                            "ميديكال فريست","لديك طلب جديد"+orderNumber);
                    Sender sender = new Sender(serverToken.getToken(),notification);




                    mApiService.sendNotification(sender).enqueue(new Callback<MyResponse>()
                    {
                        @Override
                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response)
                        {
                            //
                            if (response.code()==200) {
                                if (response.body().success == 1) {
                                    Toast.makeText(Cart.this, "Thank you " +
                                            ",Order Sent ", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                   // Toast.makeText(Cart.this, "Failed !!! ", Toast.LENGTH_SHORT).show();

                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<MyResponse> call, Throwable t)
                        {
                            Log.e("Error ", t.getMessage() );
                        }
                    });
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

/*
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {

        if (item.getTitle().equals(Common.DELETE))
        {
            deleteCart(item.getOrder());
        }

        return true;
    }*/

  /*  private void deleteCart(int position)
    {
        //We Will Remove item List<Order> by Position
        cartList.remove(position);

        //After That , we will DELETE ALL old data from Sqlite
        new Database(this).cleanCart(Common.currentUser.getPhone());

        //And final , we Will Update New Data From List<Order>CartList to SQlite
        for (Order item :cartList)
            new Database(this).addToCarts(item);

        //Referesh
        loadListFood();
    }*/

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {


        if (resultCode == Activity.RESULT_OK&&requestCode==PAYPAL_REQUEST_CODE){

            PaymentConfirmation confirm = data.getParcelableExtra(
                    PaymentActivity.EXTRA_RESULT_CONFIRMATION);

            if (confirm != null){//approved
                try {
                    Log.i("sampleapp", confirm.toJSONObject().toString(4));



                    // TODO: send 'confirm' to your server for verification
                    String paymentDetail=confirm.toJSONObject().toString(4);

                    JSONObject jsonObject=new JSONObject(paymentDetail);

                    //ده المودل اللى هشيل فيه الطلبات
                    Requests requests =new Requests(Common.currentUser.getPhone(),
                            Common.currentUser.getName(),
                            address,
                            textTotalPrice.getText().toString(),
                            "0",
                            cartList//الاطعمه
                            ,
                            String.format("%s,%s",shappingAddress.getLatLng().latitude,shappingAddress.getLatLng().longitude)
                            ,
                            comment
                            ,
                            "PAYPAL",
                            jsonObject.getJSONObject("response").getString("state") //State Pay
                            ,""
                    );
                    //we will using current.Millis to Key
                    String orderNumber=String.valueOf(System.currentTimeMillis());
                    //Submit to Firebase
                    requestesToFirebase.child(orderNumber).
                            setValue(requests);
                    //بعد ما ابعت البيانات للفيربيز وتتخزن همسحها من SQlite
                    //Delete From Cart to SQlite
                    new Database(getBaseContext()).cleanCart(Common.currentUser.getPhone());
                    sendNotificationOrder(orderNumber);

                    Toast.makeText(Cart.this, "Thank you ,Order Place ", Toast.LENGTH_SHORT).show();
                    finish();



                } catch (JSONException e) {
                    Log.e("sampleapp", "no confirmation data: ", e);
                    Toast.makeText(this, "no confirmation data:", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("sampleapp", "The user canceled.");
            Toast.makeText(this, "canceled", Toast.LENGTH_SHORT).show();


        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("sampleapp", "Invalid payment / config set");
            Toast.makeText(this, "Invalid payment / config set", Toast.LENGTH_SHORT).show();
        }


    }*/


    //Our last step is to cleanup in our onDestroy Paypal



//    //Swipe Delete
//    @Override
//    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position)
//    {
//        if (viewHolder instanceof CartViewHolder)
//        {
//
//            String name=((CartAdapter)recyclerView.getAdapter()).getItem(viewHolder.getAdapterPosition()).getProudactName();
//
//            final Order deleteOrder=((CartAdapter)recyclerView.getAdapter()).getItem(viewHolder.getAdapterPosition());
//            final int deleteIndex=viewHolder.getAdapterPosition();
//
//            //*****Remove Item*******//
//            cartAdabter.removeItem(deleteIndex);
//            new Database(getBaseContext()).removeFromCart(deleteOrder.getProudactID(),Common.currentUser.getPhone());
//
//            //Update textTotalPrice
//            //Calculate Total Price
//            int totalPrice=0;
//
//            List<Order> orders=new Database(getBaseContext()).getCarts(Common.currentUser.getPhone());
//
//           /* for (Order item:orders)
//            {
//                totalPrice+=(Integer.parseInt(item.getPrice()))*(Integer.parseInt(item.getQuentity()));
//            }*/
//
//            Locale locale1=new Locale("en","US");
//            NumberFormat numberFormat=NumberFormat.getCurrencyInstance(locale1);
//            //textTotalPrice.setText(numberFormat.format(totalPrice));
//
//            //*****UNDO Item*******//
//            //Make SnakeBar
//            Snackbar snackbar=Snackbar.make(rootLayoutCart,name +"removed From Cart",Snackbar.LENGTH_LONG);
//
//            snackbar.setAction("UNDO", new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View view)
//                {
//                    cartAdabter.restoreItem(deleteOrder,deleteIndex);
//                    new Database(getBaseContext()).addToCarts(deleteOrder);
//
//                    //Update textTotalPrice
//                    //Calculate Total Price
//                    int totalPrice=0;
//
//                    List<Order> orders=new Database(getBaseContext()).getCarts(Common.currentUser.getPhone());
//
//                  /*  for (Order item:orders)
//                    {
//                        totalPrice+=(Integer.parseInt(item.getPrice()))*(Integer.parseInt(item.getQuentity()));
//                    }*/
//
//                    Locale locale1=new Locale("en","US");
//                    NumberFormat numberFormat=NumberFormat.getCurrencyInstance(locale1);
//                    //textTotalPrice.setText(numberFormat.format(totalPrice));
//
//                }
//            });
//
//            snackbar.setActionTextColor(Color.GREEN);
//            snackbar.show();
//
//        }
//
//
//
//    }

    //--------------------------------------

    private void showHomeAddressDialog()
    {

        final Dialog dialog=new Dialog(this);



        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.home_address_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(false);


        //View infalteView=this.getLayoutInflater().inflate(R.layout.home_address_dialog,null);

        final TextInputEditText editHomeAddress=(TextInputEditText)dialog.findViewById(R.id.edit_home_address_dialog);
        Button btnChange=(Button)dialog.findViewById(R.id.home_address_btn_change);
        Button btnCancel=(Button)dialog.findViewById(R.id.home_address_btn_cancel);

        FirebaseDatabase.getInstance().getReference(Common.USER)
                .child(Common.currentUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {

                        User user=dataSnapshot.getValue(User.class);
                        editHomeAddress.setText(user.getHomeAddress());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {
                        // Getting Post failed, log a message
                        Log.w("Home Address", "loadPost:onCancelled", databaseError.toException());
                    }
                });


        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String address= editHomeAddress.getText().toString();

                if (!address.isEmpty()&&!address.equals(" ")&&address!=null) {

                    Common.currentUser.setHomeAddress(editHomeAddress.getText().toString());

                    FirebaseDatabase.getInstance().getReference(Common.USER)
                            .child(Common.currentUser.getPhone())
                            .setValue(Common.currentUser)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(Cart.this, "تم تحديث العنوان بنجاح !", Toast.LENGTH_LONG).show();
                                        dialog.cancel();
                                    }

                                }
                            });

                }else
                {
                    editHomeAddress.setError("Please Enter Your Address");
                }


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.cancel();
            }
        });

        dialog.show();
    }


}
