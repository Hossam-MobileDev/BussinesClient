package com.hashtagco.bussinesclient;


public class MenuActivity {



    /*private DatabaseReference refDatabaseTableMenu;
    private FirebaseDatabase databafirebase;

    //-------------------------------------
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton btnAddMenu;


    private MenuImage menuImage;

    //-------------------------------------
    //--------Firebase UI--------//
    private Query query;
    private FirebaseRecyclerOptions<MenuImage> options;
    private FirebaseRecyclerAdapter<MenuImage, MenuImageViewHolder> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        //-------------Init---------------//
        databafirebase=FirebaseDatabase.getInstance();
        refDatabaseTableMenu =databafirebase.getReference(Common.MENU_IMAGE);

        refDatabaseTableMenu.keepSynced(true);//Save Data Offline
        //----------------------Id-------------------------//
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView_menu_activity);



        //-------------RecyclerView-------------------//
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        if (Common.IsConnectedToInternet(this))
        {
            loadMenu();
        }else
        {
            Toast.makeText(this, "Please Check Your Connection", Toast.LENGTH_SHORT).show();
        }



    }





    private void loadMenu()
    {


        //---Using Firebase UI to populate a RecyclerView--------//
        query= FirebaseDatabase.getInstance()
                .getReference()
                .child(Common.MENU_IMAGE);

        query.keepSynced(true);//Load Data OffLine

        options = new FirebaseRecyclerOptions.Builder<MenuImage>()
                .setQuery(query, MenuImage.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<MenuImage, MenuImageViewHolder>(options) {
            @Override
            public MenuImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_menu_activity, parent, false);

                return new MenuImageViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(final MenuImageViewHolder holder, final int position, final MenuImage model) {
                // Bind the Chat object to the ChatHolder



                //Send Image  to Recyclerview
                Picasso.get()
                        .load(model.getImage())//Url
                        //  .networkPolicy(NetworkPolicy.OFFLINE)//تحميل الصوره Offline
                        // .placeholder(R.drawable.d)//الصوره الافتراضه اللى هتظهر لحد لما الصوره تتحمل
                        .into(holder.imageView);


                final MenuImage clickItem=model;


                //لما المستخدم يضغط على اى صف
                holder.setItemClickListener(new ItemClickListener()
                {

                    @Override
                    public void onClick(View view, int position, boolean isLongClick)
                    {


                    }
                });



            }//end OnBind


        };//end Adapter

        //هيعمل تحديث للبيانات لو حصل تغيرفيها
        adapter.notifyDataSetChanged(); //Refresh Data if data changed

        recyclerView.setAdapter(adapter);
    }


    //Start Adapter
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


}
