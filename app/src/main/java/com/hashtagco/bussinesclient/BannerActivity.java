package com.hashtagco.bussinesclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BannerActivity extends AppCompatActivity {

   /* private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RelativeLayout rootLayoutFoodList;
    private ProgressDialog mProgressDialog;

    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPref;
    //----------------------------------------------
    private FirebaseDatabase database;
    private DatabaseReference databaseReferenceBanner;
    private DatabaseReference databaseReferenceFoods;
    private Banner newBanner;
    List<Banner> bannerlist;
    BannerAdapter bannerAdapter;
    //------------------------------
    private Query query;
    private FirebaseRecyclerOptions<Banner> options;
//    private FirebaseRecyclerAdapter<Banner,
//            BannerAdapter> BannerAdapter;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

      /*  recyclerView=(RecyclerView)findViewById(R.id.recyclerView_Banner);
        mProgressDialog=new ProgressDialog(this);
        database=FirebaseDatabase.getInstance();
        bannerlist = new ArrayList<Banner>();
        databaseReferenceBanner=database.getReference(Common.BANNER);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadBannerList();*/
    }
   /* private void loadBannerList()
    {
        databaseReferenceBanner.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                // Banner.clear();

                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Banner banner =snapshot.
                            getValue(Banner.class);
                    banner.setBannerId(snapshot.getKey());
                   bannerlist.add(banner);

                }

                bannerAdapter=new BannerAdapter(BannerActivity.this, bannerlist);

                recyclerView.setAdapter(bannerAdapter);
                bannerAdapter.notifyDataSetChanged();
                //  swipeRefresh.setRefreshing(false);
                recyclerView.getAdapter().notifyDataSetChanged();
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
       *//* options=new  FirebaseRecyclerOptions.Builder<Banner>()
                .setQuery(databaseReferenceBanner,Banner.class)
                .build();

        adapter=new FirebaseRecyclerAdapter<Banner, BannerViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BannerViewHolder holder,
                                            int position, @NonNull Banner model)
            {

                holder.textViewBanner.setText(model.getName());

                Picasso.get()
                        .load(model.getImage())
                        .into(holder.imageViewBanner);




                holder.setItemClickListiner(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick)
                    {

                    }
                });

            }

            @NonNull
            @Override
            public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                View v=LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_banner_activity,parent,false);


                return new BannerViewHolder(v);
            }
        };


        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);*//*

    }*/

}