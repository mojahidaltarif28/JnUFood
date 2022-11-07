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

public class GetFoodOrderTableAdapter extends FirebaseRecyclerAdapter<GetFoodOrderTableModel, GetFoodOrderTableAdapter.myViewHolder> {
    OnClickEvent_FOT_restaurant onClickEvent_fot_restaurant;


    public void setOnClickEvent(OnClickEvent_FOT_restaurant onClickEvent_fot_restaurant) {
        this.onClickEvent_fot_restaurant = onClickEvent_fot_restaurant;
    }

    public GetFoodOrderTableAdapter(@NonNull FirebaseRecyclerOptions<GetFoodOrderTableModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull GetFoodOrderTableModel model) {
        holder.status.setText(model.getStatus());
        holder.mobile.setText(model.getDelivery_mobile());
        holder.pay_amount.setText(model.getPayment_amount());
        if (model.getStatus().equals("picked")||model.getStatus().equals("paid") || model.getStatus().equals("processing")||model.getStatus().equals("complete")) {
          holder.cardView.setVisibility(View.VISIBLE);
            if (model.getStatus().equals("processing")) {
                holder.complet_btn.setVisibility(View.VISIBLE);
                holder.accept_btn.setVisibility(View.GONE);
            }else if (model.getStatus().equals("paid")) {
                holder.complet_btn.setVisibility(View.GONE);
                holder.accept_btn.setVisibility(View.VISIBLE);
            }
            else if (model.getStatus().equals("picked")) {
                holder.complet_btn.setVisibility(View.GONE);
                holder.accept_btn.setVisibility(View.GONE);
            }else if (model.getStatus().equals("complete")) {
                holder.complet_btn.setVisibility(View.GONE);
                holder.accept_btn.setVisibility(View.GONE);
            }
            else {
                holder.complet_btn.setVisibility(View.GONE);
                holder.accept_btn.setVisibility(View.GONE);
            }
        } else {
            holder.cardView.setVisibility(View.GONE);
        }
        holder.view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEvent_fot_restaurant.onViewClick(model.getMobile(), model.getPayment_amount(), model.getDelivery_address());
            }
        });
        holder.accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEvent_fot_restaurant.acceptClick(model.getMobile());
            }
        });
        holder.complet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEvent_fot_restaurant.completeClick(model.getMobile(), model.getDelivery_address(), model.getDelivery_mobile(), model.getPayment_amount());
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_request_recycle_view, parent, false);

        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView status, mobile, pay_amount, accept_btn, view_btn, complet_btn;
        CardView cardView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.order_request_status);
            mobile = itemView.findViewById(R.id.order_request_mobile);
            pay_amount = itemView.findViewById(R.id.total_payment_amount_db);
            accept_btn = itemView.findViewById(R.id.delivery_request_accept_btn);
            view_btn = itemView.findViewById(R.id.view_order_request);
            complet_btn = itemView.findViewById(R.id.complete_processing);
            cardView = itemView.findViewById(R.id.order_request_recycle);
        }
    }

    public interface OnClickEvent_FOT_restaurant {
        void onViewClick(String mobile, String payment, String d_address);

        void acceptClick(String mobile);
        void completeClick(String mobile,String d_address,String d_mobile,String payment_amount);
    }
}
