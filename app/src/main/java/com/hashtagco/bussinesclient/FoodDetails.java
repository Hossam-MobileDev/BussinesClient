package com.hashtagco.bussinesclient;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.andremion.counterfab.CounterFab;

import com.hashtagco.bussinesclient.Common.Common;
import com.hashtagco.bussinesclient.Database.Database;
import com.hashtagco.bussinesclient.Model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hashtagco.bussinesclient.Model.SubFoods;
import com.squareup.picasso.Picasso;

public class FoodDetails extends AppCompatActivity {

    private TextView size  , type , name;
    private ImageView foodImage;
    private CounterFab btnCart;
    Button btnSizes;
//private FloatingActionButton btnRating;

   // private RatingBar ratingBar;
   public static final int SIZES = 100;
String sizes;
    //-----------------------------------------
    private String foodId="";
    private SubFoods currentFood;
    //----------------------------------------
    private FirebaseDatabase database;
    private DatabaseReference tabledetails;
    private DatabaseReference table_Rating;


    //Library Custom font
    /*@Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }*/

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

        setContentView(R.layout.activity_food_details);
        foodId=getIntent().getStringExtra("FoodId");

       /* if (getIntent() !=null)// Get Id Food OnClick "اللى ضغط عليها عشان ابعت الصوره وبياناتها لصفحه الوصف "
        {

            foodId=getIntent().getStringExtra("FoodId");

            if (!foodId.isEmpty())
            {
                getFoodDetails(foodId);// ارسال الKey لجلب بيانات الitem من الFirebase لعرضها فى اكتيفيتى ديه"الوصف"
                //  getRatingDetails(foodId); //also get Rating Data from Firebase use foodId
            }

        }*/
        //-----------------------Id-------------------------//

       // foodDescription=(TextView)findViewById(R.id.food_description_foodDetails);
        foodImage=(ImageView)findViewById(R.id.image_foodDetails);
        btnCart=(CounterFab)findViewById(R.id.btnCart_foodDetails);
        btnSizes = findViewById(R.id.btn_add_size);
        btnSizes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodDetails.this,ActivitySizes.class);
                intent.putExtra("FoodId",foodId);
                startActivityForResult(intent,SIZES);
            }
        });
       /* btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodDetails.this,Cart.class);
                startActivity(intent);
            }
        });*/
//name = (TextView) findViewById(R.id.productname);
        type = (TextView) findViewById(R.id.type);
    //    size = (TextView) findViewById(R.id.textsize);*/
      //  ratingBar=(RatingBar)findViewById(R.id.rating_bar_foodDetails);

//btnRating = findViewById(R.id.btnRating_foodDetails);
        //--------------------Firebase---------------------//
        database=FirebaseDatabase.getInstance();
        tabledetails=database.getReference("SubFoods");
        //table_Rating=database.getReference("Rating");

        //--------------------Collapsing Toolbar--------------//


        //----------Get Food Id From Intent--------------//


        //----------------------------Event---------------------------//

        //ارسال الطلبات المختاره "الاطعمه" لسه المشتريات اللى هيا قاعده البيانات 'SQlite'


        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Send Data to Sqlite

                /*boolean isExists = new Database(getBaseContext()).checkFoodExists
                        (foodId, Common.currentUser.getPhone());*/

//                if (!isExists){
                    //Send Data to Sqlite
                    new Database(getBaseContext()).addToCarts(new Order(
                            Common.currentUser.getPhone(),
                            foodId,
                            Common.subfooood.getName(),
                            "1", //numberButton.getNumber()
                            sizes,

                            Common.subfooood.getImage())
                    );

                    Toast.makeText(FoodDetails.this, "تم الاضافة الي السلة", Toast.LENGTH_SHORT).show();
Intent intent = new Intent(FoodDetails.this,Cart.class);
startActivity(intent);
                /*}else
                {
                    new Database(getBaseContext()).increaseCart(foodId, Common.currentUser.getPhone());

                    Toast.makeText(FoodDetails.this, "الكمية زادت", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FoodDetails.this,Cart.class);
                    startActivity(intent);
                }*/





            }
        });




        btnCart.setCount(new Database(this).getCountCart(Common.currentUser.getPhone()));


       /* //Show Dialog Rating
        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                showRatingDialog();

            }
        });*/

        Picasso.get()
                .load(Common.subfooood.getImage())//Url
                // .networkPolicy(NetworkPolicy.OFFLINE)//تحميل الصوره Offline
                //.placeholder(R.drawable.d)//الصوره الافتراضه اللى هتظهر لحد لما الصوره تتحمل
                .into(foodImage);//Image View
        type.setText(Common.subfooood.getType());


    }

//    //get Rating Data from Firebase use foodId and set Rating Bar your App
//    private void getRatingDetails(String foodId)
//    {
//        Query foodRating=table_Rating.child(foodId).orderByChild("foodId");//.equalTo(foodId)
//
//
//        foodRating.addValueEventListener(new ValueEventListener() {
//
//            int count=0,sum=0;
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//            {
//
//                for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
//                {
//                    Rating item=postSnapshot.getValue(Rating.class);
//
//                    sum+=Integer.parseInt(item.getRating());
//                    count++;
//                }
//
//
//                if (count !=0)
//                {
//                    float average=sum/count;
//
//                    ratingBar.setRating(average);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//
///*
//        //
//        foodRating.addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    int count=0,sum=0;
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot)
//                    {
//
//                        for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
//                        {
//                            Rating item=postSnapshot.getValue(Rating.class);
//
//                            sum+=Integer.parseInt(item.getRating());
//                            count++;
//                        }
//
//
//                        if (count !=0)
//                        {
//                            float average=sum/count;
//
//                            ratingBar.setRating(average);
//                        }
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                }
//        );
//*/
//
//
//    }




    //جلب البيانات item اللى ضغط عليه  عن طريق الKey الخاص بيه من الFirebase
    //get Data item  From Firebase
    private void getFoodDetails(final String foodId)
    {

        tabledetails.child(foodId).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                currentFood = dataSnapshot.getValue(SubFoods.class);

                //set image
                Picasso.get()
                        .load(currentFood.getImage())//Url
                        // .networkPolicy(NetworkPolicy.OFFLINE)//تحميل الصوره Offline
                        //.placeholder(R.drawable.d)//الصوره الافتراضه اللى هتظهر لحد لما الصوره تتحمل
                        .into(foodImage);//Image View
                type.setText(currentFood.getType());
               // name.setText(currentFood.getName());


                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    //Method Show Dialog Rating
//    private void showRatingDialog()
//    {
//        //Flow library com.stepstone.apprating:app-rating
//        new AppRatingDialog.Builder()
//                .setPositiveButtonText("تقييم")
//                .setNegativeButtonText("الغاء")
//                .setNoteDescriptions(Arrays.asList("سئ", "غير جيد", "متوسط",
//                        "جيد جدا", "ممتاز !!!"))
//                .setDefaultRating(1)
//                .setTitle("تقييم هذا المنتج")
//
//                .setStarColor(R.color.colorAccent)
//                .setNoteDescriptionTextColor(R.color.colorAccent)
//                .setTitleTextColor(R.color.colorPrimary)
//                .setDescriptionTextColor(R.color.colorAccent)
//                .setHint("من فضلك قم بتعليق")
//                .setHintTextColor(R.color.white)
//                .setCommentTextColor(R.color.white)
//                .setCommentBackgroundColor(R.color.colorAccent)
//                .setWindowAnimation(R.style.RatingDialogFadAnim)
//                .create(FoodDetails.this)
//                .show();
//
//
//        //Notes
//        //first implements for RatingDialogListener
//    }
    //this Method call when Click Submit
//    @Override
//    public void onPositiveButtonClicked(int valueRating, String comment)
//    {
//
//
//        final Rating rating=new Rating(Common.currentUser.getPhone(),foodId,String.valueOf(valueRating),comment);
//
//        table_Rating.child(foodId).push().setValue(rating).addOnCompleteListener(new OnCompleteListener<Void>()
//        {
//            @Override
//            public void onComplete(@NonNull Task<Void> task)
//            {
//                Toast.makeText(getApplicationContext(), "Thank You Your Rating", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        getRatingDetails(foodId);

        // or other Solution
        // table_Rating.child(Common.currentUser.getPhoneClient()).child(foodId).setValue(rating);
        //  getRatingDetails(foodId); //also get Rating Data from Firebase use foodId
        //  Toast.makeText(this, "Thank You Your Rating", Toast.LENGTH_SHORT).show();


           /*
        table_Rating.child(Common.currentUser.getPhoneClient()).
                             addValueEventListener(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                 {

                                     if (dataSnapshot.child(Common.currentUser.getPhoneClient()).exists())
                                     {
                                         if (dataSnapshot.child(foodId).exists())
                                         {
                                         //Remove DATA
                                         table_Rating.child(Common.currentUser.getPhoneClient()).removeValue();
                                         //Update Data
                                         table_Rating.child(Common.currentUser.getPhoneClient()).setValue(rating);

                                         }else
                                             {
                                                 table_Rating.child(Common.currentUser.getPhoneClient()).push().setValue(rating);

                                             }

                                     }else
                                         {
                                             table_Rating.child(Common.currentUser.getPhoneClient()).setValue(rating);
                                             // table_Rating.child(Common.currentUser.getPhoneClient()).push().setValue(rating);
                                         }

                                 }

                                 @Override
                                 public void onCancelled(@NonNull DatabaseError databaseError) {

                                 }
                             });
          */



   /* @Override
    public void onNegativeButtonClicked() {
    }
    @Override
    public void onNeutralButtonClicked() {
    }*/

    //--------------------------------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIZES) {
            if (resultCode == RESULT_OK) {
sizes = data.getStringExtra("size");
Toast.makeText(FoodDetails.this,sizes,Toast.LENGTH_SHORT).show();
            }
            }
    }
}
