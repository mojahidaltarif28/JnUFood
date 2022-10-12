package com.example.jnufood.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jnufood.MainActivity;
import com.example.jnufood.R;
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
 * Use the {@link ApplyForDeliveryBoy#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplyForDeliveryBoy extends Fragment {
    EditText name, mobile, email, profession, address;
    TextView image, nid_img;
    public Uri imgUrl, nidUrl;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/");
    private FirebaseStorage storage;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ApplyForDeliveryBoy() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApplyForDeliveryBoy.
     */
    // TODO: Rename and change types and number of parameters
    public static ApplyForDeliveryBoy newInstance(String param1, String param2) {
        ApplyForDeliveryBoy fragment = new ApplyForDeliveryBoy();
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
        View view = inflater.inflate(R.layout.fragment_apply_for_delivery_boy, container, false);
        // Inflate the layout for this fragment
        LinearLayout dba_form = view.findViewById(R.id.dba_app_form);
        LinearLayout dba_msg = view.findViewById(R.id.dba_success_msg);
        Button dba_submit_btn = view.findViewById(R.id.dba_btn);
        name = view.findViewById(R.id.dba_name);
        mobile = view.findViewById(R.id.dba_mobile);
        email = view.findViewById(R.id.dba_email);
        profession = view.findViewById(R.id.dba_profession);
        address = view.findViewById(R.id.dba_address);
        image = view.findViewById(R.id.dba_image);
        nid_img = view.findViewById(R.id.dba_nid);
        ProgressBar progressBar=view.findViewById(R.id.dba_progress_bar);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });
        nid_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 3);
            }
        });
        storage=FirebaseStorage.getInstance();
        dba_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dba_submit_btn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                if(name.getText().toString().isEmpty()){
                    showError(name,"Please Enter your name");
                    dba_submit_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }else if(mobile.getText().toString().length()<11){
                    showError(mobile,"Mobile number must be 11 digits");
                    dba_submit_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }else if(email.getText().toString().isEmpty()){
                    showError(email,"Enter correct email!!");
                    dba_submit_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }else if(profession.getText().toString().isEmpty()){
                    showError(profession,"Enter profession");
                    dba_submit_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }else if(address.getText().toString().isEmpty()){
                    showError(address,"Enter your Address!!");
                    dba_submit_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }else if(image.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Upload Your Image", Toast.LENGTH_SHORT).show();
                    dba_submit_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }else if(nid_img.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Upload Your NID ", Toast.LENGTH_SHORT).show();
                    dba_submit_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }else {

                    databaseReference.child("DB Application").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(mobile.getText().toString())){
                                Toast.makeText(getActivity(),"Already Submitted",Toast.LENGTH_SHORT).show();
                                dba_submit_btn.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }
                            else {
                                databaseReference.child("Administration").child("Delivery_man").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.hasChild(mobile.getText().toString())){
                                           showError(mobile,"Mobile Number already Registered");
                                            dba_submit_btn.setVisibility(View.VISIBLE);
                                            progressBar.setVisibility(View.GONE);
                                        }else {
                                            databaseReference.child("Administration").child("Admin").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if(snapshot.hasChild(mobile.getText().toString())){
                                                        showError(mobile,"Incorrect mobile Number");
                                                          dba_submit_btn.setVisibility(View.VISIBLE);
                                                        progressBar.setVisibility(View.GONE);
                                                    }else {

                                                        final StorageReference reference_img=storage.getReference().child("images").child("DB Application Image").child(name.getText().toString()).child("img");
                                                        final StorageReference reference_nid=storage.getReference().child("images").child("DB Application Image").child(name.getText().toString()).child("nid");
                                                        reference_img.putFile(imgUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                reference_nid.putFile(nidUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                    @Override
                                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                        reference_img.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                            @Override
                                                                            public void onSuccess(Uri uri_img) {
                                                                                reference_nid.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                    @Override
                                                                                    public void onSuccess(Uri uri_nid) {
                                                                                        databaseReference.child("DB Application").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                            @Override
                                                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                                databaseReference.child("DB Application").child(mobile.getText().toString()).child("name").setValue(name.getText().toString());
                                                                                                databaseReference.child("DB Application").child(mobile.getText().toString()).child("mobile").setValue(mobile.getText().toString());
                                                                                                databaseReference.child("DB Application").child(mobile.getText().toString()).child("email").setValue(email.getText().toString());
                                                                                                databaseReference.child("DB Application").child(mobile.getText().toString()).child("profession").setValue(profession.getText().toString());
                                                                                                databaseReference.child("DB Application").child(mobile.getText().toString()).child("address").setValue(address.getText().toString());
                                                                                                databaseReference.child("DB Application").child(mobile.getText().toString()).child("image").setValue(uri_img.toString());
                                                                                                databaseReference.child("DB Application").child(mobile.getText().toString()).child("nid").setValue(uri_nid.toString());


                                                                                                dba_submit_btn.setVisibility(View.VISIBLE);
                                                                                                progressBar.setVisibility(View.GONE);
                                                                                                dba_form.setVisibility(View.GONE);
                                                                                                dba_msg.setVisibility(View.VISIBLE);
                                                                                            }

                                                                                            @Override
                                                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                                                            }
                                                                                        });
                                                                                    }
                                                                                });
                                                                            }
                                                                        });
                                                                    }
                                                                });
                                                                //
                                                            }
                                                        });

                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                            //else

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getActivity(),"Registration failed database error",Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imgUrl = data.getData();
            image.setText(data.getData().toString());
        }
        if (requestCode == 3 && resultCode == RESULT_OK && data != null) {
            nidUrl = data.getData();
            nid_img.setText(data.getData().toString());
        }

    }
    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}
