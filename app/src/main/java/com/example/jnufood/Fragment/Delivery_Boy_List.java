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
import com.example.jnufood.Get_history_modal;
import com.example.jnufood.History_Adapter;
import com.example.jnufood.R;
import com.example.jnufood.ViewDeliveryBoyList;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Delivery_Boy_List#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Delivery_Boy_List extends Fragment {

    RecyclerView recyclerView;
    Delivery_Boy_Info_Adapter delivery_boy_info_adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Delivery_Boy_List() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Delivery_Boy_List.
     */
    // TODO: Rename and change types and number of parameters
    public static Delivery_Boy_List newInstance(String param1, String param2) {
        Delivery_Boy_List fragment = new Delivery_Boy_List();
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
        View view=inflater.inflate(R.layout.fragment_delivery__boy__list, container, false);

        recyclerView=view.findViewById(R.id.recycle_view_delivery_boy_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<Delivery_Boy_Info_Model> options = new FirebaseRecyclerOptions.Builder<Delivery_Boy_Info_Model>().setQuery(FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/").child("Administration").child("Delivery_man"), Delivery_Boy_Info_Model.class).build();
        options.getSnapshots();
        delivery_boy_info_adapter = new Delivery_Boy_Info_Adapter(options);
        recyclerView.setAdapter(delivery_boy_info_adapter);
        delivery_boy_info_adapter.setOnclickEvent(new Delivery_Boy_Info_Adapter.OnClickEventDBINFO() {
            @Override
            public void OnClickDBINFO(String address, String email, String name, String nid, String photo, String profession,String mobile) {
                Bundle bundle=new Bundle();
                ViewDeliveryBoyList viewDeliveryBoyList=new ViewDeliveryBoyList();
                bundle.putString("address",address);
                bundle.putString("email",email);
                bundle.putString("name",name);
                bundle.putString("nid",nid);
                bundle.putString("photo",photo);
                bundle.putString("profession",profession);
                bundle.putString("mobile",mobile);
                viewDeliveryBoyList.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,viewDeliveryBoyList,null).addToBackStack(null).commit();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        delivery_boy_info_adapter.startListening();
    }
}