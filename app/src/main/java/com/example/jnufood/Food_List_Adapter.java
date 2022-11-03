package com.example.jnufood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Food_List_Adapter extends ArrayAdapter <Get_Food_List> {
OnCLickEventFoodList onCLickEventFoodList;

    public void setOnCLickEventFoodList(OnCLickEventFoodList onCLickEventFoodList) {
        this.onCLickEventFoodList = onCLickEventFoodList;
    }

    public Food_List_Adapter(Context context, ArrayList <Get_Food_List> get_food_listArrayList){
        super(context,0,get_food_listArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View list_item_view=convertView;
       if(list_item_view==null){
           list_item_view= LayoutInflater.from(getContext()).inflate(R.layout.food_list_grid_view,parent,false);
       }
       Get_Food_List get_food_list=getItem(position);
        TextView name=list_item_view.findViewById(R.id.grid_item_name);
        TextView amount=list_item_view.findViewById(R.id.grid_item_amount);
        TextView price=list_item_view.findViewById(R.id.grid_item_price);
        ImageView photo=list_item_view.findViewById(R.id.grid_image_food_list);
        TextView restaurant=list_item_view.findViewById(R.id.restaurant);
        TextView status_food=list_item_view.findViewById(R.id.status_food_list);
        status_food.setText(get_food_list.getStatus());
        name.setText(get_food_list.getName());
        amount.setText(get_food_list.getAmount());
        price.setText(get_food_list.getPrice());
        restaurant.setText(get_food_list.getRestaurant());
        Picasso.get().load(get_food_list.getPhoto()).into(photo);
        list_item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCLickEventFoodList.on_click_food_list(get_food_list.getName(), get_food_list.getAmount(), get_food_list.getPrice(), get_food_list.getRestaurant(),get_food_list.getPhoto(),get_food_list.getStatus());
            }
        });

        return list_item_view;

    }
    public interface OnCLickEventFoodList{
        void on_click_food_list(String name,String amount,String price,String restaurant,String image,String status);
    }
}
