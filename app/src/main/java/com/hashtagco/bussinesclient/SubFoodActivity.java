package com.hashtagco.bussinesclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.hashtagco.bussinesclient.Common.Common;
import com.hashtagco.bussinesclient.Interface.ItemClickListener;
import com.hashtagco.bussinesclient.Model.SubFoods;
import com.hashtagco.bussinesclient.ViewHolder.SubFoodViewHolder;

public class SubFoodActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference subfoodList; //using index
    // private DatabaseReference foodComments;
    //----------------------------------
    private RecyclerView recyclerViewsbFoods;
    private RecyclerView.LayoutManager layoutManager;
    //--------Firebase UI--------//
    private Query searchByName;
    private FirebaseRecyclerOptions<SubFoods> options;
    private FirebaseRecyclerAdapter<SubFoods, SubFoodViewHolder> adapter;
    TextView txttoolbar;
    private String foodId;
    String foodName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_food);
        txttoolbar = findViewById(R.id.texttolbar);
        //-------------------Id----------------------------//
        recyclerViewsbFoods=(RecyclerView)findViewById(R.id.recyclerView_food);
        // materialSearchBar=(MaterialSearchBar)findViewById(R.id.search_Bar_FoodList);//---Search*/
        // swipeRefresh=(SwipeRefreshLayout)findViewById(R.id.swipe_foodlist_refresh);


        //------------------Firebase-------------------------//
        database=FirebaseDatabase.getInstance();
        subfoodList =database.getReference("SubFoods");
        //foodComments=database.getReference(Common.RATING);

        // foodComments.keepSynced(true);//Save Data Offline
        subfoodList.keepSynced(true);//Save Data Offline


        //------------------Recycler View------------------//
        recyclerViewsbFoods.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerViewsbFoods.setLayoutManager(layoutManager);
        foodId =  getIntent().getStringExtra("FoodId");
        foodName = getIntent().getStringExtra("FoodName");
        txttoolbar.setText(foodName);
        loadListsubFoods(foodId);
    }
    private void loadListsubFoods(String foodId) {
        //---Using Firebase UI to populate a RecyclerView--------//

        //Create Query by Category Id
        Query searchById = subfoodList.orderByChild("foodId").equalTo(foodId);

        options = new FirebaseRecyclerOptions.Builder<SubFoods>()
                .setQuery(searchById, SubFoods.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<SubFoods, SubFoodViewHolder>(options) {
            @Override
            public SubFoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.sub_food_item, parent, false);

                return new SubFoodViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(final SubFoodViewHolder holder, final int position,
                                            final SubFoods model) {
                // Bind the Chat object to the ChatHolder

                //Send Image  to Recyclerview
//                         holder.shareImage.setImageResource(R.drawable.ic_share_black_24dp);

                //Send Image Name to Recyclerview
                if(model.getName()!=null) {
                    holder.textFoodName.setText(model.getName());
                }else{
                    holder.textFoodName.setText(foodName);
                }
                holder.textFoodName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SubFoodActivity.this,FoodDetails.class);
                        intent.putExtra("FoodId",adapter.getRef(position).getKey());
                        Common.subfooood=model;
                        startActivity(intent);
                    }
                });
                //    holder.textFoodPrice.setText(String.format("LE %s", model.getPrice()));

                //Send Image  to Recyclerview
                   /*     Picasso.get()
                                .load(model.getImage())//Url
                                //.networkPolicy(NetworkPolicy.OFFLINE)//تحميل الصوره Offline
                                //.placeholder(R.drawable.d)//الصوره الافتراضه اللى هتظهر لحد لما الصوره تتحمل
                                .into(holder.foodimageView);*/

             //   final Foods clickItem = model;


                // Click to Share
                      /* holder.shareImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (ShareDialog.canShow(ShareLinkContent.class)) {

                                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                            //Le asignamos una imagen para mostrar que subimos a internete
                                            .setImageUrl(Uri.parse(model.getImage()))
                                            //Un enlace al que se accederia clickando en Facebook
                                            .setContentUrl(Uri.parse(model.getImage()))
                                            .setContentDescription(model.getDescription())
                                            .setQuote("https://play.google.com/store/apps/developer?id=MBasuony_JA")
                                            .build();
                                    //shareDialog .show(linkContent);  // Show facebook ShareDialog

                                }
                               //   shareItem(model.getImage(),clickItem);


                            }
                        });*/

                //Quick Cart
                    /*    holder.queckCartImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                boolean isExists = new Database(getBaseContext()).
                                        checkFoodExists(adapter.getRef(position).getKey(),
                                                Common.currentUser.getPhone());

                                if (!isExists) {
                                    //Send Data to Sqlite
                                    new Database(getBaseContext()).addToCarts(new Order(
                                            Common.currentUser.getPhone(),
                                            adapter.getRef(position).getKey(),
                                            model.getName(),
                                            "1",

                                            model.getDiscount(),
                                            model.getImage())

                                    );

                                    Toast.makeText(FoodList.this, "تمت الاضافة الي السلة", Toast.LENGTH_SHORT).show();

                                } else {
                                    new Database(getBaseContext()).increaseCart(adapter.getRef(position).getKey(), Common.currentUser.getPhone());

                                    Toast.makeText(FoodList.this, "The quantity of the same food was increased", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });*/


                //Check food do Favorites or Not ,is Not Display Image Favorite border...
                        /*if (localDatabe.isFoodFavorites(adapter.getRef(position).getKey(),
                                Common.currentUser.getPhone()))
                            holder.favimage.setImageResource(R.drawable.ic_favorite_black_24dp);
*/

                //if Click on Image Favorites
                       /* holder.favimage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Favorites favorites = new Favorites();

                                favorites.setFoodId(adapter.getRef(position).getKey());
                                favorites.setFoodName(model.getName());
                                favorites.setFoodPrice(model.getPrice());
                                favorites.setFoodMenuId(model.getMenuId());
                                favorites.setFoodImage(model.getImage());
                                favorites.setFoodDiscount(model.getDiscount());
                                favorites.setFoodDescription(model.getDescription());
                                favorites.setUserPhone(Common.currentUser.getPhone());

                                //Not Favorites
                                if (!localDatabe.isFoodFavorites(adapter.getRef(position).getKey(), Common.currentUser.getPhone())) {
                                    //Add Favorites
                                    localDatabe.addToFavorites(favorites);

                                    holder.favimage.setImageResource(R.drawable.ic_favorite_black_24dp);

                                    Toast.makeText(FoodList.this, model.getName() + " was add Favorites", Toast.LENGTH_SHORT).show();

                                } else { //IF FOOD Favorites
                                    //Remove Food Favorites
                                    localDatabe.removeFavorites(adapter.getRef(position).getKey(), Common.currentUser.getPhone());
                                    holder.favimage.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                                    Toast.makeText(FoodList.this, model.getName() + " Remove From Favorites", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });*/


                //لما المستخدم يضغط على اى صف
                       /* holder.foodimageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                Intent foodDeatilsIntent = new Intent(
                                        FoodList.this, FoodDetails.class);

                                foodDeatilsIntent.putExtra("FoodId", adapter.getRef(position).getKey());//send food id new Activity

                                startActivity(foodDeatilsIntent);
                            }
                        });*/


                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(SubFoodActivity.this, "Please click on the icon !!", Toast.LENGTH_SHORT).show();
                    }
                });


            }//end OnBind
        };//end Adapter
        adapter.startListening();
        recyclerViewsbFoods.setAdapter(adapter);
        //swipeRefresh.setRefreshing(false);
    }

}