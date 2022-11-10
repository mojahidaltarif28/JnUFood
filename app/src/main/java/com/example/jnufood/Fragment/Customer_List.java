package com.example.jnufood.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jnufood.Delivery_Boy_Info_Adapter;
import com.example.jnufood.Delivery_Boy_Info_Model;
import com.example.jnufood.R;
import com.example.jnufood.Restaurant_Model;
import com.example.jnufood.Restaurant_Model_Adapter;
import com.example.jnufood.ViewDeliveryBoyList;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Customer_List#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Customer_List extends Fragment {
    RecyclerView recyclerView;
    Restaurant_Model_Adapter restaurant_model_adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Customer_List() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Customer_List.
     */
    // TODO: Rename and change types and number of parameters
    public static Customer_List newInstance(String param1, String param2) {
        Customer_List fragment = new Customer_List();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_customer__list, container, false);
        recyclerView=view.findViewById(R.id.recycle_view_restaurant_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<Restaurant_Model> options = new FirebaseRecyclerOptions.Builder<Restaurant_Model>().setQuery(FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/").child("Administration").child("Restaurant"), Restaurant_Model.class).build();
        options.getSnapshots();
        restaurant_model_adapter = new Restaurant_Model_Adapter(options);
        recyclerView.setAdapter(restaurant_model_adapter);
        restaurant_model_adapter.setOnclickEvent(new Restaurant_Model_Adapter.OnClickEventRestaurant() {
            @Override
            public void onclickres(String address, String email, String mobile, String photo, String restaurant_name) {
                Bundle bundle=new Bundle();
                ViewRestaurant viewRestaurant=new ViewRestaurant();
                bundle.putString("address",address);
                bundle.putString("email",email);
                bundle.putString("name",restaurant_name);
                bundle.putString("photo",photo);
                bundle.putString("mobile",mobile);
                viewRestaurant.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,viewRestaurant,null).addToBackStack(null).commit();

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        restaurant_model_adapter.startListening();
    }
}