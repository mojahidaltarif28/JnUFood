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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jnufood.Food_List_Adapter;
import com.example.jnufood.Get_Food_List;
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
 * Use the {@link FoodList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodList extends Fragment {
    String mobile, item_name;
    GridView gridView;
    LinearLayout progressbar;
    ArrayList<Get_Food_List> list;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FoodList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodList.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodList newInstance(String param1, String param2) {
        FoodList fragment = new FoodList();
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
        View view = inflater.inflate(R.layout.fragment_food_list, container, false);
        // Inflate the layout for this fragment
        LinearLayout search_bar_food_list = view.findViewById(R.id.search_bar_food_list);
        progressbar = view.findViewById(R.id.progress_bar_food_list);
        progressbar.setVisibility(View.VISIBLE);
        search_bar_food_list.setVisibility(View.GONE);
        Bundle bundle = this.getArguments();
        if (getArguments().getString("id") != null || getArguments().getString("item_name") != null) {
            mobile = bundle.getString("id");
            item_name = bundle.getString("item_name");
        }
        gridView = view.findViewById(R.id.food_list_grid_view);
        list = new ArrayList<>();
        databaseReference.child("food_Item1").child(item_name).child("item list1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Get_Food_List get_food_list = dataSnapshot.getValue(Get_Food_List.class);
                    list.add(get_food_list);
                }
                Food_List_Adapter adapter = new Food_List_Adapter(getActivity(), list);
                gridView.setAdapter(adapter);
                adapter.setOnCLickEventFoodList(new Food_List_Adapter.OnCLickEventFoodList() {
                    @Override
                    public void on_click_food_list(String name, String amount, String price, String restaurant, String image,String status) {
                        ShowDetailsCart showDetailsCart = new ShowDetailsCart();
                        if(mobile.equals("")){
                            Toast.makeText(getActivity(), "Please first Login", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new Login(),null).addToBackStack(null).commit();
                        }else if(status.equals("unavailable")){
                            Toast.makeText(getActivity(),"This item is currently unavailable",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Bundle bundle1 = new Bundle();
                            bundle1.putString("name", name);
                            bundle1.putString("net", amount);
                            bundle1.putString("price", price);
                            bundle1.putString("restaurant", restaurant);
                            bundle1.putString("image", image);
                            bundle1.putString("mobile",mobile);
                            showDetailsCart.setArguments(bundle1);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, showDetailsCart, null).addToBackStack(null).commit();
                        }

                    }
                });
                search_bar_food_list.setVisibility(View.VISIBLE);
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        TextView view_cart1=view.findViewById(R.id.view_cart1);
        view_cart1.setOnClickListener(new View.OnClickListener() {
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
        TextView item_couter=view.findViewById(R.id.cart_counter);
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
        SearchView searchView = view.findViewById(R.id.food_list_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                txtSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                txtSearch(s);
                return false;
            }
        });

        return view;
    }

    private void txtSearch(String str) {
        list = new ArrayList<>();
        databaseReference.child("food_Item1").child(item_name).child("item list1").orderByChild("name").startAt(str).endAt(str + "~").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Get_Food_List get_food_list = dataSnapshot.getValue(Get_Food_List.class);
                    list.add(get_food_list);
                }
                Food_List_Adapter adapter = new Food_List_Adapter(getActivity(), list);
                gridView.setAdapter(adapter);
                adapter.setOnCLickEventFoodList(new Food_List_Adapter.OnCLickEventFoodList() {
                    @Override
                    public void on_click_food_list(String name, String amount, String price, String restaurant, String image,String status) {
                        ShowDetailsCart showDetailsCart = new ShowDetailsCart();
                        if(mobile.equals("")){
                            Toast.makeText(getActivity(), "Please first Login", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new Login(),null).addToBackStack(null).commit();
                        }else if(status.equals("unavailable")){
                            Toast.makeText(getActivity(),"This item is currently unavailable",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Bundle bundle1 = new Bundle();
                            bundle1.putString("name", name);
                            bundle1.putString("net", amount);
                            bundle1.putString("price", price);
                            bundle1.putString("restaurant", restaurant);
                            bundle1.putString("image", image);
                            bundle1.putString("mobile",mobile);
                            showDetailsCart.setArguments(bundle1);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, showDetailsCart, null).addToBackStack(null).commit();
                        }

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}