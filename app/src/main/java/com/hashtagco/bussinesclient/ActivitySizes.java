package com.hashtagco.bussinesclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hashtagco.bussinesclient.Common.Common;
import com.hashtagco.bussinesclient.Model.SubFoods;
import com.hashtagco.bussinesclient.databinding.ActivitySizesBinding;

public class ActivitySizes extends AppCompatActivity {
ActivitySizesBinding binding;
String foodId;
    String size;
    private FirebaseDatabase database;
    private DatabaseReference table_Foods;
    private SubFoods currentFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySizesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        if(size!=null){
            binding.layoutSizes.setVisibility(View.INVISIBLE);

        }
        database= FirebaseDatabase.getInstance();
        table_Foods=database.getReference("SubFoods");
if(Common.subfooood.getSmallfrom()!=null||Common.subfooood.getSmallto()
!=null||Common.subfooood.getMeduimfrom()!=null||Common.subfooood.getMeduimto()!=null) {
    binding.sfroom.setText(Common.subfooood.getSmallfrom());
    binding.stoo.setText(Common.subfooood.getSmallto());
    binding.mfroom.setText(Common.subfooood.getMeduimfrom());
    binding.mtoo.setText(Common.subfooood.getMeduimto());
    binding.mplusfroom.setText(Common.subfooood.getMplusfrom());
    binding.mplustoo.setText(Common.subfooood.getMplusto());
    binding.lfroom.setText(Common.subfooood.getLargefrom());
    binding.ltoo.setText(Common.subfooood.getLargeto());
    binding.lplusfroom.setText(Common.subfooood.getLargeplusfrom());
    binding.lplustoo.setText(Common.subfooood.getLargeplusto());
    binding.xlfroom.setText(Common.subfooood.getXlargfrom());
    binding.xltoo.setText(Common.subfooood.getXlargeto());
}else {
    binding.layoutSizes.setVisibility(View.GONE);
    binding.nosizes.setVisibility(View.VISIBLE);
}
        /*if (getIntent() !=null)// Get Id Food OnClick "اللى ضغط عليها عشان ابعت الصوره وبياناتها لصفحه الوصف "
        {

            foodId=getIntent().getStringExtra("FoodId");

            if (!foodId.isEmpty())
            {
                getFoodDetails(foodId);// ارسال الKey لجلب بيانات الitem من الFirebase لعرضها فى اكتيفيتى ديه"الوصف"
                //  getRatingDetails(foodId); //also get Rating Data from Firebase use foodId
            }

        }*/
        binding.addsizes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          size       = binding.edtsizes.getText().toString();
Intent intent = getIntent();
intent.putExtra("size",size);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void getFoodDetails(String foodId) {
        {

            table_Foods.child(foodId).addValueEventListener(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    currentFood = dataSnapshot.getValue(SubFoods.class);

                    binding.sfroom.setText(currentFood.getSmallfrom());
binding.stoo.setText(currentFood.getSmallto());
binding.mfroom.setText(currentFood.getMeduimfrom());
binding.mtoo.setText(currentFood.getMeduimto());
binding.mplusfroom.setText(currentFood.getMplusfrom());
binding.mplustoo.setText(currentFood.getMplusto());
binding.lfroom.setText(currentFood.getLargefrom());
binding.ltoo.setText(currentFood.getLargeto());
binding.lplusfroom.setText(currentFood.getLargeplusfrom());
binding.lplustoo.setText(currentFood.getLargeplusto());
binding.xlfroom.setText(currentFood.getXlargfrom());
binding.xltoo.setText(currentFood.getXlargeto());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }
}