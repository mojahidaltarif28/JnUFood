package com.example.jnufood.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewOrderTableRestaurant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewOrderTableRestaurant extends Fragment {
    String mobile,restaurant;
     RecyclerView recyclerView;
    My_Order_Adapter my_order_adapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewOrderTableRestaurant() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewOrderTableRestaurant.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewOrderTableRestaurant newInstance(String param1, String param2) {
        ViewOrderTableRestaurant fragment = new ViewOrderTableRestaurant();
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
        Bundle bundle = this.getArguments();
        mobile = bundle.getString("mobile");
        restaurant=bundle.getString("restaurant");

        View view = inflater.inflate(R.layout.fragment_view_order_table_restaurant, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_view_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<Get_My_Cart_Modal> options = new FirebaseRecyclerOptions.Builder<Get_My_Cart_Modal>().setQuery(FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/").child("Order_Table_Restaurant").child(restaurant).child(mobile).child("list"), Get_My_Cart_Modal.class).build();
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