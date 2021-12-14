package com.hashtagco.bussinesclient;


public class FavoritesActivity
       {

//    //----------------------------------
//    private FirebaseDatabase database;
//    private DatabaseReference foodList; //using index
//    //----------------------------------
//    private RecyclerView recyclerViewFavorites;
//    private RecyclerView.LayoutManager layoutManager;
//
//
//    private FavoritesAdabter favoritesAdabter;
//    private RelativeLayout rootLayout;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_favorites);
//
//        //-------------------Id----------------------------//
//        recyclerViewFavorites=(RecyclerView)findViewById(R.id.recyclerView_Favorites);
//        rootLayout=(RelativeLayout)findViewById(R.id.rootLayoutFavorites);
//
//
//        //------------------Firebase-------------------------//
//        database=FirebaseDatabase.getInstance();
//        foodList =database.getReference("Foods");
//
//
//
//        recyclerViewFavorites.setHasFixedSize(true);
//         layoutManager=new LinearLayoutManager(this);
//        recyclerViewFavorites.setLayoutManager(layoutManager);
//
//       //------------------Recycler View Animation------------------//
//        LayoutAnimationController controller= AnimationUtils.loadLayoutAnimation(recyclerViewFavorites.getContext(),R.anim.layout_animation_from_righit);
//        recyclerViewFavorites.setLayoutAnimation(controller);
//
//
//        //Swipe to delete
//        ItemTouchHelper.SimpleCallback itemTouchHelperCallback=new RecyclerItemTouchHeleper(0,ItemTouchHelper.LEFT,this);
//        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewFavorites);
//
//         //loadFavorites();
//
//    }
//
//
//    /*private void loadFavorites()
//    {
//      favoritesAdabter=new FavoritesAdabter(this,new Database(this).getAllFavorites(Common.currentUser.getPhone()));
//      recyclerViewFavorites.setAdapter(favoritesAdabter);
//    }*/
//
//
//    @Override
//    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position)
//    {
//        if (viewHolder instanceof FavoritesViewHolder)
//        {
//
//            String name=((FavoritesAdabter)recyclerViewFavorites.getAdapter()).getItem(position).getFoodName();
//
//            final Favorites deleteOrder=((FavoritesAdabter)recyclerViewFavorites.getAdapter()).getItem(viewHolder.getAdapterPosition());
//            final int deleteIndex=viewHolder.getAdapterPosition();
//
//            //*****Remove Item*******//
//            favoritesAdabter.removeItem(deleteIndex);
//            new Database(getBaseContext()).removeFavorites(deleteOrder.getFoodId(), Common.currentUser.getPhone());
//
//
//            //*****UNDO Item*******//
//            //Make SnakeBar
//            Snackbar snackbar=Snackbar.make(rootLayout,name +"removed From Favorites",Snackbar.LENGTH_LONG);
//
//            snackbar.setAction("UNDO", new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View view)
//                {
//                    favoritesAdabter.restoreItem(deleteOrder,deleteIndex);
//                    //new Database(getBaseContext()).addToFavorites(deleteOrder);
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
//    }
}
