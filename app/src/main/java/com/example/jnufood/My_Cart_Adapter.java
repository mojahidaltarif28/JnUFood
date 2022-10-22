package com.example.jnufood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class My_Cart_Adapter extends FirebaseRecyclerAdapter<Get_My_Cart_Modal,My_Cart_Adapter.myViewHolder> {

    public My_Cart_Adapter(@NonNull FirebaseRecyclerOptions<Get_My_Cart_Modal> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Get_My_Cart_Modal model) {

        holder.name.setText(model.getName());
        Glide.with(holder.img.getContext()).
                load(model.getPhoto())
                .placeholder(R.drawable.fastfood)
                .circleCrop()
                .error(R.drawable.fastfood)
                .into(holder.img);
        holder.price.setText(model.getPrice());
        holder.total_item.setText(model.getTotal_item());

        holder.total_price.setText(model.getTotal_price());
        String item_no=holder.total_item.getText().toString();
        holder.plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(item_no)>0){
                    int numofitem = Integer.parseInt(model.getTotal_item());
                    numofitem = numofitem +1;
                    holder.total_item.setText(String.valueOf(numofitem));
                }
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_recycle_view,parent,false);


        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView  name,net,price,total_item,total_price,plusbtn,minusbtn,delete_btn;
        String restaurant,mobile;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.my_cart_recycle_view_image);
            name=itemView.findViewById(R.id.my_cart_item_name_view);
            price=itemView.findViewById(R.id.price_my_cart_rv);
            total_price=itemView.findViewById(R.id.total_mycart_single_item_rv);
            total_item=itemView.findViewById(R.id.total_item_mycart);
            plusbtn=itemView.findViewById(R.id.plus_item_mycart);
            minusbtn=itemView.findViewById(R.id.minus_item_mycart);
            delete_btn=itemView.findViewById(R.id.delete_btn_cart_view);
            }
    }
}
