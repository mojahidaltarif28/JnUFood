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

public class History_Adapter extends FirebaseRecyclerAdapter<Get_history_modal,History_Adapter.myViewHolder> {
OnClickEventHistory onClickEventHistory;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public History_Adapter(@NonNull FirebaseRecyclerOptions<Get_history_modal> options) {
        super(options);
    }
    public void setOnclickEvent(OnClickEventHistory onClickEventHistory){
        this.onClickEventHistory=onClickEventHistory;
    }
    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Get_history_modal model) {
        holder.date.setText(model.getDate());
        holder.total.setText(model.getPayment_amount());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEventHistory.onClickHistroy(model.getTime());
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_recycle_view,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView date,total;
        CardView cardView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.history_date);
            total=itemView.findViewById(R.id.total_histoy_amount);
            cardView=itemView.findViewById(R.id.history_recycle);
        }
    }
    public interface OnClickEventHistory{
        void onClickHistroy(String time);
    }
}
