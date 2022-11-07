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

public class DB_Adapter_Accept extends FirebaseRecyclerAdapter<DB_Accept_Modal, DB_Adapter_Accept.myViewHolder> {
    OnClickDBAccept onClickDBAccept;
    public void setOnclickEvent(OnClickDBAccept onClickDBAccept){
        this.onClickDBAccept=onClickDBAccept;
    }
    public DB_Adapter_Accept(@NonNull FirebaseRecyclerOptions<DB_Accept_Modal> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull DB_Accept_Modal model) {
        if(model.getStatus().equals("processed")){
            holder.call_btn.setVisibility(View.VISIBLE);
            holder.give_btn.setVisibility(View.GONE);
            holder.receive_btn.setVisibility(View.VISIBLE);
        }else if(model.getStatus().equals("destination")){
            holder.call_btn.setVisibility(View.VISIBLE);
            holder.give_btn.setVisibility(View.VISIBLE);
            holder.receive_btn.setVisibility(View.GONE);
        }
           holder.cardView.setVisibility(View.VISIBLE);
           holder.restaurant.setText(model.getPeak_address());
           holder.destination.setText(model.getDelivery_address());
           holder.delivery_mobile.setText(model.getDelivery_mobile());
           holder.receive_btn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   onClickDBAccept.onclickReceive(model.getDb(), model.getMobile(),model.getPeak_address());
               }
           });

           holder.give_btn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   onClickDBAccept.onclickGive(model.getDb(), model.getMobile());
               }
           });
           holder.call_btn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   onClickDBAccept.onclickCall(model.getDelivery_mobile());
               }
           });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.db_home_accept_recycle_view,parent,false);

        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView restaurant,destination,delivery_mobile,accept_btn,receive_btn,give_btn,call_btn;
        CardView cardView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurant=itemView.findViewById(R.id.db_request_pickup1);
            destination=itemView.findViewById(R.id.db_request_destination1);
            delivery_mobile=itemView.findViewById(R.id.db_delivery_mobile1);
            cardView=itemView.findViewById(R.id.db_home_recycle1);
            receive_btn=itemView.findViewById(R.id.db_request_receive_btn1);
            give_btn=itemView.findViewById(R.id.db_request_give_btn1);
            call_btn=itemView.findViewById(R.id.db_request_call_btn1);
        }
    }
    public interface OnClickDBAccept{
         void onclickReceive(String db,String mobile_c,String restaurant);
        void onclickGive(String db,String mobile_c);
        void onclickCall(String delivery_mobile);
    }
}
