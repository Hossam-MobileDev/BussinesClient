package com.hashtagco.bussinesclient.ViewHolder;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hashtagco.bussinesclient.FoodList;
import com.hashtagco.bussinesclient.Model.Category;
import com.hashtagco.bussinesclient.R;

import java.util.List;



//Adabter Home Activity
public class AdabterHome extends RecyclerView.Adapter<AdabterHome.ViewHolderHome>
{

   private List<Category> Category;
   private Context context;

    public AdabterHome(Context context, List<Category> Category)
    {
        this.Category = Category;
        this.context=context;

    }


    @NonNull
    @Override
    public ViewHolderHome onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view= LayoutInflater.from(context).inflate(R.layout.menu_item,parent,false);

        return new ViewHolderHome(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHome holder, int position)
    {
        Category Category = this.Category.get(position);


       holder.menu_name.setText(Category.getName());
        //  holder.menu_Image.setImageResource(category.getImage());
        //Picasso.get().load(Category.getImage()).into(holder.menu_Image);

    }

    @Override
    public int getItemCount()
    {
        return Category.size();
    }

    //View Holder
    class ViewHolderHome extends RecyclerView.ViewHolder
  {

      TextView menu_name;
      ImageView menu_Image;

      public ViewHolderHome(View itemView)
      {
          super(itemView);

          menu_name=(TextView)itemView.findViewById(R.id.menu_name);
//          menu_Image=(ImageView)itemView.findViewById(R.id.menu_image);


          itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view)
              {
                  Category Category =
                          AdabterHome.this.Category.get(getAdapterPosition());

                  Intent intent=new Intent(context, FoodList.class);

                  intent.putExtra("CategoryId", Category.getMenuId());
                  intent.putExtra("CategorName", Category.getName());


                  context.startActivity(intent);


              }
          });
      }




  }


}
