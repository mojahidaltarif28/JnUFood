package com.example.jnufood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridAdapter extends ArrayAdapter<Get_Menu_Item> {
    // constructor for our list view adapter.
    public GridAdapter(Context context, ArrayList<Get_Menu_Item> get_menu_itemArrayList) {
        super(context, 0, get_menu_itemArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView=convertView;
        if(listitemView==null){
            listitemView=LayoutInflater.from(getContext()).inflate(R.layout.home_view_grid_item,parent,false);
        }
        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        Get_Menu_Item get_menu_item=getItem(position);
        // initializing our UI components of list view item.
        TextView item_name=listitemView.findViewById(R.id.grid_image_home_item_name);
        ImageView item_image=listitemView.findViewById(R.id.grid_image_home);

        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.
        item_name.setText(get_menu_item.getName());
        // in below line we are using Picasso to load image
        // from URL in our Image VIew.
        Picasso.get().load(get_menu_item.getPhoto()).into(item_image);
        // below line is use to add item
        // click listener for our item of list view.
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Item clicked is:"+get_menu_item.getName(),Toast.LENGTH_SHORT).show();
            }
        });

        return listitemView;
    }
}
