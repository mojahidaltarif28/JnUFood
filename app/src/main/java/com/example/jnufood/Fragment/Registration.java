package com.example.jnufood.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jnufood.MainActivity;
import com.example.jnufood.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Registration#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Registration extends Fragment {
    EditText confirm_show, password_show;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText phone, input_password, confirm_password, email, name, dept;
    Button btn_register;
    // TODO: Rename and change types of parameters
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/");
    private String mParam1;
    private String mParam2;

    public Registration() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Registration.
     */
    // TODO: Rename and change types and number of parameters
    public static Registration newInstance(String param1, String param2) {
        Registration fragment = new Registration();
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
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        //password and phone validation and firebase connection

        phone = view.findViewById(R.id.registration_phone);
        email = view.findViewById(R.id.input_email);
        input_password = view.findViewById(R.id.input_password);
        confirm_password = view.findViewById(R.id.confirm_password);
        name = view.findViewById(R.id.input_name);
        dept = view.findViewById(R.id.input_dept);
        btn_register = view.findViewById(R.id.btn_register);

        final ProgressBar progressBar = view.findViewById(R.id.progress_bar_r);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone_s = phone.getText().toString();
                String email_s = email.getText().toString();
                String input_password_s = input_password.getText().toString();
                String confirm_password_s = confirm_password.getText().toString();
                String name_s = name.getText().toString();
                String dept_s = dept.getText().toString();


                if (name_s.isEmpty()) {
                    showError(name, "Please enter your name");
                } else if (phone_s.isEmpty() || phone_s.length() != 11) {
                    showError(phone, "Phone number is not valid");
                } else if (!email_s.isEmpty() && (!email_s.contains(".") || !email_s.contains("@"))) {
                    showError(email, "Email is not valid");
                } else if (dept_s.isEmpty()) {
                    showError(dept, "Please enter your department");
                } else if (input_password_s.isEmpty() || input_password_s.length() < 6) {
                    showError(input_password, "password must be at least 6 characters");
                } else if (confirm_password_s.isEmpty() || !confirm_password_s.equals(input_password_s)) {
                    showError(confirm_password, "Password does not match");
                } else {

                    databaseReference.child("Customer").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(phone_s)) {
                                showError(phone, "Mobile number already registered");

                            } else {
                                //pass all value to the otp fragment

                                progressBar.setVisibility(view.VISIBLE);
                                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                        "+88" + phone.getText().toString(),
                                        60,
                                        TimeUnit.SECONDS,
                                        getActivity(),
                                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                            @Override
                                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                                progressBar.setVisibility(view.GONE);
                                            }

                                            @Override
                                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                                progressBar.setVisibility(view.GONE);
                                                Toast.makeText(getActivity(), "Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
                                            }

                                            @Override
                                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                progressBar.setVisibility(view.GONE);
                                                Toast.makeText(getActivity(), "OTP send to your mobile,please check", Toast.LENGTH_SHORT).show();
                                                Bundle bundle = new Bundle();
                                                bundle.putString("name", name_s);
                                                bundle.putString("mobile", phone_s);
                                                bundle.putString("email", email_s);
                                                bundle.putString("dept", dept_s);
                                                bundle.putString("password", input_password_s);
                                                bundle.putString("otp_id", verificationId);
                                                OTP_Verify otp_verify = new OTP_Verify();
                                                otp_verify.setArguments(bundle);
                                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, otp_verify, null).addToBackStack(null).commit();
                                            }
                                        }
                                );
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


        //password show hide part
        password_show = view.findViewById(R.id.input_password);
        confirm_show = view.findViewById(R.id.confirm_password);

        password_show.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= password_show.getRight() - password_show.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = password_show.getSelectionEnd();
                        if (password_show.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                            password_show.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            password_show.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_security, 0, R.drawable.ic_show, 0);

                        } else {
                            password_show.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                            password_show.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_security, 0, R.drawable.ic_hide, 0);

                        }
                        password_show.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
        confirm_show.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= confirm_show.getRight() - confirm_show.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = confirm_show.getSelectionEnd();
                        if (confirm_show.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                            confirm_show.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            confirm_show.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_security, 0, R.drawable.ic_show, 0);

                        } else {
                            confirm_show.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                            confirm_show.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_security, 0, R.drawable.ic_hide, 0);

                        }
                        confirm_show.setSelection(selection);
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
