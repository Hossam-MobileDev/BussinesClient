package com.hashtagco.bussinesclient.Helper;

import android.graphics.Canvas;

import android.view.View;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.hashtagco.bussinesclient.Interface.RecyclerItemTouchHleperListiner;
import com.hashtagco.bussinesclient.ViewHolder.CartViewHolder;
import com.hashtagco.bussinesclient.ViewHolder.FavoritesViewHolder;


//Swipe Delete Cart
public class RecyclerItemTouchHeleper extends ItemTouchHelper.SimpleCallback{

    private RecyclerItemTouchHleperListiner listiner;

    public RecyclerItemTouchHeleper(int dragDirs, int swipeDirs,RecyclerItemTouchHleperListiner listiner) {
        super(dragDirs, swipeDirs);
        this.listiner =listiner;
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
    {
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
    {
        if (listiner!=null)
              listiner.onSwiped(viewHolder,direction,viewHolder.getAdapterPosition());
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
    {

        if (viewHolder instanceof CartViewHolder)
        {
            View viewForbackground = ((CartViewHolder) viewHolder).viewForBackground;
            getDefaultUIUtil().clearView(viewForbackground);
        }
        else if (viewHolder instanceof FavoritesViewHolder)
        {
            View viewForbackground = ((FavoritesViewHolder) viewHolder).viewFaoritesForBackground;
            getDefaultUIUtil().clearView(viewForbackground);
        }


    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
    {

        if (viewHolder instanceof CartViewHolder)
        {
            View viewForbackground = ((CartViewHolder) viewHolder).viewForBackground;
            getDefaultUIUtil().onDraw(c, recyclerView, viewForbackground, dX, dY, actionState, isCurrentlyActive);

        }else if (viewHolder instanceof FavoritesViewHolder)
        {
            View viewForbackground = ((FavoritesViewHolder) viewHolder).viewFaoritesForBackground;
            getDefaultUIUtil().onDraw(c, recyclerView, viewForbackground, dX, dY, actionState, isCurrentlyActive);

        }

    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState)
    {

        if (viewHolder !=null)
        {
                 if (viewHolder instanceof CartViewHolder)
                 {
                     View viewForbackground = ((CartViewHolder) viewHolder).viewForBackground;
                     getDefaultUIUtil().onSelected(viewForbackground);
                 }
                 else if (viewHolder instanceof  FavoritesViewHolder)
                 {
                     View viewForbackground = ((FavoritesViewHolder) viewHolder).viewFaoritesForBackground;
                     getDefaultUIUtil().onSelected(viewForbackground);
                 }


        }

    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
    {

        if (viewHolder instanceof CartViewHolder)
        {
            View viewForbackground=((CartViewHolder)viewHolder).viewForBackground;
            getDefaultUIUtil().onDrawOver(c,recyclerView,viewForbackground,dX,dY,actionState,isCurrentlyActive);

        }
        else if (viewHolder instanceof  FavoritesViewHolder)
        {
            View viewForbackground=((FavoritesViewHolder)viewHolder).viewFaoritesForBackground;
            getDefaultUIUtil().onDrawOver(c,recyclerView,viewForbackground,dX,dY,actionState,isCurrentlyActive);

        }


    }


}
