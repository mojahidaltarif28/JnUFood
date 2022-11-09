package com.example.jnufood.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jnufood.DB_Accept_Modal;
import com.example.jnufood.DB_Adapter_Accept;
import com.example.jnufood.DB_Modal;
import com.example.jnufood.Get_My_Cart_Modal;
import com.example.jnufood.My_Order_Adapter;
import com.example.jnufood.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link History_Delivery_Boy#newInstance} factory method to
 * create an instance of this fragment.
 */
public class History_Delivery_Boy extends Fragment {
    String mobile;
    RecyclerView recyclerView;
    DB_Adapter_Accept db_adapter_accept;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/");
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public History_Delivery_Boy() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment History_Delivery_Boy.
     */
    // TODO: Rename and change types and number of parameters
    public static History_Delivery_Boy newInstance(String param1, String param2) {
        History_Delivery_Boy fragment = new History_Delivery_Boy();
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
        View view = inflater.inflate(R.layout.fragment_history__delivery__boy, container, false);
        // Inflate the layout for this fragment
        Bundle bundle = this.getArguments();
        mobile = bundle.getString("otp_id");
        recyclerView = view.findViewById(R.id.recycle_view_current_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<DB_Accept_Modal> options = new FirebaseRecyclerOptions.Builder<DB_Accept_Modal>().setQuery(FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/").child("Order_DB_Accept").child(mobile), DB_Accept_Modal.class).build();
        options.getSnapshots();
        db_adapter_accept = new DB_Adapter_Accept(options);
        recyclerView.setAdapter(db_adapter_accept);
        db_adapter_accept.setOnclickEvent(new DB_Adapter_Accept.OnClickDBAccept() {

            @Override
            public void onclickReceive(String db,String mobile_c,String restaurant) {
                HashMap hashMap = new HashMap();
                hashMap.put("status", "destination");
                databaseReference.child("Order_DB_Accept").child(db).child(mobile_c).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(getActivity(),"Received",Toast.LENGTH_SHORT).show();
                    }
                });
                databaseReference.child("Order_Table").child(mobile_c).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        databaseReference.child("Order_Table_Restaurant").child(restaurant).child(mobile_c).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                });
            }

            @Override
            public void onclickGive(String db,String mobile_c) {
                HashMap hashMap = new HashMap();
                hashMap.put("status", "receive");
                databaseReference.child("Order_Table").child(mobile_c).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        databaseReference.child("Order_DB_Accept").child(db).child(mobile_c).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getActivity(),"Gived",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onclickCall(String delivery_mobile) {
                Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+Uri.encode(delivery_mobile)));
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        db_adapter_accept.startListening();
    }
}