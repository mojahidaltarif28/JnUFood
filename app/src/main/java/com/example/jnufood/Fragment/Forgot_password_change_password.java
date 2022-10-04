package com.example.jnufood.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Forgot_password_change_password#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Forgot_password_change_password extends Fragment {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/");
    EditText otp1, otp2, otp3, otp4, otp5, otp6, new_password, confirm_password;
    Button submit;
    LinearLayout wrong_otp;
    TextView counter, resend_txt, resend_txt_btn;
    String otp_id;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Forgot_password_change_password() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Forgot_password_change_password.
     */
    // TODO: Rename and change types and number of parameters
    public static Forgot_password_change_password newInstance(String param1, String param2) {
        Forgot_password_change_password fragment = new Forgot_password_change_password();
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
        View view = inflater.inflate(R.layout.fragment_forgot_password_change_password, container, false);
        otp1 = view.findViewById(R.id.otp1_p);
        otp2 = view.findViewById(R.id.otp2_p);
        otp3 = view.findViewById(R.id.otp3_p);
        otp4 = view.findViewById(R.id.otp4_p);
        otp5 = view.findViewById(R.id.otp5_p);
        otp6 = view.findViewById(R.id.otp6_p);
        submit = view.findViewById(R.id.btn_otp_submit_p);

        next_text_auto();
        //time counter
        counter = view.findViewById(R.id.time_countdown_p);
        otp_countetr();
        wrong_otp = view.findViewById(R.id.wrong_otp_p);
        TextView mobile = view.findViewById(R.id.mobile_p);
        Bundle bundle = this.getArguments();
        String phone = bundle.getString("mobile");
        otp_id = bundle.getString("otp_id");
        mobile.setText(phone);
        LinearLayout new_password_part = view.findViewById(R.id.new_password_part);
        LinearLayout otp_verify_part = view.findViewById(R.id.otp_verify_part);
        final ProgressBar progressBar = view.findViewById(R.id.progress_bar_submit_p);
        final Button btn_submit = view.findViewById(R.id.btn_otp_submit_p);
        new_password = view.findViewById(R.id.new_password_p);
        confirm_password = view.findViewById(R.id.confirm_password_p);

        // resend option
        resend_txt = view.findViewById(R.id.text_resend);
        resend_txt_btn = view.findViewById(R.id.resend_p);
        resend_txt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "Please wait a moment,we will resend OTP to your mobile", Toast.LENGTH_LONG).show();
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+88" + mobile.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        getActivity(),
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                resend_txt.setVisibility(view.VISIBLE);
                                resend_txt_btn.setVisibility(view.VISIBLE);
                                Toast.makeText(getActivity(), "Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String NewVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                otp_id = NewVerificationId;
                                otp_countetr();
                                counter.setVisibility(View.VISIBLE);
                                resend_txt.setVisibility(view.GONE);
                                resend_txt_btn.setVisibility(view.GONE);
                                Toast.makeText(getActivity(), "OTP send again to your mobile,please check", Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });

// submit otp option

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otp1.getText().toString().trim().isEmpty() ||
                        otp2.getText().toString().trim().isEmpty() ||
                        otp3.getText().toString().trim().isEmpty() ||
                        otp4.getText().toString().trim().isEmpty() ||
                        otp5.getText().toString().trim().isEmpty() ||
                        otp6.getText().toString().trim().isEmpty()
                ) {
                    wrong_otp.setVisibility(view.VISIBLE);
                } else {
                    wrong_otp.setVisibility(view.GONE);
                    String code = otp1.getText().toString() + otp2.getText().toString() +
                            otp3.getText().toString() + otp4.getText().toString() +
                            otp5.getText().toString() + otp6.getText().toString();
                    if (otp_id != null) {
                        progressBar.setVisibility(view.VISIBLE);
                        btn_submit.setVisibility(view.GONE);
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                otp_id,
                                code
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBar.setVisibility(view.GONE);
                                        btn_submit.setVisibility(view.VISIBLE);
                                        if (task.isSuccessful()) {
                                            wrong_otp.setVisibility(view.GONE);
                                            otp_verify_part.setVisibility(View.GONE);
                                            new_password_part.setVisibility(View.VISIBLE);

                                        } else {
                                            wrong_otp.setVisibility(view.VISIBLE);
                                        }
                                    }
                                });
                    }
                }
            }
        });
        // set new password part
        Button save_btn=view.findViewById(R.id.save_btn_p);
        ProgressBar progressBar_p=view.findViewById(R.id.progress_bar_save_p);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new_password.getText().toString().isEmpty()){
                    showError(new_password,"Please Enter New Password");
                }else if(new_password.getText().toString().length()<6){
                    showError(new_password,"Password must be at least 6 characters");
                }else if(!confirm_password.getText().toString().equals(new_password.getText().toString())){
                    showError(confirm_password,"Password Not Match");
                }else {
                    save_btn.setVisibility(View.GONE);
                   progressBar_p.setVisibility(View.VISIBLE);
                    HashMap hashMap = new HashMap();
                    hashMap.put("Password", confirm_password.getText().toString());
                    databaseReference.child("Customer").child(phone).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(getActivity(), "Password Changed Successfully, Please login", Toast.LENGTH_SHORT).show();
                            save_btn.setVisibility(View.VISIBLE);
                            progressBar_p.setVisibility(View.GONE);
                           new_password_part.setVisibility(View.GONE);
                           otp_verify_part.setVisibility(View.VISIBLE);
                            FragmentManager fm=getActivity().getSupportFragmentManager();
                            FragmentTransaction ft=fm.beginTransaction();
                            ft.replace(R.id.fragment, new Login()).commit();
                        }
                    });
                }
            }
        });
        //password show hide part
        EditText password_show = view.findViewById(R.id.new_password_p);
        EditText confirm_show = view.findViewById(R.id.confirm_password_p);

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

    private void otp_countetr() {
        long duration = TimeUnit.MINUTES.toMillis(1);
        new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long l) {
                String sDuration = String.format(Locale.ENGLISH, "%02d:%02d"
                        , TimeUnit.MILLISECONDS.toMinutes(l)
                        , TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit
                                .MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
                counter.setText(sDuration);
            }

            @Override
            public void onFinish() {
                counter.setVisibility(View.GONE);
                resend_txt.setVisibility(View.VISIBLE);
                resend_txt_btn.setVisibility(View.VISIBLE);
            }
        }.start();
    }


    // Automatic focus next textfield otp code
    private void next_text_auto() {
        otp1.requestFocus();
        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp1.getText().toString().length() == 1) {
                    otp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (otp2.getText().toString().length() == 1) {
                    otp3.requestFocus();
                } else {
                    otp1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp3.getText().toString().length() == 1) {
                    otp4.requestFocus();
                } else {
                    otp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp4.getText().toString().length() == 1) {
                    otp5.requestFocus();
                } else {
                    otp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp5.getText().toString().length() == 1) {
                    otp6.requestFocus();
                } else {
                    otp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp6.getText().toString().length() == 1) {
                    submit.requestFocus();
                } else {
                    otp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

}