package com.hashtagco.bussinesclient.ViewHolder;


import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.hashtagco.bussinesclient.Common.Common;
import com.hashtagco.bussinesclient.Interface.ItemClickListener;
import com.hashtagco.bussinesclient.R;


// رViewHolder عاديه عشان الاAdabter
//------------------View Holder-----------------//
public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
                                                                                        ,View.OnCreateContextMenuListener{


    public TextView textCartName,textCartPrice;
    public ElegantNumberButton numberButtonCart;
    public ImageView image , deleteicon;
    private ItemClickListener itemClickListener; //Interface

    public RelativeLayout viewBackground;
    public LinearLayout viewForBackground;



    public CartViewHolder(View itemView)
    {
        super(itemView);

        textCartName=(TextView)itemView.findViewById(R.id.cart_item_name);
        textCartPrice=(TextView)itemView.findViewById(R.id.cart_item_price);
        numberButtonCart=(ElegantNumberButton) itemView.findViewById(R.id.number_button_Cart);
        image=(ImageView)itemView.findViewById(R.id.cart_image_row);
deleteicon=(ImageView) itemView.findViewById(R.id.delete_icon);
//        viewBackground=(RelativeLayout)itemView.findViewById(R.id.view_background);
//        viewForBackground=(LinearLayout)itemView.findViewById(R.id.view_forground);


        itemView.setOnCreateContextMenuListener(this);
    }

    public void setTextCartName(TextView textCartName) {
        this.textCartName = textCartName;
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo)
    {
        contextMenu.setHeaderTitle("Select action ");
        contextMenu.add(0,0,getAdapterPosition(), Common.DELETE);
    }
}



