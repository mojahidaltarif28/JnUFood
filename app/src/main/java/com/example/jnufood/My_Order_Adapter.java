package com.example.jnufood;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class My_Order_Adapter extends FirebaseRecyclerAdapter<Get_My_Cart_Modal, My_Order_Adapter.myViewHolder> {
    public My_Order_Adapter(@NonNull FirebaseRecyclerOptions<Get_My_Cart_Modal> options) {
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
        holder.price.setText(model.getPrice()+"x"+model.getTotal_item());
        holder.total_price.setText(model.getTotal_price());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_recycle_view,parent,false);


        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView name,price,total_price;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.my_order_recycle_view_image);
            name=itemView.findViewById(R.id.my_order_item_name_view);
            price=itemView.findViewById(R.id.price_my_order_rv);
            total_price=itemView.findViewById(R.id.total_order_single_item_rv);
        }
    }
}
