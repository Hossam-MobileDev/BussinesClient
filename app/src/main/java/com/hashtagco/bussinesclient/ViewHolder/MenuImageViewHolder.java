package com.hashtagco.bussinesclient.ViewHolder;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.hashtagco.bussinesclient.Interface.ItemClickListener;
import com.hashtagco.bussinesclient.R;


public class MenuImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{



    public ImageView imageView;
    //-------------------------
    private ItemClickListener itemClickListener;// Interface


    public MenuImageViewHolder(View itemView)
    {
        super(itemView);

        imageView=(ImageView)itemView.findViewById(R.id.food_menu_activity_image);



        itemView.setOnClickListener(this); //Item Click Listiner



    }



    @Override
    public void onClick(View view)
    {

        itemClickListener.onClick(view , getAdapterPosition() , false);


    }


    //Setter ItemClickListiner
    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;


    }



}
