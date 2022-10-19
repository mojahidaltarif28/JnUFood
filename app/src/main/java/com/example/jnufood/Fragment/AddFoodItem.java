package com.example.jnufood.Fragment;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jnufood.Get_Food_Item_recycleModal;
import com.example.jnufood.Get_Food_Item_recycle_Adapter;
import com.example.jnufood.MainActivity;
import com.example.jnufood.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFoodItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFoodItem extends Fragment {
    private FirebaseStorage storage=FirebaseStorage.getInstance();
    private FirebaseDatabase database;
    TextView add_btn, back_btn, save_btn, upload_image;
    ImageView image;
    ProgressBar progressBar;
    EditText name, price, net, restaurant;
    LinearLayout food_list_show;
    ScrollView food_list_add_show;
    RecyclerView recyclerView;
    ConstraintLayout search_bar_food;
    Get_Food_Item_recycle_Adapter get_food_item_recycle_adapter;
    String menu_name,mobile;
    public Uri imgUrl, uri1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFoodItem() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFoodItem.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFoodItem newInstance(String param1, String param2) {
        AddFoodItem fragment = new AddFoodItem();
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
        View view = inflater.inflate(R.layout.fragment_add_food_item, container, false);
        // Inflate the layout for this fragment
        Bundle bundle = this.getArguments();
        menu_name = bundle.getString("item_name");
        mobile=bundle.getString("mobile");

        search_bar_food = view.findViewById(R.id.search_bar_food);
        back_btn = view.findViewById(R.id.arrow_back_btn_food);
        add_btn = view.findViewById(R.id.add_food_item_btn_ad);
        food_list_show = view.findViewById(R.id.food_item_layout);
        food_list_add_show = view.findViewById(R.id.add_food_item_layout);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                food_list_show.setVisibility(View.GONE);
                search_bar_food.setVisibility(View.GONE);
                food_list_add_show.setVisibility(View.VISIBLE);
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                food_list_add_show.setVisibility(View.GONE);
                food_list_show.setVisibility(View.VISIBLE);
                search_bar_food.setVisibility(View.VISIBLE);

            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.add_food_item_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<Get_Food_Item_recycleModal> options =
                new FirebaseRecyclerOptions.Builder<Get_Food_Item_recycleModal>()
                        .setQuery(FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/").child("food_Item").child(menu_name).child("item list"), Get_Food_Item_recycleModal.class)
                        .build();
        get_food_item_recycle_adapter = new Get_Food_Item_recycle_Adapter(options);
        recyclerView.setAdapter(get_food_item_recycle_adapter);
        get_food_item_recycle_adapter.setOnclickEvent(new Get_Food_Item_recycle_Adapter.OnclickEventAddFoodItem() {
            @Override
            public void on_food_click(String name, String photo, String price, String net, String restaurant) {
                Toast.makeText(getActivity(), name + " " + price + " " + net + " " + restaurant, Toast.LENGTH_SHORT).show();
            }
        });
        name = view.findViewById(R.id.name_add_food_item);
        price = view.findViewById(R.id.price_add_food_item);
        net = view.findViewById(R.id.net_add_food_item);
        restaurant = view.findViewById(R.id.restaurant_add_food_item);
        upload_image = view.findViewById(R.id.upload_add_food_image);
        save_btn = view.findViewById(R.id.add_food_save_btn);
        progressBar = view.findViewById(R.id.add_food_item_progressbar);
        image = view.findViewById(R.id.add_food_item_iv);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_btn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                if (name.getText().toString().isEmpty()) {
                    showError(name, "Enter the Item name");
                    save_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else if (price.getText().toString().isEmpty()) {
                    showError(price, "Enter price");
                    save_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else if (net.getText().toString().isEmpty()) {
                    showError(net, "Enter net amount of this item");
                    save_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else if (restaurant.getText().toString().isEmpty()) {
                    showError(restaurant, "Enter restaurant name");
                    save_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else if (upload_image.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Upload image for this item", Toast.LENGTH_SHORT).show();
                    save_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else {
                    final DatabaseReference databaseReference = database.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/");
                    final StorageReference reference = storage.getReferenceFromUrl("gs://jnufood.appspot.com/").child("images").child("Food Item Image").child(name.getText().toString()+"%"+price.getText().toString()+"%"+net.getText().toString()+"%"+restaurant.getText().toString()).child("img");

                    databaseReference.child("food_Item").child(menu_name).child("item list").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(name.getText().toString()) &&
                                    snapshot.hasChild(price.getText().toString())
                                    && snapshot.hasChild(net.getText().toString()) && snapshot.hasChild(restaurant.getText().toString())
                            ) {
                                Toast.makeText(getActivity(), "This item already exits", Toast.LENGTH_SHORT).show();
                                save_btn.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }else {
                                reference.putFile(imgUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                databaseReference.child("food_Item").child(menu_name).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        databaseReference.child("food_Item").child(menu_name).child("item list").child(name.getText().toString()).child("name").setValue(name.getText().toString());
                                                        databaseReference.child("food_Item").child(menu_name).child("item list").child(name.getText().toString()).child("price").setValue(price.getText().toString());
                                                        databaseReference.child("food_Item").child(menu_name).child("item list").child(name.getText().toString()).child("amount").setValue(net.getText().toString());
                                                        databaseReference.child("food_Item").child(menu_name).child("item list").child(name.getText().toString()).child("photo").setValue(uri.toString());
                                                        databaseReference.child("food_Item").child(menu_name).child("item list").child(name.getText().toString()).child("restaurant").setValue(restaurant.getText().toString());
                                                        Toast.makeText(getActivity(),"Successfully Added",Toast.LENGTH_SHORT).show();
                                                        save_btn.setVisibility(View.VISIBLE);
                                                        progressBar.setVisibility(View.GONE);
                                                        image.setImageResource(R.drawable.fastfood);
                                                        name.setText("");
                                                        price.setText("");
                                                        net.setText("");
                                                        restaurant.setText("");
                                                        upload_image.setText("");
                                                        Intent in=new Intent(getActivity(), MainActivity.class);
                                                        in.putExtra("login_code","-50");
                                                        in.putExtra("mobile",mobile);
                                                        in.putExtra("type","Admin");
                                                        startActivity(in);
                                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new Add_Menu_Item()).commit();


                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {
                                                        save_btn.setVisibility(View.VISIBLE);
                                                        progressBar.setVisibility(View.GONE);
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });


        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });

        SearchView searchView = view.findViewById(R.id.search_food_item_ad);
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
        FirebaseRecyclerOptions<Get_Food_Item_recycleModal> options =
                new FirebaseRecyclerOptions.Builder<Get_Food_Item_recycleModal>()
                        .setQuery(FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/").child("food_Item").
                                child(menu_name).child("item list").orderByChild("name").startAt(str).endAt(str + "~"), Get_Food_Item_recycleModal.class)
                        .build();
        get_food_item_recycle_adapter = new Get_Food_Item_recycle_Adapter(options);
        get_food_item_recycle_adapter.startListening();
        recyclerView.setAdapter(get_food_item_recycle_adapter);
        get_food_item_recycle_adapter.setOnclickEvent(new Get_Food_Item_recycle_Adapter.OnclickEventAddFoodItem() {
            @Override
            public void on_food_click(String name, String photo, String price, String net, String restaurant) {
                Toast.makeText(getActivity(), name + " " + price + " " + net + " " + restaurant, Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imgUrl = data.getData();
            image.setImageURI(imgUrl);
            upload_image.setText(data.getData().toString());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        get_food_item_recycle_adapter.startListening();

    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}