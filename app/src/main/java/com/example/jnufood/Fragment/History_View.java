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
import android.widget.TextView;

import com.example.jnufood.Get_My_Cart_Modal;
import com.example.jnufood.My_Order_Adapter;
import com.example.jnufood.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link History_View#newInstance} factory method to
 * create an instance of this fragment.
 */
public class History_View extends Fragment {
    TextView date_show, payment_amount;
    RecyclerView recyclerView;
    String mobile, time;
    My_Order_Adapter my_order_adapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/");
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public History_View() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment History_View.
     */
    // TODO: Rename and change types and number of parameters
    public static History_View newInstance(String param1, String param2) {
        History_View fragment = new History_View();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history__view, container, false);
        Bundle bundle = this.getArguments();
        mobile = bundle.getString("mobile");
        time = bundle.getString("time");
        date_show=view.findViewById(R.id.History_date_show);
        payment_amount=view.findViewById(R.id.payment_amount_show);
        databaseReference.child("History").child(mobile).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(time)){
                String amount=snapshot.child(time).child("payment_amount").getValue(String.class);
                String  date1=snapshot.child(time).child("date").getValue(String.class);
                payment_amount.setText(amount);
                date_show.setText(date1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView = view.findViewById(R.id.recycle_view_my_history_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<Get_My_Cart_Modal> options = new FirebaseRecyclerOptions.Builder<Get_My_Cart_Modal>().setQuery(FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/").child("History").child(mobile).child(time).child("list"), Get_My_Cart_Modal.class).build();
        options.getSnapshots();
        my_order_adapter = new My_Order_Adapter(options);
        recyclerView.setAdapter(my_order_adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        my_order_adapter.startListening();
    }
}