package com.example.jnufood.Fragment;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jnufood.Get_Menu_Item_Recycle_Adapter;
import com.example.jnufood.Get_Menu_Item_Recycle_view;
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
 * Use the {@link Add_Menu_Item#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressWarnings("deprecation")
public class Add_Menu_Item extends Fragment {
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    TextView add_btn, back_btn, save_btn, upload_image;
    ImageView image;
    ProgressBar progressBar;
    EditText name;
    String mobile;
    LinearLayout menu_list_show, menu_list_add_show, search_bar;
    RecyclerView recyclerView;
    Get_Menu_Item_Recycle_Adapter get_menu_item_recycle_adapter;
    public Uri imgUrl, uri1;
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add__menu__item, container, false);
        Bundle bundle = this.getArguments();
        mobile = bundle.getString("otp_id");


        recyclerView = (RecyclerView) view.findViewById(R.id.add_menu_item_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<Get_Menu_Item_Recycle_view> options =
                new FirebaseRecyclerOptions.Builder<Get_Menu_Item_Recycle_view>()
                        .setQuery(FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/").child("food_Item"), Get_Menu_Item_Recycle_view.class)
                        .build();

        get_menu_item_recycle_adapter = new Get_Menu_Item_Recycle_Adapter(options);
        recyclerView.setAdapter(get_menu_item_recycle_adapter);
        get_menu_item_recycle_adapter.setOnClickEvent(new Get_Menu_Item_Recycle_Adapter.OnClickEventAdd_Menu_Item() {
            @Override
            public void on_menu_click(String name) {
                Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
                AddFoodItem addFoodItem=new AddFoodItem();
                Bundle bundle1=new Bundle();
                bundle1.putString("item_name",name);
                bundle1.putString("mobile",mobile);
                addFoodItem.setArguments(bundle1);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,addFoodItem , null).addToBackStack(null).commit();
            }
        });


        add_btn = view.findViewById(R.id.add_menu_item_btn_ad);
        back_btn = view.findViewById(R.id.arrow_back_btn);
        save_btn = view.findViewById(R.id.add_menu_save_btn);
        upload_image = view.findViewById(R.id.upload_add_menu_image);
        image = view.findViewById(R.id.add_menu_item_iv);
        name = view.findViewById(R.id.add_menu_item_name);
        progressBar = view.findViewById(R.id.add_menu_item_progressbar);
        menu_list_show = view.findViewById(R.id.menu_item_layout);
        menu_list_add_show = view.findViewById(R.id.add_menu_item_layout);

        search_bar = view.findViewById(R.id.search_bar_add_bar);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu_list_show.setVisibility(View.GONE);
                menu_list_add_show.setVisibility(View.VISIBLE);
                search_bar.setVisibility(View.GONE);
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu_list_show.setVisibility(View.VISIBLE);
                menu_list_add_show.setVisibility(View.GONE);
                search_bar.setVisibility(View.VISIBLE);
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

        storage = FirebaseStorage.getInstance();
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_btn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                if (name.getText().toString().isEmpty()) {
                    showError(name, "Enter the Category name");
                    save_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else if (upload_image.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please Select Image", Toast.LENGTH_SHORT).show();
                    save_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else {
                    final StorageReference reference = storage.getReference().child("images").child("Menu Item Image").child(name.getText().toString()).child("img");
                    final DatabaseReference databaseReference = database.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/");
                    databaseReference.child("food_Item").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(name.getText().toString())) {
                                Toast.makeText(getActivity(), "This Category Already exists??", Toast.LENGTH_SHORT).show();
                                save_btn.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            } else {
                                reference.putFile(imgUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                databaseReference.child("food_Item").addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                        databaseReference.child("food_Item").child(name.getText().toString()).child("name").setValue(name.getText().toString());
                                                        databaseReference.child("food_Item").child(name.getText().toString()).child("photo").setValue(uri.toString());
                                                        save_btn.setVisibility(View.VISIBLE);
                                                        progressBar.setVisibility(View.GONE);
                                                        upload_image.setText("");
                                                        name.setText("");
                                                        Toast.makeText(getActivity(), "Successfully Saved", Toast.LENGTH_SHORT).show();
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
        SearchView searchView = view.findViewById(R.id.search_menu_item_ad);
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
        FirebaseRecyclerOptions<Get_Menu_Item_Recycle_view> options =
                new FirebaseRecyclerOptions.Builder<Get_Menu_Item_Recycle_view>()
                        .setQuery(FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/").child("food_Item").orderByChild("name").startAt(str).endAt(str + "~"), Get_Menu_Item_Recycle_view.class)
                        .build();
        get_menu_item_recycle_adapter = new Get_Menu_Item_Recycle_Adapter(options);
        get_menu_item_recycle_adapter.startListening();
        recyclerView.setAdapter(get_menu_item_recycle_adapter);
        get_menu_item_recycle_adapter.setOnClickEvent(new Get_Menu_Item_Recycle_Adapter.OnClickEventAdd_Menu_Item() {
            @Override
            public void on_menu_click(String name) {
                Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
                AddFoodItem addFoodItem=new AddFoodItem();
                Bundle bundle=new Bundle();
                bundle.putString("item_name",name);
                bundle.putString("mobile",mobile);
                addFoodItem.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, addFoodItem, null).addToBackStack(null).commit();
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
        get_menu_item_recycle_adapter.startListening();

    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

}