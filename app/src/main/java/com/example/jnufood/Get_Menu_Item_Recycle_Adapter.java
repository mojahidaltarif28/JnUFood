package com.example.jnufood;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Get_Menu_Item_Recycle_Adapter extends FirebaseRecyclerAdapter<Get_Menu_Item_Recycle_view,Get_Menu_Item_Recycle_Adapter.myViewHolder> {
    OnClickEventAdd_Menu_Item onClickEventAdd_menu_item;

    public void setOnClickEvent(OnClickEventAdd_Menu_Item onClickEvent) {
        this.onClickEventAdd_menu_item = onClickEvent;
    }
    public Get_Menu_Item_Recycle_Adapter(@NonNull FirebaseRecyclerOptions<Get_Menu_Item_Recycle_view> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Get_Menu_Item_Recycle_view model) {
        holder.name.setText(model.getName());
        Glide.with(holder.img.getContext()).
                load(model.getPhoto())
                .placeholder(R.drawable.fastfood)
                .circleCrop()
                .error(R.drawable.fastfood)
                .into(holder.img);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEventAdd_menu_item.on_menu_click(model.getName());
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.get_menu_item_recycle_view,parent,false);


        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView name;
        CardView cardView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img=(CircleImageView) itemView.findViewById(R.id.get_recycle_view_image);
            name=(TextView) itemView.findViewById(R.id.item_name_recycle_view);
            cardView=(CardView) itemView.findViewById(R.id.card_view_add_menu_item);
        }
    }
    public interface OnClickEventAdd_Menu_Item{
        void on_menu_click(String name);
    }
}
