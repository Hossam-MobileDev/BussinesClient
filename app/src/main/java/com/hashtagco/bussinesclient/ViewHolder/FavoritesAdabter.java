package com.hashtagco.bussinesclient.ViewHolder;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hashtagco.bussinesclient.FoodDetails;
import com.hashtagco.bussinesclient.Interface.ItemClickListener;
import com.hashtagco.bussinesclient.Model.Favorites;
import com.hashtagco.bussinesclient.R;
import com.squareup.picasso.Picasso;

import java.util.List;



public class FavoritesAdabter extends RecyclerView.Adapter<FavoritesViewHolder> {

   private Context context;
   private List<Favorites> favoritesList;

    public FavoritesAdabter(Context context, List<Favorites>favoritesList)
    {
        this.context=context;
        this.favoritesList=favoritesList;
    }


    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.favorites_item,parent,false);


        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position)
    {

        final Favorites favorites=favoritesList.get(position);

        holder.favoritesFoodName.setText(favorites.getFoodName());
        holder.favoritesFoodPrice.setText(String.format("$ %s",favorites.getFoodPrice()));
        Picasso.get().load(favorites.getFoodImage()).into(holder.favoritesFoodImage);


        //Quick Cart
        holder.favoritesCartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
      boolean isExists = new Database(context).checkFoodExists(favorites.getFoodId(), Common.currentUser.getPhone());

                if (!isExists){
                    //Send Data to Sqlite
                    new Database(context).addToCarts(new Order(
                            Common.currentUser.getPhone(),
                            favorites.getFoodId(),
                            favorites.getFoodName(),
                            "1",

                            favorites.getFoodDiscount(),
                            favorites.getFoodImage())
                    );

                    Toast.makeText(context, "Added To Cars", Toast.LENGTH_SHORT).show();

                }else
                {
                    new Database(context).increaseCart(favorites.getFoodId(), Common.currentUser.getPhone());

                    Toast.makeText(context, "The quantity of the same food was increased", Toast.LENGTH_SHORT).show();

                }*/
            }
        });


        //لما المستخدم يضغط على اى صف
        holder.setItemClickListener(new ItemClickListener() {

            @Override
            public void onClick(View view, int position, boolean isLongClick)
            {
                //Start New Activity
                Intent foodDeatilsIntent=new Intent(context, FoodDetails.class);

                foodDeatilsIntent.putExtra("FoodId",favorites.getFoodId());//send food id new Activity
                context.startActivity(foodDeatilsIntent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }


    //********Swipe Delete Cart*********//
    public Favorites getItem(int postion)
    {
        return favoritesList.get(postion);
    }

    public void removeItem(int position)
    {
        favoritesList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Favorites item,int position)
    {
        favoritesList.add(position,item);
        notifyItemInserted(position);
    }


}
