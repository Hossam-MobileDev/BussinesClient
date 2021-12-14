package com.hashtagco.bussinesclient.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hashtagco.bussinesclient.Model.Banner;
import com.hashtagco.bussinesclient.R;
import com.squareup.picasso.Picasso;

import java.util.List;


//Adabter Home Activity
public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder>
{

   private List<Banner> banner;
   private Context context;

    public BannerAdapter(Context context, List<Banner> banner)
    {
        this.banner = banner;
        this.context=context;

    }


    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view= LayoutInflater.from(context).inflate(R.layout.item_banner_activity,parent,false);

        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position)
    {
        Banner banner = this.banner.get(position);

holder.priceoffer.setText(banner.getNewprice()+"جنيه");
       holder.banner_name.setText(banner.getName());
        //  holder.menu_Image.setImageResource(category.getImage());
        Picasso.get().load(banner.getImage()).into(holder.banner_Image);

    }

    @Override
    public int getItemCount()
    {
        return banner.size();
    }

    //View Holder
    class BannerViewHolder extends RecyclerView.ViewHolder
  {

      TextView banner_name , priceoffer;
      ImageView banner_Image;

      public BannerViewHolder(View itemView)
      {
          super(itemView);

          banner_name=(TextView)itemView.findViewById(R.id.namefood_banner);
          banner_Image=(ImageView)itemView.findViewById(R.id.imagefood_banner);
          priceoffer=(TextView) itemView.findViewById(R.id.priceoffer);


      }




  }


}
