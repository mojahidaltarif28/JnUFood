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

public class Get_DBA_Modal_Adapter extends FirebaseRecyclerAdapter<Get_DBA_Modal,Get_DBA_Modal_Adapter.myViewHolder> {
   OnClickEvent_DBA_admin onClickEvent_dba_admin;
   public void setOnClickEvent(OnClickEvent_DBA_admin onClickEvent){
       this.onClickEvent_dba_admin=onClickEvent;
   }
    public Get_DBA_Modal_Adapter(@NonNull FirebaseRecyclerOptions<Get_DBA_Modal> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Get_DBA_Modal model) {
        holder.name.setText(model.getName());
        holder.profession.setText(model.getProfession());
        Glide.with(holder.img.getContext()).load(model.getImage())
                .placeholder(R.drawable.ic_person)
                .circleCrop()
                .error(R.drawable.ic_person)
                .into(holder.img);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEvent_dba_admin.on_DBA_Admin_Click(model.getName().toString(),model.getMobile().toString(),
                        model.getEmail().toString(),model.getProfession().toString(),model.getAddress().toString(),
                        model.getImage().toString(),model.getNid().toString());
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_db_application_recycle_view,parent,false);

        return new myViewHolder(view);
    }
    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView name,profession;
        CardView cardView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img=(CircleImageView) itemView.findViewById(R.id.dba_recycle_view_image);
            name=(TextView) itemView.findViewById(R.id.dba_name_recycle_view);
            profession=(TextView) itemView.findViewById(R.id.dba_recycle_view_profession);
            cardView=(CardView) itemView.findViewById(R.id.cardView_dba);
        }
    }
    public interface OnClickEvent_DBA_admin{
        void on_DBA_Admin_Click(String name,String mobile,String email,String profession,String address,String image,String nid);
    }

}
