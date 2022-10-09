package com.example.jnufood.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jnufood.Get_Menu_Item_Recycle_Adapter;
import com.example.jnufood.Get_Menu_Item_Recycle_view;
import com.example.jnufood.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Add_Menu_Item#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Add_Menu_Item extends Fragment {

    RecyclerView recyclerView;
    Get_Menu_Item_Recycle_Adapter get_menu_item_recycle_adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Add_Menu_Item() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Add_Menu_Item.
     */
    // TODO: Rename and change types and number of parameters
    public static Add_Menu_Item newInstance(String param1, String param2) {
        Add_Menu_Item fragment = new Add_Menu_Item();
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
        View view=inflater.inflate(R.layout.fragment_add__menu__item, container, false);
        recyclerView=(RecyclerView) view.findViewById(R.id.add_menu_item_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<Get_Menu_Item_Recycle_view> options =
                new FirebaseRecyclerOptions.Builder<Get_Menu_Item_Recycle_view>()
                        .setQuery(FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/").child("food_Item"), Get_Menu_Item_Recycle_view.class)
                        .build();

        get_menu_item_recycle_adapter=new Get_Menu_Item_Recycle_Adapter(options);
        recyclerView.setAdapter(get_menu_item_recycle_adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        get_menu_item_recycle_adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        get_menu_item_recycle_adapter.stopListening();
    }
}