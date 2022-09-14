package com.example.jnufood.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jnufood.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OTP_Verify#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OTP_Verify extends Fragment {

    EditText otp1,otp2,otp3,otp4,otp5,otp6;
    Button submit;
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
        submit=view.findViewById(R.id.btn_otp_submit);

        next_text_auto();


        TextView mobile = view.findViewById(R.id.mobile);
        String name= OTP_VerifyArgs.fromBundle(getArguments()).getName();
        String phone = OTP_VerifyArgs.fromBundle(getArguments()).getMobileNo();
        String email=OTP_VerifyArgs.fromBundle(getArguments()).getEmail();
        String dept=OTP_VerifyArgs.fromBundle(getArguments()).getDept();
        String password=OTP_VerifyArgs.fromBundle(getArguments()).getPassword();
        mobile.setText(phone);


        return view;
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
               if(otp1.getText().toString().length()==1) {
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

                if(otp2.getText().toString().length()==1){
                    otp3.requestFocus();
                }
                else {
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
                if(otp3.getText().toString().length()==1){
                    otp4.requestFocus();
                }
                else{
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
                if(otp4.getText().toString().length()==1){
                    otp5.requestFocus();
                }else{
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
                if(otp5.getText().toString().length()==1){
                    otp6.requestFocus();
                }else {
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
                if(otp6.getText().toString().length()==1){
                    submit.requestFocus();
                }else {
                    otp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}