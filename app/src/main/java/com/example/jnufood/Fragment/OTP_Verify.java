package com.example.jnufood.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OTP_Verify#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OTP_Verify extends Fragment  {

    protected NavigationView navigationView;
    EditText otp1, otp2, otp3, otp4, otp5, otp6;
    Button submit;
    LinearLayout wrong_otp;
    TextView counter, resend_txt,resend_txt_btn;
    String otp_id;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OTP_Verify() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OTP_Verify.
     */
    // TODO: Rename and change types and number of parameters
    public static OTP_Verify newInstance(String param1, String param2) {
        OTP_Verify fragment = new OTP_Verify();
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
        View view = inflater.inflate(R.layout.fragment_o_t_p__verify, container, false);
        otp1 = view.findViewById(R.id.otp1);
        otp2 = view.findViewById(R.id.otp2);
        otp3 = view.findViewById(R.id.otp3);
        otp4 = view.findViewById(R.id.otp4);
        otp5 = view.findViewById(R.id.otp5);
        otp6 = view.findViewById(R.id.otp6);
        submit = view.findViewById(R.id.btn_otp_submit);

        next_text_auto();
        //time counter
        counter=view.findViewById(R.id.time_countdown);
       otp_countetr();
        wrong_otp=view.findViewById(R.id.wrong_otp);
        TextView mobile = view.findViewById(R.id.mobile);
        String name = OTP_VerifyArgs.fromBundle(getArguments()).getName();
        String phone = OTP_VerifyArgs.fromBundle(getArguments()).getMobileNo();
        String email = OTP_VerifyArgs.fromBundle(getArguments()).getEmail();
        String dept = OTP_VerifyArgs.fromBundle(getArguments()).getDept();
        String password = OTP_VerifyArgs.fromBundle(getArguments()).getPassword();
        mobile.setText(phone);

        final ProgressBar progressBar = view.findViewById(R.id.progress_bar_o);
        final Button btn_submit = view.findViewById(R.id.btn_otp_submit);
        otp_id = OTP_VerifyArgs.fromBundle(getArguments()).getVerificationId();
        // resend option
         resend_txt=view.findViewById(R.id.text_resend);
          resend_txt_btn=view.findViewById(R.id.resend);
        resend_txt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(),"Please wait a moment,we will resend OTP to your mobile",Toast.LENGTH_LONG).show();
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
                                Toast.makeText(getActivity(),"Please Check Your Internet Connection",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String NewVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                otp_id=NewVerificationId;
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
                ){
                    wrong_otp.setVisibility(view.VISIBLE);
                }else{
                    wrong_otp.setVisibility(view.GONE);
                    String code=otp1.getText().toString()+otp2.getText().toString()+
                            otp3.getText().toString()+otp4.getText().toString()+
                            otp5.getText().toString()+otp6.getText().toString();
                    if(otp_id!=null){
                        progressBar.setVisibility(view.VISIBLE);
                        btn_submit.setVisibility(view.GONE);
                        PhoneAuthCredential phoneAuthCredential= PhoneAuthProvider.getCredential(
                                otp_id,
                                code
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                       progressBar.setVisibility(view.GONE);
                                        btn_submit.setVisibility(view.VISIBLE);
                                       if (task.isSuccessful()){
                                           wrong_otp.setVisibility(view.GONE);

                                           Toast.makeText(getActivity(),"Registration Successfully",Toast.LENGTH_SHORT).show();

                                           Navigation.findNavController(view).navigate(R.id.action_OTP_Verify_to_nav_home);

                                       }
                                       else {
                                           wrong_otp.setVisibility(view.VISIBLE);
                                       }
                                    }
                                });
                    }
                }
            }
        });

        return view;

    }

    private void otp_countetr() {
        long duration=TimeUnit.MINUTES.toMillis(1);
        new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long l) {
                String sDuration=String.format(Locale.ENGLISH,"%02d:%02d"
                        ,TimeUnit.MILLISECONDS.toMinutes(l)
                ,TimeUnit.MILLISECONDS.toSeconds(l)-TimeUnit
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

}