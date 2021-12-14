package com.hashtagco.bussinesclient.ViewHolder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.hashtagco.bussinesclient.Cart;
import com.hashtagco.bussinesclient.Common.Common;
import com.hashtagco.bussinesclient.Database.Database;
import com.hashtagco.bussinesclient.Model.Order;
import com.hashtagco.bussinesclient.R;
import com.squareup.picasso.Picasso;


import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>{

    private List<Order>listData;
    private Cart cart;
Context context;
    public CartAdapter(List<Order> listData, Cart cart,Context context)
    {
        this.listData = listData;
        this.cart = cart;
        this.context=context;

    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater=LayoutInflater.from(cart);

        View view=layoutInflater.inflate(R.layout.cart_item_layout,parent,false);

        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, final int position)
    {

      /*  TextDrawable textDrawable=TextDrawable.builder()
                                  .buildRound(""+listData.get(position).getQuentity(), Color.RED);

        holder.imageCartCount.setImageDrawable(textDrawable);*/

        Picasso.get().load(listData.get(position).getImage()).into(holder.image);

        final Locale locale=new Locale("en","US");
        NumberFormat fmt=NumberFormat.getInstance(locale);

       // int price=(Integer.parseInt(listData.get(position).getPrice()))*
        // (Integer.parseInt(listData.get(position).getQuentity()));

        //holder.textCartPrice.setText(fmt.format(price));

       // holder.textCartName.setText(listData.get(position).getProudactName());

holder.deleteicon.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        removeItem(position);
    }
});

        holder.numberButtonCart.setNumber(listData.get(position).getQuentity());


        holder.numberButtonCart.setOnValueChangeListener(
                new ElegantNumberButton.OnValueChangeListener() {
                    @Override
                    public void onValueChange(ElegantNumberButton view, int oldValue, int newValue)
                    {



                        Order order=listData.get(position);
                        order.setQuentity(String.valueOf(newValue));

                        new Database(cart).updateCart(order);
                        Log.d("Coulom this =", ""+listData.get(position).getUserPhone());

                        //Update textTotalPrice
                        //Calculate Total Price
                        int totalPrice=0;

                        List<Order> orders=new Database(cart).getCarts(Common.currentUser.getPhone());

                        /*for (Order item:orders)
                        {
                            totalPrice+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(item.getQuentity()));
                        }*/

                        Locale locale1=new Locale("en","US");
                        NumberFormat numberFormat=NumberFormat.getCurrencyInstance(locale1);
                       // cart.textTotalPrice.setText(numberFormat.format(totalPrice)+"جنيه");
                    //    cart.textTotalPrice.setText( String.format("جنيه %s", totalPrice));


                    }
                });


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    //********Swipe Delete Cart*********//
    public Order getItem(int postion)
    {
        return listData.get(postion);
    }

    public void removeItem(int position)
    {
        listData.remove(position);
        new Database(context).cleanCart(Common.currentUser.getPhone());
        for (Order item :listData)
            new Database(context).addToCarts(item);
        //cart.textTotalPrice.setText("0");

        notifyItemRemoved(position);
    }

    public void restoreItem(Order item,int position)
    {
        listData.add(position,item);
        notifyItemInserted(position);
    }

}
