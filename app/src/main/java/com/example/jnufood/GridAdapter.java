package com.example.jnufood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridAdapter extends ArrayAdapter<Get_Menu_Item> {
    // constructor for our list view adapter.
    OnClickEvent onClickEvent;
    String name;

    public void setOnClickEvent(OnClickEvent onClickEvent) {
        this.onClickEvent = onClickEvent;
    }

    public GridAdapter(Context context, ArrayList<Get_Menu_Item> get_menu_itemArrayList) {
        super(context, 0, get_menu_itemArrayList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.home_view_grid_item, parent, false);
        }

        Get_Menu_Item get_menu_item = getItem(position);

        TextView item_name = listitemView.findViewById(R.id.grid_image_home_item_name);
        ImageView item_image = listitemView.findViewById(R.id.grid_image_home);

        item_name.setText(get_menu_item.getName());

        Picasso.get().load(get_menu_item.getPhoto()).into(item_image);

        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // Toast.makeText(getContext(), "ttt"+get_menu_item.getName(), Toast.LENGTH_SHORT).show();
               name=get_menu_item.getName();
//                Toast.makeText(getContext(), ""+name, Toast.LENGTH_SHORT).show();
                onClickEvent.onhomeclick(name);
                           }
        });

        return listitemView;
    }
    public interface OnClickEvent{
        void onhomeclick(String name);
    }


}
