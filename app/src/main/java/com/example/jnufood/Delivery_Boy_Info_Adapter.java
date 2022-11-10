package com.example.jnufood;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class Delivery_Boy_Info_Adapter extends FirebaseRecyclerAdapter<Delivery_Boy_Info_Model, Delivery_Boy_Info_Adapter.myViewHolder> {
    OnClickEventDBINFO onClickEventDBINFO;

    public Delivery_Boy_Info_Adapter(@NonNull FirebaseRecyclerOptions<Delivery_Boy_Info_Model> options) {
        super(options);
    }

    public void setOnclickEvent(OnClickEventDBINFO onClickEventDBINFO) {
        this.onClickEventDBINFO = onClickEventDBINFO;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Delivery_Boy_Info_Model model) {

        holder.name.setText(model.getName());
        holder.mobile.setText(model.getMobile());
        holder.profession.setText(model.getProfession());
        Glide.with(holder.imageView.getContext()).
                load(model.getPhoto())
                .placeholder(R.drawable.ic_person)
                .circleCrop()
                .error(R.drawable.ic_person)
                .into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEventDBINFO.OnClickDBINFO(model.getAddress(), model.getEmail(), model.getName(), model.getNid(), model.getPhoto(), model.getProfession(), model.getMobile());
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_boy_list_recycle_view, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView name, mobile, profession;
        CardView cardView;
        ImageView imageView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_db_recycle_view);
            mobile = itemView.findViewById(R.id.mobile_db_recycle_view);
            profession = itemView.findViewById(R.id.profession_db_recycle_view);
            cardView = itemView.findViewById(R.id.delivery_boy_list_recycle);
            imageView = itemView.findViewById(R.id.delivery_boy_image);
        }
    }

    public interface OnClickEventDBINFO {
        void OnClickDBINFO(String address, String email, String name, String nid, String photo, String profession,String mobile);
    }
}
