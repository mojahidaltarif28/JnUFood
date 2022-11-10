package com.example.jnufood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class Restaurant_Model_Adapter extends FirebaseRecyclerAdapter<Restaurant_Model,Restaurant_Model_Adapter.myViewHolder> {
    OnClickEventRestaurant onClickEventRestaurant;
    public void setOnclickEvent(OnClickEventRestaurant onClickEventRestaurant){
        this.onClickEventRestaurant=onClickEventRestaurant;
    }
    public Restaurant_Model_Adapter(@NonNull FirebaseRecyclerOptions<Restaurant_Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Restaurant_Model model) {
        holder.txt.setText("Address:");
        holder.name.setText(model.getRestaurant_name());
        holder.mobile.setText(model.getMobile());
        holder.profession.setText(model.getAddress());
        Picasso.get().load(model.getPhoto()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEventRestaurant.onclickres(model.getAddress(), model.getEmail(), model.getMobile(), model.getPhoto(), model.getRestaurant_name());
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_boy_list_recycle_view,parent,false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView name, mobile, profession,txt;
        CardView cardView;
        ImageView imageView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_db_recycle_view);
            mobile = itemView.findViewById(R.id.mobile_db_recycle_view);
            profession = itemView.findViewById(R.id.profession_db_recycle_view);
            cardView = itemView.findViewById(R.id.delivery_boy_list_recycle);
            imageView = itemView.findViewById(R.id.delivery_boy_image);
            txt=itemView.findViewById(R.id.txt3125);
        }
    }
    public interface OnClickEventRestaurant{
        void onclickres( String address,String email,String mobile,String photo,String restaurant_name);
    }
}
