package com.example.jnufood;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class DB_Adapter extends FirebaseRecyclerAdapter<DB_Modal,DB_Adapter.myViewHolder> {
    OnClickDB onClickDB;
    public void setOnclickEvent(OnClickDB onClickDB){
        this.onClickDB=onClickDB;
    }
    public DB_Adapter(@NonNull FirebaseRecyclerOptions<DB_Modal> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull DB_Modal model) {

           holder.cardView.setVisibility(View.VISIBLE);
           holder.restaurant.setText(model.getPeak_address());
           holder.destination.setText(model.getDelivery_address());
           holder.delivery_mobile.setText(model.getDelivery_mobile());

       holder.accept_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onClickDB.onClickAccept(model.getDb(), model.getDelivery_address(), model.getDelivery_mobile(), model.getMobile(), model.getPeak_address(), model.getStatus());
           }
       });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.db_home_recycle_view,parent,false);

        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView restaurant,destination,delivery_mobile,accept_btn;
        CardView cardView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurant=itemView.findViewById(R.id.db_request_pickup);
            destination=itemView.findViewById(R.id.db_request_destination);
            delivery_mobile=itemView.findViewById(R.id.db_delivery_mobile);
            accept_btn=itemView.findViewById(R.id.db_request_accept_btn);
            cardView=itemView.findViewById(R.id.db_home_recycle);
        }
    }
    public interface OnClickDB{
        void onClickAccept(String db,String delivery_address,String delivery_mobile,String mobile_c,String peak_address,String status);
    }
}
