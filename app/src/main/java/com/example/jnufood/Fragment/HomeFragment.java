package com.example.jnufood.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SearchView;
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
public class HomeFragment extends Fragment {
    GridView HomeGridView;
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
    String mobile, type;

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
        LinearLayout search_bar_layout=view.findViewById(R.id.search_bar_layout);
        progressbar = view.findViewById(R.id.progress_bar_home);
        progressbar.setVisibility(View.VISIBLE);
        search_bar_layout.setVisibility(View.GONE);
        Bundle bundle = this.getArguments();
        mobile = bundle.getString("otp_id");
        type = bundle.getString("type");

        LinearLayout customer_home_show = view.findViewById(R.id.home_customer_view);
        LinearLayout Admin_home_show = view.findViewById(R.id.home_admin_view);
        LinearLayout db_home_show=view.findViewById(R.id.home_db_view);
        Toast.makeText(getActivity(), "login:" + mobile + " type:" + type, Toast.LENGTH_SHORT).show();
//       gridview part
        if(type.equals("Customer")){
            Admin_home_show.setVisibility(View.GONE);
            db_home_show.setVisibility(View.GONE);
            customer_home_show.setVisibility(View.VISIBLE);
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
                GridAdapter adapter = new GridAdapter(getActivity(), list);
                HomeGridView.setAdapter(adapter);
                adapter.setOnClickEvent(new GridAdapter.OnClickEvent() {
                    @Override
                    public void onhomeclick(String name) {
                        FoodList foodList = new FoodList();
                        Bundle bundle = new Bundle();
                        bundle.putString("id", mobile);
                        bundle.putString("item_name", name);
                        foodList.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, foodList, null).addToBackStack(null).commit();

                    }
                });
                search_bar_layout.setVisibility(View.VISIBLE);
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        TextView viewcart=view.findViewById(R.id.view_cart);
        viewcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mobile.length()<11){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new Login(),null).addToBackStack(null).commit();
                    Toast.makeText(getActivity(),"Please Login",Toast.LENGTH_SHORT).show();
                }else{
                    CartFragment mycart=new CartFragment();
                    Bundle bundle1=new Bundle();
                    bundle1.putString("mobile",mobile);
                    mycart.setArguments(bundle1);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,mycart,null).addToBackStack(null).commit();
                }
            }
        });

        TextView item_couter=view.findViewById(R.id.cart_counter1);
        if(mobile.length()==11) {
            databaseReference.child("Cart_List").child(mobile).child("list").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int i = 0;
                    if(snapshot.exists()){
                        i=(int) snapshot.getChildrenCount();
                    }
                    if(i==0){
                        item_couter.setVisibility(View.GONE);
                    }else {
                    item_couter.setVisibility(View.VISIBLE);
                    item_couter.setText(String.valueOf(i));}

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        } else if (type.equals("Admin")) {
            db_home_show.setVisibility(View.GONE);
            customer_home_show.setVisibility(View.GONE);
            Admin_home_show.setVisibility(View.VISIBLE);
            TextView application_btn=view.findViewById(R.id.application_btn);
            application_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new Application_Admin(),null).addToBackStack(null).commit();
                }
            });
            TextView app_counter=view.findViewById(R.id.aplication_counter);
            databaseReference.child("DB Application").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int i = 0;
                    if(snapshot.exists()){
                        i=(int) snapshot.getChildrenCount()-1;
                    }
                    if(i==0){
                       app_counter.setVisibility(View.GONE);
                    }else {
                        app_counter.setVisibility(View.VISIBLE);
                       app_counter.setText(String.valueOf(i));}

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else if(type.equals("Delivery")){
            customer_home_show.setVisibility(View.GONE);
            Admin_home_show.setVisibility(View.GONE);
            db_home_show.setVisibility(View.VISIBLE);
        }

        else{
            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
        }

        //search item
//        MenuItem s=view.findViewById(R.id.menu_item_search);
//        SearchView searchView=(SearchView)s.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                txtSearch(s);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                txtSearch(s);
//                return false;
//            }
//        });
        return view;
    }

    private void txtSearch(String str) {
//        databaseReference.child("food_Item").orderByChild("name").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Get_Menu_Item get_menu_item = dataSnapshot.getValue(Get_Menu_Item.class);
//                    list.add(get_menu_item);
//                }
//                GridAdapter adapter = new GridAdapter(getActivity(),list);
//                HomeGridView.setAdapter(adapter);
//
//    }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//        });
    }
}