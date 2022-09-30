package com.example.jnufood.Fragment;

import static com.example.jnufood.R.id.progress_send_p;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import com.example.jnufood.R;
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
 * Use the {@link Forgot_password_otp_send#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Forgot_password_otp_send extends Fragment {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/");
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Forgot_password_otp_send() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Forgot_password_otp_send.
     */
    // TODO: Rename and change types and number of parameters
    public static Forgot_password_otp_send newInstance(String param1, String param2) {
        Forgot_password_otp_send fragment = new Forgot_password_otp_send();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("MissingInflatedId")
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
        View view = inflater.inflate(R.layout.fragment_forgot_password_otp_send, container, false);
        // Inflate the layout for this fragment
        EditText mobile = view.findViewById(R.id.forgot_password_mobile);
        Button send_btn = view.findViewById(R.id.forgot_send_btn);
        TextView incorrect_mobile_number = view.findViewById(R.id.incorrect_mobile_number);
        ProgressBar progressBar = view.findViewById(progress_send_p);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mobile.getText().toString().isEmpty()) {
                    showError(mobile, "Please Enter Your Mobile Number");
                } else if (mobile.getText().toString().length() < 11) {
                    showError(mobile, "Mobile Number must be 11 digit");
                } else {
                    databaseReference.child("Customer").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(mobile.getText().toString())) {
                                incorrect_mobile_number.setVisibility(View.GONE);
                                progressBar.setVisibility(view.VISIBLE);
                                send_btn.setVisibility(View.GONE);
                                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                        "+88" + mobile.getText().toString(),
                                        60,
                                        TimeUnit.SECONDS,
                                        getActivity(),
                                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                            @Override
                                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                                progressBar.setVisibility(view.GONE);
                                                send_btn.setVisibility(view.VISIBLE);
                                            }

                                            @Override
                                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                                progressBar.setVisibility(view.GONE);
                                                send_btn.setVisibility(view.VISIBLE);
                                                Toast.makeText(getActivity(), "Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
                                            }

                                            @Override
                                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                progressBar.setVisibility(view.GONE);
                                                send_btn.setVisibility(view.INVISIBLE);
                                                Toast.makeText(getActivity(), "OTP send to your mobile,please check", Toast.LENGTH_SHORT).show();
                                                Bundle bundle = new Bundle();
                                                bundle.putString("mobile", mobile.getText().toString());
                                                bundle.putString("otp_id", verificationId);
                                                Forgot_password_change_password forgot_password_change_password=new Forgot_password_change_password();
                                                forgot_password_change_password.setArguments(bundle);
                                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, forgot_password_change_password).commit();
                                            }
                                        }
                                );
                            } else {
                                incorrect_mobile_number.setVisibility(View.VISIBLE);
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

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}