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

public class Get_Food_Item_recycle_Adapter extends FirebaseRecyclerAdapter<Get_Food_Item_recycleModal,Get_Food_Item_recycle_Adapter.myViewHolder>{
    OnclickEventAddFoodItem onclickEventAddFoodItem;

    public  void setOnclickEvent(OnclickEventAddFoodItem onclickEventAddFoodItem){
        this.onclickEventAddFoodItem=onclickEventAddFoodItem;
    }
    public Get_Food_Item_recycle_Adapter(@NonNull FirebaseRecyclerOptions<Get_Food_Item_recycleModal> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Get_Food_Item_recycleModal model) {
        holder.name.setText(model.getName());
        Glide.with(holder.img.getContext()).
                load(model.getPhoto())
                .placeholder(R.drawable.fastfood)
                .circleCrop()
                .error(R.drawable.fastfood)
                .into(holder.img);
        holder.price.setText(model.getPrice());
        holder.restaurant.setText(model.getRestaurant());
        holder.net.setText(model.getAmount());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickEventAddFoodItem.on_food_click(model.getName(),model.getPhoto(),model.getPrice(), model.getAmount(), model.getRestaurant());
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.get_food_item_recycle_view,parent,false);


        return new myViewHolder(view);
    }


    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView name,price,net,restaurant;
        CardView cardView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img=(CircleImageView)itemView.findViewById(R.id.food_list_recycle_view_image);
            name=(TextView) itemView.findViewById(R.id.name_recycle_view);
            price=(TextView) itemView.findViewById(R.id.price_recycle_view);
            net=(TextView) itemView.findViewById(R.id.net_recycle_view);
            restaurant=(TextView) itemView.findViewById(R.id.restaurant_recycle_view);
            cardView=(CardView) itemView.findViewById(R.id.cardView_add_food_item);
        }
    }

   public interface OnclickEventAddFoodItem{
        void on_food_click(String name,String photo,String price,String net,String restaurant);
   }
}
