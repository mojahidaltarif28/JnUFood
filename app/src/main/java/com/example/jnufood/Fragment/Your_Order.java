package com.example.jnufood.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jnufood.Get_My_Cart_Modal;
import com.example.jnufood.My_Cart_Adapter;
import com.example.jnufood.My_Order_Adapter;
import com.example.jnufood.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Your_Order#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Your_Order extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String mobile;
    View pay_a, process_a_line, process_a, destination_line, destination_a, receive_line, receive_a;
    TextView empty_order, receive_btn, pay_txt, process_txt, destination_txt, receive_txt,cancel_btn;
    RecyclerView recyclerView;
    My_Order_Adapter my_order_adapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/");
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Your_Order() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Your_Order.
     */
    // TODO: Rename and change types and number of parameters
    public static Your_Order newInstance(String param1, String param2) {
        Your_Order fragment = new Your_Order();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_your__order, container, false);
        // Inflate the layout for this fragment
        Bundle bundle = this.getArguments();
        if (getArguments().getString("otp_id") != null) {
            mobile = bundle.getString("otp_id");
        }
        ConstraintLayout show_my_order = view.findViewById(R.id.show_my_order);
        empty_order = view.findViewById(R.id.empty_order);
        pay_a = view.findViewById(R.id.pay_a);
        process_a_line = view.findViewById(R.id.pay_a_line);
        process_a = view.findViewById(R.id.accept_a);
        destination_line = view.findViewById(R.id.accept_a_line);
        destination_a = view.findViewById(R.id.way_a);
        receive_line = view.findViewById(R.id.way_a_line);
        receive_a = view.findViewById(R.id.take_a);
        pay_txt = view.findViewById(R.id.pay_a_txt);
        process_txt = view.findViewById(R.id.process_txt);
        destination_txt = view.findViewById(R.id.destination_txt);
        receive_txt = view.findViewById(R.id.receive_txt);
        cancel_btn=view.findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+Uri.encode("01717268127")));
                startActivity(intent);
            }
        });
        receive_btn = view.findViewById(R.id.receive_btn);
        databaseReference.child("Order_Table").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(mobile)) {
                    String status = snapshot.child(mobile).child("status").getValue(String.class);
                    if (status.equals("paid")) {
                        pay_a.setBackgroundResource(R.drawable.my_order_animation_bg_success);
                        pay_txt.setTextColor(Color.parseColor("#0077c0"));
                    } else if (status.equals("processing")) {
                        pay_a.setBackgroundResource(R.drawable.my_order_animation_bg_success);
                        pay_txt.setTextColor(Color.parseColor("#0077c0"));
                        process_a_line.setBackgroundResource(R.drawable.my_order_animation_bg_line_success);
                        process_a.setBackgroundResource(R.drawable.my_order_animation_bg_success);
                        process_txt.setTextColor(Color.parseColor("#0077c0"));
                    } else if (status.equals("destination")) {
                        pay_a.setBackgroundResource(R.drawable.my_order_animation_bg_success);
                        pay_txt.setTextColor(Color.parseColor("#0077c0"));
                        process_a_line.setBackgroundResource(R.drawable.my_order_animation_bg_line_success);
                        process_a.setBackgroundResource(R.drawable.my_order_animation_bg_success);
                        process_txt.setTextColor(Color.parseColor("#0077c0"));
                        destination_line.setBackgroundResource(R.drawable.my_order_animation_bg_line_success);
                        destination_a.setBackgroundResource(R.drawable.my_order_animation_bg_success);
                        destination_txt.setTextColor(Color.parseColor("#0077c0"));

                    } else if (status.equals("receive")) {
                        pay_a.setBackgroundResource(R.drawable.my_order_animation_bg_success);
                        pay_txt.setTextColor(Color.parseColor("#0077c0"));
                        process_a_line.setBackgroundResource(R.drawable.my_order_animation_bg_line_success);
                        process_a.setBackgroundResource(R.drawable.my_order_animation_bg_success);
                        process_txt.setTextColor(Color.parseColor("#0077c0"));
                        destination_line.setBackgroundResource(R.drawable.my_order_animation_bg_line_success);
                        destination_a.setBackgroundResource(R.drawable.my_order_animation_bg_success);
                        destination_txt.setTextColor(Color.parseColor("#0077c0"));
                        receive_line.setBackgroundResource(R.drawable.my_order_animation_bg_line_success);
                        receive_a.setBackgroundResource(R.drawable.my_order_animation_bg_success);
                        receive_txt.setTextColor(Color.parseColor("#0077c0"));
                        receive_btn.setVisibility(View.VISIBLE);
                        cancel_btn.setVisibility(View.INVISIBLE);
                    } else {
                        show_my_order.setVisibility(View.GONE);
                        empty_order.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_my_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<Get_My_Cart_Modal> options = new FirebaseRecyclerOptions.Builder<Get_My_Cart_Modal>().setQuery(FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/").child("Order_Table").child(mobile).child("list"), Get_My_Cart_Modal.class).build();
        options.getSnapshots();
        my_order_adapter = new My_Order_Adapter(options);
        recyclerView.setAdapter(my_order_adapter);
        databaseReference.child("Order_Table").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(mobile)) {
                    empty_order.setVisibility(View.GONE);
                    show_my_order.setVisibility(View.VISIBLE);

                } else {
                    empty_order.setVisibility(View.VISIBLE);
                    show_my_order.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        String  time= new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
                .format(Calendar.getInstance().getTime());
        String  date= new SimpleDateFormat("dd-MM-yyyy")
                .format(Calendar.getInstance().getTime());
        receive_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("Order_Table").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        if (snapshot1.hasChild(mobile)) {
                            String d_address, d_mobile, pay_amount;
                            d_address = snapshot1.child(mobile).child("delivery_address").getValue(String.class);
                            d_mobile = snapshot1.child(mobile).child("delivery_mobile").getValue(String.class);
                            pay_amount = snapshot1.child(mobile).child("payment_amount").getValue(String.class);

                            for (DataSnapshot dataSnapshot : snapshot1.child(mobile).child("list").getChildren()) {
                                Get_My_Cart_Modal get_my_cart_modal = dataSnapshot.getValue(Get_My_Cart_Modal.class);
                                String Name, mobile, net, photo, price, total_item, total_price;
                                Name = get_my_cart_modal.getName();
                                net = get_my_cart_modal.getNet();
                                mobile = get_my_cart_modal.getMobile();
                                photo = get_my_cart_modal.getPhoto();
                                price = get_my_cart_modal.getPrice();
                                total_item = get_my_cart_modal.getTotal_item();
                                total_price = get_my_cart_modal.getTotal_price();
                                databaseReference.child("History").child(mobile).child(time).child("delivery_address").setValue(d_address);
                                databaseReference.child("History").child(mobile).child(time).child("date").setValue(date);
                                databaseReference.child("History").child(mobile).child(time).child("time").setValue(time);
                                databaseReference.child("History").child(mobile).child(time).child("delivery_mobile").setValue(d_mobile);
                                databaseReference.child("History").child(mobile).child(time).child("payment_amount").setValue(pay_amount);
                                databaseReference.child("History").child(mobile).child(time).child("list").child(Name).child("Name").setValue(Name);
                                databaseReference.child("History").child(mobile).child(time).child("list").child(Name).child("price").setValue(price);
                                databaseReference.child("History").child(mobile).child(time).child("list").child(Name).child("net").setValue(net);
                                databaseReference.child("History").child(mobile).child(time).child("list").child(Name).child("mobile").setValue(mobile);
                                databaseReference.child("History").child(mobile).child(time).child("list").child(Name).child("photo").setValue(photo);
                                databaseReference.child("History").child(mobile).child(time).child("list").child(Name).child("total_item").setValue(total_item);
                                databaseReference.child("History").child(mobile).child(time).child("list").child(Name).child("total_price").setValue(total_price);
                            }
                            databaseReference.child("Order_Table").child(mobile).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            });


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        my_order_adapter.startListening();
    }
}