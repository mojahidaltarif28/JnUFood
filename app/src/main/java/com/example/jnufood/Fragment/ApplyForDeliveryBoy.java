package com.example.jnufood.Fragment;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
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
    EditText name, mobile, email, profession, address,restaurant_name,restaurant_mobile,restaurant_email,food_category,restaurant_address;
    TextView image, nid_img, for_db_btn, for_res_btn,restaurant_image;
    LinearLayout db_show,res_show,btn_group;
    Button restaurant_submit_btn;
    ProgressBar restaurant_progressbar;
    public Uri imgUrl, nidUrl,resUrl;
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

    @SuppressLint("MissingInflatedId")
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
        for_db_btn = view.findViewById(R.id.for_db_btn);
        for_res_btn = view.findViewById(R.id.for_res_btn);
        db_show=view.findViewById(R.id.dba_app_form);
        for_db_btn.setBackgroundResource(R.drawable.received_btn);
        res_show=view.findViewById(R.id.application_restaurant_show);

        btn_group=view.findViewById(R.id.btn_group);
        restaurant_name=view.findViewById(R.id.restaurant_name);
        restaurant_mobile=view.findViewById(R.id.restaurant_mobile);
        restaurant_email=view.findViewById(R.id.restaurant_email);
        food_category=view.findViewById(R.id.food_category);
        restaurant_address=view.findViewById(R.id.restaurent_address);
        restaurant_image=view.findViewById(R.id.restaurant_image);
        restaurant_submit_btn=view.findViewById(R.id.restaurant_submit_btn);
        restaurant_progressbar=view.findViewById(R.id.restaurant_apply_progress_bar);
        for_db_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_show.setVisibility(View.VISIBLE);
                res_show.setVisibility(View.GONE);
                for_db_btn.setBackgroundResource(R.drawable.received_btn);
                for_res_btn.setBackgroundResource(R.drawable.btn_bg);
            }
        });
        for_res_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                res_show.setVisibility(View.VISIBLE);
                db_show.setVisibility(View.GONE);
                for_res_btn.setBackgroundResource(R.drawable.received_btn);
                for_db_btn.setBackgroundResource(R.drawable.btn_bg);
            }
        });
        ProgressBar progressBar = view.findViewById(R.id.dba_progress_bar);

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
        restaurant_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 4);
            }
        });
        storage = FirebaseStorage.getInstance();
        dba_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dba_submit_btn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                if (name.getText().toString().isEmpty()) {
                    showError(name, "Please Enter your name");
                    dba_submit_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else if (mobile.getText().toString().length() < 11) {
                    showError(mobile, "Mobile number must be 11 digits");
                    dba_submit_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else if (email.getText().toString().isEmpty()) {
                    showError(email, "Enter correct email!!");
                    dba_submit_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else if (profession.getText().toString().isEmpty()) {
                    showError(profession, "Enter profession");
                    dba_submit_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else if (address.getText().toString().isEmpty()) {
                    showError(address, "Enter your Address!!");
                    dba_submit_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else if (image.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Upload Your Image", Toast.LENGTH_SHORT).show();
                    dba_submit_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else if (nid_img.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Upload Your NID ", Toast.LENGTH_SHORT).show();
                    dba_submit_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else {

                    databaseReference.child("DB Application").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(mobile.getText().toString())) {
                                Toast.makeText(getActivity(), "Already Submitted", Toast.LENGTH_SHORT).show();
                                dba_submit_btn.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            } else {
                                databaseReference.child("Administration").child("Delivery_man").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.hasChild(mobile.getText().toString())) {
                                            showError(mobile, "Mobile Number already Registered");
                                            dba_submit_btn.setVisibility(View.VISIBLE);
                                            progressBar.setVisibility(View.GONE);
                                        } else {
                                            databaseReference.child("Administration").child("Admin").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if (snapshot.hasChild(mobile.getText().toString())) {
                                                        showError(mobile, "Incorrect mobile Number");
                                                        dba_submit_btn.setVisibility(View.VISIBLE);
                                                        progressBar.setVisibility(View.GONE);
                                                    } else {

                                                        final StorageReference reference_img = storage.getReference().child("images").child("DB Application Image").child(name.getText().toString()).child("img");
                                                        final StorageReference reference_nid = storage.getReference().child("images").child("DB Application Image").child(name.getText().toString()).child("nid");
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

                                                                                                btn_group.setVisibility(View.GONE);
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
                            Toast.makeText(getActivity(), "Registration failed database error", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });


       restaurant_submit_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               restaurant_submit_btn.setVisibility(View.GONE);
               restaurant_progressbar.setVisibility(View.VISIBLE);
               if (restaurant_name.getText().toString().isEmpty()) {
                   showError(restaurant_name, "Please Enter Restaurant name");
                   restaurant_submit_btn.setVisibility(View.VISIBLE);
                   restaurant_progressbar.setVisibility(View.GONE);
               } else if (restaurant_mobile.getText().toString().length() < 11) {
                   showError(restaurant_mobile, "Mobile number must be 11 digits");
                   restaurant_submit_btn.setVisibility(View.VISIBLE);
                   restaurant_progressbar.setVisibility(View.GONE);
               } else if (restaurant_email.getText().toString().isEmpty()) {
                   showError(restaurant_email, "Enter correct email!!");
                   restaurant_submit_btn.setVisibility(View.VISIBLE);
                   restaurant_progressbar.setVisibility(View.GONE);
               } else if (food_category.getText().toString().isEmpty()) {
                   showError(food_category, "Enter food category");
                   restaurant_submit_btn.setVisibility(View.VISIBLE);
                   restaurant_progressbar.setVisibility(View.GONE);
               } else if (restaurant_address.getText().toString().isEmpty()) {
                   showError(restaurant_address, "Enter your Address!!");
                   restaurant_submit_btn.setVisibility(View.VISIBLE);
                   restaurant_progressbar.setVisibility(View.GONE);
               } else if (restaurant_image.getText().toString().isEmpty()) {
                   Toast.makeText(getActivity(), "Upload Food Image", Toast.LENGTH_SHORT).show();
                   restaurant_submit_btn.setVisibility(View.VISIBLE);
                   restaurant_progressbar.setVisibility(View.GONE);
               } else {

                   databaseReference.child("Restaurant_Application").addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           if (snapshot.hasChild(restaurant_mobile.getText().toString())) {
                               Toast.makeText(getActivity(), "Already Submitted", Toast.LENGTH_SHORT).show();
                               restaurant_submit_btn.setVisibility(View.VISIBLE);
                               restaurant_progressbar.setVisibility(View.GONE);
                           } else {
                           databaseReference.child("DB Application").addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                   if (snapshot.hasChild(restaurant_mobile.getText().toString())) {
                                       Toast.makeText(getActivity(), "Already Exist", Toast.LENGTH_SHORT).show();
                                       restaurant_submit_btn.setVisibility(View.VISIBLE);
                                       restaurant_progressbar.setVisibility(View.GONE);
                                   } else {
                                       databaseReference.child("Administration").child("Restaurant").addListenerForSingleValueEvent(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(@NonNull DataSnapshot snapshot) {
                                               if (snapshot.hasChild(restaurant_mobile.getText().toString())) {
                                                   showError(restaurant_mobile, "Mobile Number already Registered");
                                                   restaurant_submit_btn.setVisibility(View.VISIBLE);
                                                   restaurant_progressbar.setVisibility(View.GONE);
                                               } else {
                                                   databaseReference.child("Administration").child("Admin").addListenerForSingleValueEvent(new ValueEventListener() {
                                                       @Override
                                                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                           if (snapshot.hasChild(restaurant_mobile.getText().toString())) {
                                                               showError(restaurant_mobile, "Incorrect mobile Number");
                                                               restaurant_submit_btn.setVisibility(View.VISIBLE);
                                                               restaurant_progressbar.setVisibility(View.GONE);
                                                           } else {

                                                               final StorageReference reference_img = storage.getReference().child("images").child("Restaurant Application Image").child(restaurant_image.getText().toString()).child("img");
                                                               reference_img.putFile(resUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                   @Override
                                                                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                       reference_img.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                           @Override
                                                                           public void onSuccess(Uri uri_img) {
                                                                               databaseReference.child("Restaurant_Application").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                   @Override
                                                                                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                       databaseReference.child("Restaurant_Application").child(restaurant_mobile.getText().toString()).child("restaurant_name").setValue(restaurant_name.getText().toString());
                                                                                       databaseReference.child("Restaurant_Application").child(restaurant_mobile.getText().toString()).child("mobile").setValue(restaurant_mobile.getText().toString());
                                                                                       databaseReference.child("Restaurant_Application").child(restaurant_mobile.getText().toString()).child("email").setValue(restaurant_email.getText().toString());
                                                                                       databaseReference.child("Restaurant_Application").child(restaurant_mobile.getText().toString()).child("Food_category").setValue(food_category.getText().toString());
                                                                                       databaseReference.child("Restaurant_Application").child(restaurant_mobile.getText().toString()).child("address").setValue(restaurant_address.getText().toString());
                                                                                       databaseReference.child("Restaurant_Application").child(restaurant_mobile.getText().toString()).child("image").setValue(uri_img.toString());
//

                                                                                       btn_group.setVisibility(View.GONE);
                                                                                       restaurant_submit_btn.setVisibility(View.VISIBLE);
                                                                                       restaurant_progressbar.setVisibility(View.GONE);
                                                                                       res_show.setVisibility(View.GONE);
                                                                                       dba_msg.setVisibility(View.VISIBLE);
                                                                                   }

                                                                                   @Override
                                                                                   public void onCancelled(@NonNull DatabaseError error) {

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
                                   Toast.makeText(getActivity(), "Registration failed database error", Toast.LENGTH_SHORT).show();

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
        if (requestCode == 4 && resultCode == RESULT_OK && data != null) {
            resUrl = data.getData();
            restaurant_image.setText(data.getData().toString());
        }


    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}
