package com.example.jnufood;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class Get_Restaurant_Adapter extends FirebaseRecyclerAdapter<Get_Restaurant_Model, Get_Restaurant_Adapter.myViewHolder> {
    OnClickEvent_RES_admin onClickEvent_res_admin;

    public void setOnClickEvent(OnClickEvent_RES_admin onClickEvent_res_admin) {
        this.onClickEvent_res_admin = onClickEvent_res_admin;
    }

    public Get_Restaurant_Adapter(@NonNull FirebaseRecyclerOptions<Get_Restaurant_Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Get_Restaurant_Model model) {
        holder.name.setText(model.getRestaurant_name());
        holder.txt1_pp.setText("Category:");
        holder.mobile.setText(model.getMobile());
        Glide.with(holder.img.getContext()).load(model.getImage())
                .placeholder(R.drawable.ic_person)
                .circleCrop()
                .error(R.drawable.ic_person)
                .into(holder.img);
        holder.profession.setText(model.getFood_category());
        holder.accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEvent_res_admin.on_RES_Admin_Click(model.getFood_category(), model.getAddress(), model.getEmail(), model.getImage(), model.getMobile(), model.getRestaurant_name());
            }
        });
        holder.decline_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEvent_res_admin.decline_Res_click(model.getMobile());
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_db_application_recycle_view, parent, false);

        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView name, profession, txt1_pp, mobile, accept_btn, decline_btn;
        CardView cardView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            txt1_pp = itemView.findViewById(R.id.txt1_pp);
            mobile = itemView.findViewById(R.id.dba_recycle_mobile);
            accept_btn = itemView.findViewById(R.id.dba_recycle_accept_btn);
            decline_btn = itemView.findViewById(R.id.dba_recycle_decline_btn);
            img = (CircleImageView) itemView.findViewById(R.id.dba_recycle_view_image);
            name = (TextView) itemView.findViewById(R.id.dba_name_recycle_view);
            profession = (TextView) itemView.findViewById(R.id.dba_recycle_view_profession);
            cardView = (CardView) itemView.findViewById(R.id.cardView_dba);
        }
    }

    public interface OnClickEvent_RES_admin {
        void on_RES_Admin_Click(String food_category, String address, String email, String image, String mobile, String res_name);
        void decline_Res_click(String mobile);
    }

}
