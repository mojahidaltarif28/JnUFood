package com.example.jnufood.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jnufood.MainActivity;
import com.example.jnufood.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowDetailsCart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowDetailsCart extends Fragment {
    String title, net, price, restaurant, image,mobile;
    private int number_of_item=1;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/");
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShowDetailsCart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowDetailsCart.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowDetailsCart newInstance(String param1, String param2) {
        ShowDetailsCart fragment = new ShowDetailsCart();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_details_cart, container, false);
        // Inflate the layout for this fragment
        Bundle bundle = this.getArguments();

        title = bundle.getString("name");
        net = bundle.getString("net");
        price = bundle.getString("price");
        int price_int=Integer.parseInt(price);
        restaurant = bundle.getString("restaurant");
        image = bundle.getString("image");
        mobile=bundle.getString("mobile");

        ImageView image_t = view.findViewById(R.id.cart_details_image);
        TextView title_t = view.findViewById(R.id.title_cart);
        TextView price_t = view.findViewById(R.id.amount);
        TextView total_item = view.findViewById(R.id.total_item);
        TextView net_t = view.findViewById(R.id.net_amount);
        TextView restaurant_t = view.findViewById(R.id.restaurant_details_cart);
        TextView total_price = view.findViewById(R.id.total_price);
        Picasso.get().load(image).into(image_t);
        title_t.setText(title);
        price_t.setText(price);
        total_item.setText(String.valueOf(number_of_item));
        net_t.setText(net);
        restaurant_t.setText(restaurant);
        total_price.setText(price);

        TextView plusbtn,minusbtn;
        plusbtn=view.findViewById(R.id.plus_item);
        minusbtn=view.findViewById(R.id.minus_item);
        plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(number_of_item<99){
                number_of_item=number_of_item+1;
                }else {
                    Toast.makeText(getActivity(), "maximum order of item is 99 ", Toast.LENGTH_SHORT).show();
                }
                 total_item.setText(String.valueOf(number_of_item));
                 total_price.setText(String.valueOf(number_of_item*price_int));
            }
        });
        minusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(number_of_item>1){
                    number_of_item=number_of_item-1;
                }
                total_item.setText(String.valueOf(number_of_item));
                total_price.setText(String.valueOf(number_of_item*price_int));
            }
        });
        TextView add_cart_btn = view.findViewById(R.id.add_to_cart_btn);
        add_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("Cart_List").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(mobile).child("list").hasChild(title)){
                            Toast.makeText(getActivity(),"Already Added Your Cart",Toast.LENGTH_SHORT).show();

                        }
                        else if(snapshot.child(mobile).hasChild("Total")){
                            databaseReference.child("Cart_List").child(mobile).child("list").child(title).child("restaurant").setValue(restaurant_t.getText().toString());
                            databaseReference.child("Cart_List").child(mobile).child("list").child(title).child("Name").setValue(title);
                            databaseReference.child("Cart_List").child(mobile).child("list").child(title).child("price").setValue(price);
                            databaseReference.child("Cart_List").child(mobile).child("list").child(title).child("net").setValue(net);
                            databaseReference.child("Cart_List").child(mobile).child("list").child(title).child("mobile").setValue(mobile);
                            databaseReference.child("Cart_List").child(mobile).child("list").child(title).child("photo").setValue(image);
                            databaseReference.child("Cart_List").child(mobile).child("list").child(title).child("total_item").setValue(total_item.getText().toString());
                            databaseReference.child("Cart_List").child(mobile).child("list").child(title).child("total_price").setValue(total_price.getText().toString());
                            final String total = snapshot.child(mobile).child("Total").getValue(String.class);
                            final int t;
                            t=Integer.parseInt(total)+Integer.parseInt(total_price.getText().toString());
                            databaseReference.child("Cart_List").child(mobile).child("Total").setValue(String.valueOf(t));
                            Toast.makeText(getActivity(),"Added to Your Cart",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            databaseReference.child("Cart_List").child(mobile).child("list").child(title).child("restaurant").setValue(restaurant_t.getText().toString());
                            databaseReference.child("Cart_List").child(mobile).child("list").child(title).child("Name").setValue(title);
                            databaseReference.child("Cart_List").child(mobile).child("list").child(title).child("price").setValue(price);
                            databaseReference.child("Cart_List").child(mobile).child("list").child(title).child("net").setValue(net);
                            databaseReference.child("Cart_List").child(mobile).child("list").child(title).child("mobile").setValue(mobile);
                            databaseReference.child("Cart_List").child(mobile).child("list").child(title).child("photo").setValue(image);
                            databaseReference.child("Cart_List").child(mobile).child("list").child(title).child("total_item").setValue(total_item.getText().toString());
                            databaseReference.child("Cart_List").child(mobile).child("list").child(title).child("total_price").setValue(total_price.getText().toString());
                            databaseReference.child("Cart_List").child(mobile).child("Total").setValue(total_price.getText().toString());
                            Toast.makeText(getActivity(),"Added to Your Cart",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(),"Added Failed",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


        return view;
    }
}