package com.example.jnufood.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.example.jnufood.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link My_Account#newInstance} factory method to
 * create an instance of this fragment.
 */
public class My_Account extends Fragment {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/");
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String mobile;
    String password;

    public My_Account() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment My_Account.
     */
    // TODO: Rename and change types and number of parameters
    public static My_Account newInstance(String param1, String param2) {
        My_Account fragment = new My_Account();
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
        View view = inflater.inflate(R.layout.fragment_my__account, container, false);
        // Inflate the layout for this fragment

        Bundle bundle = this.getArguments();
        if (getArguments().getString("otp_id") != null) {
            mobile = bundle.getString("otp_id");
        }
        TextView close_edit_btn = view.findViewById(R.id.close_edit_btn);
        LinearLayout my_ac_show = view.findViewById(R.id.my_Account_show);
        TextView ac_name = view.findViewById(R.id.ac_name);
        TextView ac_mobile = view.findViewById(R.id.ac_mobile);
        TextView ac_email = view.findViewById(R.id.ac_email);
        TextView ac_dept = view.findViewById(R.id.ac_dept);
        //edit part
        LinearLayout ac_edit_layout = view.findViewById(R.id.ac_edit_part);
        EditText ac_name_edit = view.findViewById(R.id.ac_name_edit);
        EditText ac_email_edit = view.findViewById(R.id.ac_email_edit);
        EditText ac_dept_edit = view.findViewById(R.id.ac_dept_edit);
        EditText ac_current_password = view.findViewById(R.id.ac_current_password);
        LinearLayout ac_new_password_layout = view.findViewById(R.id.ac_new_password_layout);
        EditText ac_new_password = view.findViewById(R.id.ac_new_password);
        LinearLayout ac_repeat_password_layout = view.findViewById(R.id.ac_confirm_password_layout);
        EditText ac_repeat_password = view.findViewById(R.id.ac_confirm_password);
        Button ac_save_btn = view.findViewById(R.id.ac_save_btn);
        ProgressBar ac_progressbar = view.findViewById(R.id.progress_bar_ac);
        TextView ac_edit_btn = view.findViewById(R.id.ac_edit_btn);


        close_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_ac_show.setVisibility(View.VISIBLE);
                ac_edit_layout.setVisibility(View.GONE);

            }
        });
        ac_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ac_edit_layout.setVisibility(View.VISIBLE);
                my_ac_show.setVisibility(View.GONE);
            }
        });
        //get and set value from database
        databaseReference.child("Customer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(mobile)) {
                    final String name = snapshot.child(mobile).child("Name").getValue(String.class);
                    final String email = snapshot.child(mobile).child("Email").getValue(String.class);
                    final String dept = snapshot.child(mobile).child("Department").getValue(String.class);
                    password = snapshot.child(mobile).child("Password").getValue(String.class);
                    ac_name.setText(name);
                    ac_email.setText(email);
                    ac_mobile.setText(mobile);
                    ac_dept.setText(dept);
                    ac_name_edit.setText(name);
                    ac_email_edit.setText(email);
                    ac_dept_edit.setText(dept);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//upadte value to firebase

        ac_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ac_name_edit.getText().toString().isEmpty()) {
                    showError(ac_name_edit, "Please Enter Your Name");
                } else if (ac_dept_edit.getText().toString().isEmpty()) {
                    showError(ac_dept_edit, "Please Enter Your Department Name");
                } else if (!ac_current_password.getText().toString().isEmpty()) {
                    if (!ac_current_password.getText().toString().equals(password)) {
                        showError(ac_current_password, "Incorrect Password");
                    } else if (ac_new_password.getText().toString().length() < 6) {
                        showError(ac_new_password, "Password must be at least 6 characters");
                    } else if (!ac_repeat_password.getText().toString().equals(ac_new_password.getText().toString())) {
                        showError(ac_repeat_password, "Password not matched");
                    } else {
                        ac_save_btn.setVisibility(View.GONE);
                        ac_progressbar.setVisibility(View.VISIBLE);
                        HashMap hashMap = new HashMap();
                        hashMap.put("Department", ac_dept_edit.getText().toString());
                        hashMap.put("Email", ac_email_edit.getText().toString());
                        hashMap.put("Name", ac_name_edit.getText().toString());
                        hashMap.put("Password", ac_repeat_password.getText().toString());
                        databaseReference.child("Customer").child(mobile).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(getActivity(), "Update Successful", Toast.LENGTH_SHORT).show();
                                ac_save_btn.setVisibility(View.VISIBLE);
                                ac_progressbar.setVisibility(View.GONE);
                                Bundle bundle1 = new Bundle();
                                My_Account my_account = new My_Account();
                                bundle1.putString("otp_id", mobile);
                                my_account.setArguments(bundle1);
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, my_account).commit();
                                 }
                        });
                    }
                } else if (!ac_email_edit.getText().toString().isEmpty() && (!ac_email_edit.getText().toString().contains(".") ||
                        !ac_email_edit.getText().toString().contains("@"))) {
                    showError(ac_email_edit, "Invalid Email");
                } else if (!ac_new_password.getText().toString().isEmpty() || !ac_repeat_password.getText().toString().isEmpty()) {
                    showError(ac_current_password, "Enter Your Current Password");
                } else {
                    ac_save_btn.setVisibility(View.GONE);
                    ac_progressbar.setVisibility(View.VISIBLE);
                    HashMap hashMap = new HashMap();
                    hashMap.put("Department", ac_dept_edit.getText().toString());
                    hashMap.put("Email", ac_email_edit.getText().toString());
                    hashMap.put("Name", ac_name_edit.getText().toString());
                    databaseReference.child("Customer").child(mobile).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(getActivity(), "Update Successful", Toast.LENGTH_SHORT).show();
                            ac_save_btn.setVisibility(View.VISIBLE);
                            ac_progressbar.setVisibility(View.GONE);
                            Bundle bundle1 = new Bundle();
                            My_Account my_account = new My_Account();
                            bundle1.putString("otp_id", mobile);
                            my_account.setArguments(bundle1);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, my_account).commit();

                        }
                    });
                }

            }
        });

        //edit onclick password part
        ac_current_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ac_new_password_layout.setVisibility(View.VISIBLE);
                final int Right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= ac_current_password.getRight() - ac_current_password.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = ac_current_password.getSelectionEnd();
                        if (ac_current_password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                            ac_current_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            ac_current_password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_show, 0);

                        } else {
                            ac_current_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                            ac_current_password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_hide, 0);

                        }
                        ac_current_password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        ac_new_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ac_repeat_password_layout.setVisibility(View.VISIBLE);
                final int Right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= ac_new_password.getRight() - ac_new_password.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = ac_new_password.getSelectionEnd();
                        if (ac_new_password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                            ac_new_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            ac_new_password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_show, 0);

                        } else {
                            ac_new_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                            ac_new_password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_hide, 0);

                        }
                        ac_new_password.setSelection(selection);
                        return true;
                    }
                }

                return false;
            }
        });
        ac_repeat_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= ac_repeat_password.getRight() - ac_repeat_password.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = ac_repeat_password.getSelectionEnd();
                        if (ac_repeat_password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                            ac_repeat_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            ac_repeat_password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_show, 0);

                        } else {
                            ac_repeat_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                            ac_repeat_password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_hide, 0);

                        }
                        ac_repeat_password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });


        return view;
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}