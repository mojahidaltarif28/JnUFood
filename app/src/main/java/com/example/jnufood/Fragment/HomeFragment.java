package com.example.jnufood.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.jnufood.DB_Adapter;
import com.example.jnufood.DB_Modal;
import com.example.jnufood.GetFoodOrderTableAdapter;
import com.example.jnufood.GetFoodOrderTableModel;
import com.example.jnufood.Get_Menu_Item;
import com.example.jnufood.Get_My_Cart_Modal;
import com.example.jnufood.GridAdapter;
import com.example.jnufood.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    GetFoodOrderTableAdapter getFoodOrderTableAdapter;
    GridView HomeGridView;
    LinearLayout progressbar;
    DB_Adapter db_adapter;
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
        LinearLayout search_bar_layout = view.findViewById(R.id.search_bar_layout);
        progressbar = view.findViewById(R.id.progress_bar_home);
        progressbar.setVisibility(View.VISIBLE);
        search_bar_layout.setVisibility(View.GONE);
        Bundle bundle = this.getArguments();
        mobile = bundle.getString("otp_id");
        type = bundle.getString("type");

        LinearLayout customer_home_show = view.findViewById(R.id.home_customer_view);
        LinearLayout Admin_home_show = view.findViewById(R.id.home_admin_view);
        LinearLayout db_home_show = view.findViewById(R.id.home_db_view);
        LinearLayout restaurant_home_show = view.findViewById(R.id.home_restaurant_view);
        // Toast.makeText(getActivity(), "login:" + mobile + " type:" + type, Toast.LENGTH_SHORT).show();
//       gridview part
        if (type.equals("Customer")) {
            Admin_home_show.setVisibility(View.GONE);
            db_home_show.setVisibility(View.GONE);
            customer_home_show.setVisibility(View.VISIBLE);
            restaurant_home_show.setVisibility(View.GONE);
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
            TextView viewcart = view.findViewById(R.id.view_cart);
            viewcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mobile.length() < 11) {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Login(), null).addToBackStack(null).commit();
                        Toast.makeText(getActivity(), "Please Login", Toast.LENGTH_SHORT).show();
                    } else {
                        CartFragment mycart = new CartFragment();
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("mobile", mobile);
                        mycart.setArguments(bundle1);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, mycart, null).addToBackStack(null).commit();
                    }
                }
            });

            TextView item_couter = view.findViewById(R.id.cart_counter1);
            if (mobile.length() == 11) {
                databaseReference.child("Cart_List").child(mobile).child("list").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int i = 0;
                        if (snapshot.exists()) {
                            i = (int) snapshot.getChildrenCount();
                        }
                        if (i == 0) {
                            item_couter.setVisibility(View.GONE);
                        } else {
                            item_couter.setVisibility(View.VISIBLE);
                            item_couter.setText(String.valueOf(i));
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            SearchView searchView = view.findViewById(R.id.menu_item_search);
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

        } else if (type.equals("Admin")) {
            db_home_show.setVisibility(View.GONE);
            customer_home_show.setVisibility(View.GONE);
            Admin_home_show.setVisibility(View.VISIBLE);
            restaurant_home_show.setVisibility(View.GONE);
            TextView application_btn = view.findViewById(R.id.application_btn);
            application_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Application_Admin application_admin=new Application_Admin();
                    Bundle bundle1=new Bundle();
                    bundle1.putString("mobile",mobile);
                    application_admin.setArguments(bundle1);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, application_admin, null).addToBackStack(null).commit();
                }
            });
            TextView app_counter = view.findViewById(R.id.aplication_counter);
            databaseReference.child("DB Application").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot1) {
                    databaseReference.child("Restaurant_Application").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot2) {
                            int i = 0,j=0;
                            if (snapshot1.exists()) {
                                i = (int) snapshot1.getChildrenCount() ;
                            }
                            if(snapshot2.exists()){
                                j = (int) snapshot2.getChildrenCount() ;
                            }
                            if (i == 0&&j==0) {
                                app_counter.setVisibility(View.GONE);
                            } else {
                                app_counter.setVisibility(View.VISIBLE);
                                app_counter.setText(String.valueOf(i+j));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else if (type.equals("Delivery")) {
            customer_home_show.setVisibility(View.GONE);
            Admin_home_show.setVisibility(View.GONE);
            db_home_show.setVisibility(View.VISIBLE);
            restaurant_home_show.setVisibility(View.GONE);
           RecyclerView recyclerView=view.findViewById(R.id.recycle_view_order_request_db);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            FirebaseRecyclerOptions<DB_Modal> options = new FirebaseRecyclerOptions.Builder<DB_Modal>().setQuery(FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/").child("Order_DB"), DB_Modal.class).build();
            options.getSnapshots();
            db_adapter=new DB_Adapter(options);
            recyclerView.setAdapter(db_adapter);
            db_adapter.setOnclickEvent(new DB_Adapter.OnClickDB() {
                @Override
                public void onClickAccept(String db, String delivery_address, String delivery_mobile, String mobile_c, String peak_address, String status) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("status", "picked");
                    databaseReference.child("Order_Table_Restaurant").child(peak_address).child(mobile_c).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            databaseReference.child("Order_DB_Accept").child(mobile).child(mobile_c).child("status").setValue("processed");
                            databaseReference.child("Order_DB_Accept").child(mobile).child(mobile_c).child("db").setValue(mobile);
                            databaseReference.child("Order_DB_Accept").child(mobile).child(mobile_c).child("mobile").setValue(mobile_c);
                            databaseReference.child("Order_DB_Accept").child(mobile).child(mobile_c).child("delivery_address").setValue(delivery_address);
                            databaseReference.child("Order_DB_Accept").child(mobile).child(mobile_c).child("delivery_mobile").setValue(delivery_mobile);
                            databaseReference.child("Order_DB_Accept").child(mobile).child(mobile_c).child("peak_address").setValue(peak_address);
                            databaseReference.child("Order_DB").child(mobile_c).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getActivity(),"Accepted",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });
                }
            });
            db_adapter.startListening();

        }
        else if(type.equals("Restaurant")){
            restaurant_home_show.setVisibility(View.VISIBLE);
            customer_home_show.setVisibility(View.GONE);
            Admin_home_show.setVisibility(View.GONE);
            customer_home_show.setVisibility(View.GONE);

            databaseReference.child("Administration").child("Restaurant").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String restaurant_get=snapshot.child(mobile).child("restaurant_name").getValue(String.class);
                    recyclerView=view.findViewById(R.id.recycle_view_order_request_retaurant);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    FirebaseRecyclerOptions<GetFoodOrderTableModel> options = new FirebaseRecyclerOptions.Builder<GetFoodOrderTableModel>().setQuery(FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/").child("Order_Table_Restaurant").child(restaurant_get), GetFoodOrderTableModel.class).build();
                    options.getSnapshots();
                    getFoodOrderTableAdapter=new GetFoodOrderTableAdapter(options);
                    recyclerView.setAdapter(getFoodOrderTableAdapter);
                    getFoodOrderTableAdapter.setOnClickEvent(new GetFoodOrderTableAdapter.OnClickEvent_FOT_restaurant() {
                        @Override
                        public void onViewClick(String mobile, String payment, String d_address) {
                            Bundle bundle1=new Bundle();
                            ViewOrderTableRestaurant viewOrderTableRestaurant=new ViewOrderTableRestaurant();
                            bundle1.putString("mobile",mobile);
                            bundle1.putString("restaurant",restaurant_get);
                            viewOrderTableRestaurant.setArguments(bundle1);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,viewOrderTableRestaurant,null).addToBackStack(null).commit();
                        }

                        @Override
                        public void acceptClick(String mobile) {
                            HashMap hashMap = new HashMap();
                            hashMap.put("status", "processing");
                            databaseReference.child("Order_Table").child(mobile).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    Toast.makeText(getActivity(),"Accepted",Toast.LENGTH_SHORT).show();
                                }
                            });
                            databaseReference.child("Order_Table_Restaurant").child(restaurant_get).child(mobile).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    Toast.makeText(getActivity(),"Accepted",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void completeClick(String mobile,String d_address,String d_mobile,String payment_amount) {
                            HashMap hashMap = new HashMap();
                            hashMap.put("status", "complete");
                            databaseReference.child("Order_Table_Restaurant").child(restaurant_get).child(mobile).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    Toast.makeText(getActivity(),"complete",Toast.LENGTH_SHORT).show();
                                }
                            });
                            databaseReference.child("Order_DB").child(mobile).child("status").setValue("processed");
                            databaseReference.child("Order_DB").child(mobile).child("db").setValue("no");
                            databaseReference.child("Order_DB").child(mobile).child("mobile").setValue(mobile);
                            databaseReference.child("Order_DB").child(mobile).child("delivery_address").setValue(d_address);
                            databaseReference.child("Order_DB").child(mobile).child("delivery_mobile").setValue(d_mobile);
                            databaseReference.child("Order_DB").child(mobile).child("payment_amount").setValue(payment_amount);
                            databaseReference.child("Order_DB").child(mobile).child("peak_address").setValue(restaurant_get);
                        }
                    });
                    getFoodOrderTableAdapter.startListening();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
        else {
            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
        }

        //  search item

        return view;
    }

    private void txtSearch(String str) {
        list = new ArrayList<>();
        databaseReference.child("food_Item").orderByChild("name").startAt(str).endAt(str + "~").addValueEventListener(new ValueEventListener() {
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
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,  foodList, null).addToBackStack(null).commit();

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}