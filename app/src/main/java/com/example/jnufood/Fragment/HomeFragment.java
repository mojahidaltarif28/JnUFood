package com.example.jnufood.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jnufood.Get_Menu_Item;
import com.example.jnufood.GridAdapter;
import com.example.jnufood.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment  {
    GridView HomeGridView;
    GridAdapter gridAdapter;
    LinearLayout progressbar;
    ArrayList<Get_Menu_Item> list;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String mobile;
    GridView gridView;

    public HomeFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment

        progressbar = view.findViewById(R.id.progress_bar_home);
        progressbar.setVisibility(View.VISIBLE);
        Bundle bundle = this.getArguments();
        if (getArguments().getString("otp_id") != null) {
            mobile = bundle.getString("otp_id");
        }
        Toast.makeText(getActivity(), "login:" + mobile, Toast.LENGTH_SHORT).show();
//       gridview part
        HomeGridView = view.findViewById(R.id.home_grid_view);
        list = new ArrayList<>();
        databaseReference.child("food_Item").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Get_Menu_Item get_menu_item = dataSnapshot.getValue(Get_Menu_Item.class);

                    list.add(get_menu_item);
                }
                GridAdapter adapter = new GridAdapter(getActivity(),list);
                HomeGridView.setAdapter(adapter);
                adapter.setOnClickEvent(new GridAdapter.OnClickEvent() {
                    @Override
                    public void onhomeclick(String name) {
                        Toast.makeText(getActivity(),name+""+mobile,Toast.LENGTH_SHORT).show();
                    }
                });
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

}