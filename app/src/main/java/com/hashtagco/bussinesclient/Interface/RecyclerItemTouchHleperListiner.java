package com.hashtagco.bussinesclient.Interface;


import androidx.recyclerview.widget.RecyclerView;

//Swipe Delete Cart
public interface RecyclerItemTouchHleperListiner
{

    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);

}
