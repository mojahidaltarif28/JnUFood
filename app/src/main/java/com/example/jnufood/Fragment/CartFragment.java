package com.example.jnufood.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jnufood.Get_Food_List;
import com.example.jnufood.Get_My_Cart_Modal;
import com.example.jnufood.My_Cart_Adapter;
import com.example.jnufood.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {
    String mobile;
    RecyclerView recyclerView;
    My_Cart_Adapter my_cart_adapter;
    LinearLayout checkoutshow;
    TextView total_price, delivery_charge, checkout_amount;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/");
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        total_price = view.findViewById(R.id.itemtotal);
        delivery_charge = view.findViewById(R.id.delivery_services);
        checkout_amount = view.findViewById(R.id.total_checkout_amount);
        checkoutshow=view.findViewById(R.id.checkoutshow);
        Bundle bundle = this.getArguments();
        mobile = bundle.getString("mobile");
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_my_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<Get_My_Cart_Modal> options = new FirebaseRecyclerOptions.Builder<Get_My_Cart_Modal>().setQuery(FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/").child("Cart_List").child(mobile).child("list"), Get_My_Cart_Modal.class).build();
        options.getSnapshots();
        my_cart_adapter = new My_Cart_Adapter(options);
        recyclerView.setAdapter(my_cart_adapter);
        my_cart_adapter.setOnclickEvent(new My_Cart_Adapter.OnClickEventMyCart() {
            @Override
            public void plusbtn(String name, String total_item, String price) {
                if (Integer.parseInt(total_item) < 99) {
                    int total_i = Integer.parseInt(total_item) + 1;
                    int total_p = Integer.parseInt(price) * total_i;
                    HashMap hashMap = new HashMap();
                    hashMap.put("total_item", String.valueOf(total_i));
                    hashMap.put("total_price", String.valueOf(total_p));
                    databaseReference.child("Cart_List").child(mobile).child("list").child(name).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            databaseReference.child("Cart_List").child(mobile).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.hasChild("Total")){
                                        final String total_tk = snapshot.child("Total").getValue(String.class);
                                       int total_plus=Integer.parseInt(total_tk)+Integer.parseInt(price);

                                        int checkout=total_plus+10;

                                        HashMap hashMapP = new HashMap();
                                        hashMapP.put("Total", String.valueOf(total_plus));
                                        databaseReference.child("Cart_List").child(mobile).updateChildren(hashMapP).addOnSuccessListener(new OnSuccessListener() {
                                            @Override
                                            public void onSuccess(Object o) {
                                                total_price.setText(String.valueOf(total_plus));
                                                checkout_amount.setText(String.valueOf(checkout));
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
                } else {
                    Toast.makeText(getActivity(), "Can't add more than 99 item", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void minusbtn(String name, String total_item, String price) {
                if (Integer.parseInt(total_item) > 1) {
                    int total_i = Integer.parseInt(total_item) - 1;
                    int total_p = Integer.parseInt(price) * total_i;
                    HashMap hashMap = new HashMap();
                    hashMap.put("total_item", String.valueOf(total_i));
                    hashMap.put("total_price", String.valueOf(total_p));
                    databaseReference.child("Cart_List").child(mobile).child("list").child(name).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            databaseReference.child("Cart_List").child(mobile).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.hasChild("Total")){
                                        final String total_tk = snapshot.child("Total").getValue(String.class);
                                        int total_minus=Integer.parseInt(total_tk)-Integer.parseInt(price);

                                        int checkout=total_minus+10;

                                        HashMap hashMapM = new HashMap();
                                        hashMapM.put("Total", String.valueOf(total_minus));
                                        databaseReference.child("Cart_List").child(mobile).updateChildren(hashMapM).addOnSuccessListener(new OnSuccessListener() {
                                            @Override
                                            public void onSuccess(Object o) {
                                                total_price.setText(String.valueOf(total_minus));
                                                checkout_amount.setText(String.valueOf(checkout));
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
                } else {
                    Toast.makeText(getActivity(), "Can't add less than one item", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void delete(String name,String total_price_d) {
                databaseReference.child("Cart_List").child(mobile).child("list").child(name).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        databaseReference.child("Cart_List").child(mobile).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChild("Total")){
                                    final String total_tk = snapshot.child("Total").getValue(String.class);
                                    int total_minus=Integer.parseInt(total_tk)-Integer.parseInt(total_price_d);

                                    int checkout=total_minus+10;

                                    HashMap hashMapM = new HashMap();
                                    hashMapM.put("Total", String.valueOf(total_minus));
                                    databaseReference.child("Cart_List").child(mobile).updateChildren(hashMapM).addOnSuccessListener(new OnSuccessListener() {
                                        @Override
                                        public void onSuccess(Object o) {
                                            total_price.setText(String.valueOf(total_minus));
                                            checkout_amount.setText(String.valueOf(checkout));
                                            if(total_minus==0){
                                                checkoutshow.setVisibility(View.GONE);
                                            }else {
                                                checkoutshow.setVisibility(View.VISIBLE);
                                            }
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
            }

        });
          sum_set();

        return view;
    }

    private void sum_set() {
        databaseReference.child("Cart_List").child(mobile).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("Total")){
                    final String total_tk = snapshot.child("Total").getValue(String.class);
                    total_price.setText(total_tk);
                    int checkout=Integer.parseInt(total_tk)+10;
                    checkout_amount.setText(String.valueOf(checkout));
                    if(total_tk==null || Integer.parseInt(total_tk)==0){
                        checkoutshow.setVisibility(View.GONE);
                    }else {
                        checkoutshow.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        my_cart_adapter.startListening();
    }
}