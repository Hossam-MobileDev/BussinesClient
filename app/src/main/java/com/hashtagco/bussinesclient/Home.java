package com.hashtagco.bussinesclient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
/*import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;*/
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.hashtagco.bussinesclient.Common.Common;

import com.hashtagco.bussinesclient.Model.Category;
import com.hashtagco.bussinesclient.Model.Token;
import com.hashtagco.bussinesclient.ViewHolder.AdabterHome;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.paperdb.Paper;

public class Home extends AppCompatActivity
       implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseDatabase database;
    private DatabaseReference category;
    //--------------------------------
    private TextView textFullName;
   // private CounterFab fab;
    private View headerView;
    private TextView textCountr;
    private Button btnBadgeHome;

    //-------------------------------
    private RecyclerView recyclerViewMenu;
    private RecyclerView.LayoutManager layoutManager;
    //--------Firebase UI--------//
    //private Query query;
    // private FirebaseRecyclerOptions<Category> options;
    // private FirebaseRecyclerAdapter adapter;
    //------------------
    private ProgressDialog progressDialog;
    //private SwipeRefreshLayout swipeRefresh;
    //---------------------------------------------
    private AdabterHome adabterHome;
    private List<Category> Category;
    //-----------Banner------------------
  //  private SliderLayout sliderLayout;
    private HashMap<String,String> mapImage_List;





    //Library Custom font
   /* @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @SuppressLint("ResourceAsColor")*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" الأقسام الرئيسية");

     /*   //Library Custom font
      CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/restaurant_font.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );*/

        Category =new ArrayList<>();
        //---------------------Firebase-----------------------//
        database=FirebaseDatabase.getInstance();
        category=database.getReference("Category");
        category.keepSynced(true);// Save Data offline

        //Init Paper*/
        Paper.init(this);

        //---------------------------Id----------------------------//
      //  fab = (CounterFab)findViewById(R.id.fab);

        //fab = (CounterFab) findViewById(R.id.fab);
       /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent cartIntent = new Intent(Home.this, Cart.class);
                startActivity(cartIntent);
            }
        });*/

        //fab.setCount(new Database(this).getCountCart(Common.currentUser.getPhone()));
        //fab.setColorFilter(R.color.primaryColor);
        //swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.SwipeHome_Refresh);
        recyclerViewMenu=(RecyclerView)findViewById(R.id.recycler_menu);
        progressDialog=new ProgressDialog(Home.this);
//        textCountr=(TextView)findViewById(R.id.badgeText_Home);
//        btnBadgeHome=(Button)findViewById(R.id.btn_badge_home);



        //--Load Menu--//
         recyclerViewMenu.setHasFixedSize(true);
         layoutManager=new LinearLayoutManager(this);
        recyclerViewMenu.setLayoutManager(layoutManager);
       recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));
        loadMenu();
      //  LayoutAnimationController controller= AnimationUtils.loadLayoutAnimation(recyclerViewMenu.getContext(),R.anim.animation_fall_down);
      //  recyclerViewMenu.setLayoutAnimation(controller);

        //---------------------------Event-----------------------//
    /*    btnBadgeHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cartIntent=new Intent(Home.this,Cart.class);
                startActivity(cartIntent);

            }
        });
*/
        //fab.setCount(new Database(this).getCountCart(Common.currentUser.getPhone()));
     /*  if (new Database(this).getCountCart(Common.currentUser.getPhone())==0)
        {
            textCountr.setVisibility(View.GONE); //will hide the button and space acquired by button

        }else
        {*/
        /*    textCountr.setVisibility(View.VISIBLE); //Shaw Button
            textCountr.setText(String.valueOf(new Database(this).
                    getCountCart(new User().getPhone())));
//        }

        if (Common.IsConnectedToInternet(this))
        {
            loadMenu();
        }
        else
        {
            Toast.makeText(this, "Please Check Your Connection", Toast.LENGTH_SHORT).show();
        }*/

        //  fab.setBackgroundTintList(ColorStateList.valueOf(R.color.greenW)); الخلفيه اللى ورى الا يقون

        // the colors and size of the loader.
       /* swipeRefresh.setColorSchemeResources(R.color.colorPrimaryDark,//This method will rotate
                android.R.color.holo_green_dark, //colors given to it when
                android.R.color.holo_orange_dark, //loader continues to
                android.R.color.holo_blue_dark);//refresh.
        //setSize() Method Sets The Size Of Loader
        swipeRefresh.setSize(SwipeRefreshLayout.LARGE);
        //Below Method Will set background color of Loader
        swipeRefresh.setProgressBackgroundColorSchemeResource(R.color.colorPrimaryDark);


        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh()
            {
                //set up MediaPlayer

             *//* Uri uriDefaultSound= RingtoneManager.getDefaultUri(R.raw.messengerrefreshsound);
                try {
                    MediaPlayer mp=MediaPlayer.create(getApplicationContext(),R.raw.messengerrefreshsound);// the song is a filename which i have pasted inside a folder **raw** created under the **res** folder.//
                    mp.prepare();
                    mp.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }*//*
                MediaPlayer mMediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.messengerrefreshsound);
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        onStop();
                    }
                });
                mMediaPlayer.start();


                if (Common.IsConnectedToInternet(getApplicationContext()))
                {
                    loadMenu();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please Check Your Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //Defaulet ,Load First Time
        swipeRefresh.setRefreshing(true);

        swipeRefresh.post(new Runnable() {
            @Override
            public void run()
            {

                if (Common.IsConnectedToInternet(getApplicationContext()))
                {
                    loadMenu();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please Check Your Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //--Set Name for User--//
        headerView=navigationView.getHeaderView(0);

        textFullName=(TextView)headerView.findViewById(R.id.text_Full_Name_User);

    //   textFullName.setText(Common.currentUser.getName());



        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( Home.this,
                new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken",newToken);
                updateToken(newToken);
            }
        });


        //Storage your Token app to FirebaseDatabse
       //updateToken(new);

        //setUp Slider
       // setupSlider();

    }


  /*  private void setupSlider()
    {
        sliderLayout=(SliderLayout)findViewById(R.id.slider);
        mapImage_List =new HashMap<>();

        final DatabaseReference reBanner=database.getReference("Banner");

        reBanner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Banner banner=snapshot.getValue(Banner.class);

                    //we will concat string name and id like => "PIZZA_01"
                    //and show Description ,01 for food id to click
                    mapImage_List.put(banner.getName()+"@@"+banner.getId(),banner.getImage());

                }


                for (String key:mapImage_List.keySet())
                {
                    String[]keySplit=key.split("@@"); //GET ONLY KEY, First parameter
                    String nameOfFood=keySplit[0];// name
                    String idOfFood=keySplit[1]; //id

                    //Create Slider
                    final TextSliderView textSliderView=new TextSliderView(getBaseContext());

                    textSliderView.description(nameOfFood).
                            image(mapImage_List.get(key)).
                            setScaleType(BaseSliderView.ScaleType.Fit). //center crope
                            setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider)
                        {
                            Intent intent=new Intent(Home.this,FoodDetails.class);
                            intent.putExtras(textSliderView.getBundle());
                            startActivity(intent);

                        }
                    });
                    //add Extra Bundle
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle().putString("FoodId",idOfFood); //Set Key Food
                    sliderLayout.addSlider(textSliderView);

                    //Remove event after finish
                    reBanner.removeEventListener(this);
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });


        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(5000);

    }*/


    //Update Token
    private void updateToken(String token)
    {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("Tokens");

        Token token1=new Token(token,false);

        reference.child(Common.currentUser.getPhone()).setValue(token1);
    }

/*
    //Method Load Data From FirebaseDatabse AND sen Data to RecyclerView
    private void loadMenu()
    {
        //---Using Firebase UI to populate a RecyclerView--------//
        query= FirebaseDatabase.getInstance()
                .getReference()
                .child("Category");
         // query.keepSynced(true);//Load Data OffLine
        options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(query, Category.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<Category, MenuVIewHolder>(options) {
            @Override
            public MenuVIewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.menu_item, parent, false);
                return new MenuVIewHolder(view);
            }
            @Override
            protected void onBindViewHolder(final MenuVIewHolder holder, final int position, final Category model) {
                // Bind the Chat object to the ChatHolder

                         //Send Image Name to Recyclerview
                holder.textMenuName.setText(model.getName());

                //Send Image  to Recyclerview
                Picasso.get()
                        .load(model.getImage())//Url
                      //  .networkPolicy(NetworkPolicy.OFFLINE)//تحميل الصوره Offline
                       // .placeholder(R.drawable.d)//الصوره الافتراضه اللى هتظهر لحد لما الصوره تتحمل
                        .into(holder.imageView, new Callback()
                        {
                            @Override
                            public void onSuccess() {//لو الصوره اتحملت offline بنجاح
                            }
                            @Override
                            public void onError(Exception e)
                            {//لو الصوره ماتحملتشى Offline بنجاح اعرضها Online

                                Picasso.get()
                                        .load(model.getImage())
                                        .placeholder(R.drawable.d)//الصوره الافتراضه اللى هتظهر لحد لما الصوره تتحمل
                                        .into(holder.imageView);
                            }
                        });

                  final Category clickItem=model;

                //لما المستخدم يضغط على اى صف
                 holder.setItemClickListener(new ItemClickListener() {
                     @Override
                     public void onClick(View view, int position, boolean isLongClick)
                     {
                          //Get CategoryId and send to new Activity
                         Intent foodsListIntent=new Intent(Home.this,FoodList.class);
                               foodsListIntent.putExtra("CategoryId",adapter.getRef(position).getKey());//Just Get Key Of item
                               startActivity(foodsListIntent);
                      }
                 });
            }//end OnBind
        };//end Adapter
        recyclerViewMenu.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }
*/

    private void loadMenu()
    {

        category.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                Category.clear();

                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Category Category =snapshot.
                            getValue(Category.class);
                    Category.setMenuId(snapshot.getKey());
                    Home.this.Category.add(Category);

                }

                adabterHome=new AdabterHome(Home.this, Category);

                recyclerViewMenu.setAdapter(adabterHome);
                adabterHome.notifyDataSetChanged();
              //  swipeRefresh.setRefreshing(false);
                recyclerViewMenu.getAdapter().notifyDataSetChanged();
              //  recyclerViewMenu.scheduleLayoutAnimation();
                // إذا أردنا عرض العناصر الحديثة من الأعلى (بمعنى أنه أي عنصر جديد يتم إضافته يظهر في الأعلى)
                //فقط نقوم بعكس ال List عبر الميثود
                // Collections.reverse(categories);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }


  /*   //Start Adapter
   @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    //Stop Adapter
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       /* if (item.getItemId()==R.id.menu_refersh)
        {
         loadMenu();
        }
*/
       /* if (item.getItemId()==R.id.menu_search)
        {
           // startActivity(new Intent(Home.this,SearchActivityHome.class));
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       if (id == R.id.nav_cart)
        {
            Intent intentCart=new Intent(Home.this,Cart.class);
            startActivity(intentCart);

        } /*else if (id == R.id.nav_orders)
        {
            Intent intentOrderStatus=new Intent(Home.this,OrderStatus.class);
            startActivity(intentOrderStatus);

        }*//* else if (id == R.id.nav_logout)
        {
            //Delete Remember User & Password
          //  Paper.book().destroy();

           // AccountKit.logOut();
            //LogOut
        // String   mail = Paper.book().read(Common.USER_KEY);
        //   String pass=Paper.book().read(Common.PWD_KEY);
            Intent  intentSignIn=new Intent(Home.this,SignIn.class);
         //  intentSignIn.putExtra("email",mail);
           // intentSignIn.putExtra("pass",pass);
            intentSignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intentSignIn);
        }*/
        /*else if (id==R.id.nav_change_pwd)
        {
            showChangePasswordDialog();
        }*/
       /* else if (id==R.id.nav_home_address)
        {
            showHomeAddressDialog();
        }*/else if (id==R.id.nav_settings)
        {
            Intent i = new Intent(Home.this,AboutUsActivity.class);
            startActivity(i);
        }

      /* else if (id==R.id.offers)
        {
            Intent intent=new Intent(Home.this,BannerActivity.class);
            startActivity(intent);
        }*/

       else if (id==R.id.chat)
       {
           String contact = "+20 1000740984"; // use country code with your phone number
           String url = "https://api.whatsapp.com/send?phone=" + contact;
           try {
               PackageManager pm = this.getPackageManager();
               pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
               Intent i = new Intent(Intent.ACTION_VIEW);
               i.setData(Uri.parse(url));
               startActivity(i);
           } catch (PackageManager.NameNotFoundException e) {
               Toast.makeText(Home.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
               e.printStackTrace();
           }
       }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    protected void onResume() {
        super.onResume();
         //fab.setCount(new Database(this).getCountCart(Common.currentUser.getPhone()));
        //textCountr.setText(String.valueOf(new Database(this).getCountCart(Common.currentUser.getPhone())));

//        if (new Database(this).getCountCart(Common.currentUser.getPhone())==0)
//        {
//            textCountr.setVisibility(View.GONE); //will hide the button and space acquired by button
//
//        }else
//        {
           /* textCountr.setVisibility(View.VISIBLE); //Shaw Button
            textCountr.setText(String.valueOf(new Database(this)
                    .getCountCart(Common.currentUser.getPhone())));*/
       // }

    }

    @Override
    protected void onStop() {
        super.onStop();
        //sliderLayout.stopAutoCycle();
    }


    /*private void showSettingsDialog()
    {

        final Dialog dialog=new Dialog(this);
        dialog.setCancelable(false);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.settings_news_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




        final CheckBox checkBoxNews=(CheckBox)dialog.findViewById(R.id.checkbox_news);
        ImageView btnCancelDialog=(ImageView)dialog.findViewById(R.id.btn_cancel_setting_notification);
        Button    btnOkDialog=(Button)dialog.findViewById(R.id.btn_ok_setting_notification);
        final ImageView imageNotification=(ImageView)dialog.findViewById(R.id.icon_notification_sub_unsub);


        //Add Code Remember State of CheckBox
        Paper.init(this);
        String isSubscribe=Paper.book().read("sub_new");
        if (isSubscribe==null|| TextUtils.isEmpty(isSubscribe)||isSubscribe.equals("false"))
        {
            checkBoxNews.setChecked(false);
            imageNotification.setImageResource(R.drawable.notification_unsub);

        }else
        {
            checkBoxNews.setChecked(true);
            imageNotification.setImageResource(R.drawable.notification_sub);

        }

        //alertDialog.setView(infalteView);


        btnCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.dismiss();

            }
        });

       *//* btnOkDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.dismiss();

                if (checkBoxNews.isChecked())
                {
                    //  FirebaseMessaging.getInstance().subscribeToTopic(Common.toopicName);

                    FirebaseMessaging.getInstance().subscribeToTopic(Common.toopicName)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    //  String msg = getString(R.string.msg_subscribed);

                                    if (!task.isSuccessful())
                                    {
                                        //msg = getString(R.string.msg_subscribe_failed);
                                        Toast.makeText(Home.this, "حاول مره اخرى", Toast.LENGTH_SHORT).show();

                                    }
                                    //   Log.d(TAG, msg);
                                    Toast.makeText(Home.this, "تم الاشتراك", Toast.LENGTH_SHORT).show();
                                    imageNotification.setImageResource(R.drawable.notification_sub);
                                }
                            });


                    //write value  State of CheckBox
                    Paper.book().write("sub_new","true");
                }else
                {
                    //   FirebaseMessaging.getInstance().unsubscribeFromTopic(Common.toopicName);

                    FirebaseMessaging.getInstance().unsubscribeFromTopic(Common.toopicName)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    //  String msg = getString(R.string.msg_subscribed);

                                    if (!task.isSuccessful())
                                    {
                                        //msg = getString(R.string.msg_subscribe_failed);
                                        Toast.makeText(Home.this, "حاول مره اخرى", Toast.LENGTH_SHORT).show();

                                    }
                                    //   Log.d(TAG, msg);
                                    Toast.makeText(Home.this, "تم الغاء الاشتراك", Toast.LENGTH_SHORT).show();

                                }

                            });

                    //write value  State of CheckBox
                    Paper.book().write("sub_new","false");
                }

            }
        });*//*




        dialog.show();

    }


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

        FirebaseDatabase.getInstance().getReference(Common.USER).
                child(Common.currentUser.getUid())
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
                            .child(Common.currentUser.getUid())
                            .setValue(Common.currentUser)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(Home.this, "Update Address Successful", Toast.LENGTH_LONG).show();
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
    }*/



    //---------------------------------------------------------------
/*
    private void showChangePasswordDialog()
    {   //Change Password

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(Home.this);

                           alertDialog.setTitle("Change Password");
                           alertDialog.setMessage("Please Fill All Information");

        View  inflateView=this.getLayoutInflater().inflate(R.layout.change_password_dialog,null);

        final TextInputEditText editOldPassword=(TextInputEditText)inflateView.findViewById(R.id.old_password_ChangePassword);
        final TextInputEditText editNewPassword=(TextInputEditText)inflateView.findViewById(R.id.new_password_ChangePassword);
        final TextInputEditText editRepeatNewPassword=(TextInputEditText)inflateView.findViewById(R.id.repeat_password_ChangePassword);

        alertDialog.setView(inflateView);

        alertDialog.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                progressDialog.setTitle("Please Wait...");
                progressDialog.setMessage("Please Wait Please update your password");
                progressDialog.show();

              //Check Old Password
                if (editOldPassword.getText().toString().equals(Common.currentUser.getPassword()))
                {
                       //Check New Password And Repeat Password
                    if (editNewPassword.getText().toString().equals(editRepeatNewPassword.getText().toString()))
                    {
                        Map<String,Object> passwordUpdate=new HashMap<>();
                        passwordUpdate.put("Password",editNewPassword.getText().toString());

                        //Make Update
                        DatabaseReference user=FirebaseDatabase.getInstance().getReference("User");

                             user.child(Common.currentUser.getPhone()).updateChildren(passwordUpdate)
                                                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                     @Override
                                                     public void onComplete(@NonNull Task<Void> task)
                                                     {
                                                         progressDialog.dismiss();
                                                         Toast.makeText(Home.this, "Password Updated SuccessFull", Toast.LENGTH_SHORT).show();
                                                     }
                                                 })
                                     .addOnFailureListener(new OnFailureListener()
                             {
                                 @Override
                                 public void onFailure(@NonNull Exception e)
                                 {
                                     progressDialog.dismiss();
                                     Toast.makeText(Home.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                 }
                             });

                    }else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(Home.this, "new Password does not Match !!!", Toast.LENGTH_SHORT).show();
                        }
                }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(Home.this, "Wrong Password !!!", Toast.LENGTH_SHORT).show();
                    }
            }
        });


        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }
*/


}

