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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Administration#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Administration extends Fragment {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/");
    EditText password_show;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Administration() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Administration.
     */
    // TODO: Rename and change types and number of parameters
    public static Administration newInstance(String param1, String param2) {
        Administration fragment = new Administration();
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
        View view= inflater.inflate(R.layout.fragment_administration, container, false);
        Button ad_btn=view.findViewById(R.id.ad_btn_login);
        TextView incorrect=view.findViewById(R.id.ad_incorrect_password);
        ProgressBar progressBar=view.findViewById(R.id.ad_progressbar);
        EditText mobile=view.findViewById(R.id.ad_login_phone);
        EditText password=view.findViewById(R.id.ad_login_password);
        ad_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad_btn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                String mobile_s=mobile.getText().toString();
                String password_s=password.getText().toString();
                if(mobile_s.isEmpty()||mobile_s.length()<11){
                    showError(mobile,"Enter Your Correct Mobile Number");
                    progressBar.setVisibility(View.GONE);
                    ad_btn.setVisibility(View.VISIBLE);
                }else if(password_s.isEmpty()){
                    showError(password,"Enter Your Password");
                    progressBar.setVisibility(View.GONE);
                    ad_btn.setVisibility(View.VISIBLE);
                }else {
                    databaseReference.child("Administration").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.child("Admin").hasChild(mobile_s)){
                                final String get_admin_pass=snapshot.child("Admin").child(mobile_s).child("password").getValue(String.class);
                                if(get_admin_pass.equals(password_s)){
                                    getActivity().finish();
                                    Intent in=new Intent(getActivity(), MainActivity.class);
                                    in.putExtra("login_code","-50");
                                    in.putExtra("mobile",mobile_s);
                                    in.putExtra("type","Admin");
                                    startActivity(in);
                                }else {
                                    incorrect.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    ad_btn.setVisibility(View.VISIBLE);
                                }
                            }
                            else if(snapshot.child("Delivery_man").hasChild(mobile_s)){
                                final String get_db_pass=snapshot.child("Delivery_man").child(mobile_s).child("password").getValue(String.class);
                                if (get_db_pass.equals(password_s)){
                                    getActivity().finish();
                                    Intent in=new Intent(getActivity(), MainActivity.class);
                                    in.putExtra("login_code","-100");
                                    in.putExtra("mobile",mobile_s);
                                    in.putExtra("type","Delivery");
                                    startActivity(in);
                                }else {
                                    incorrect.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    ad_btn.setVisibility(View.VISIBLE);
                                }
                            }else{
                                incorrect.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                ad_btn.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }


            }
        });

        //password show
        password_show = view.findViewById(R.id.ad_login_password);

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
        return view;
    }
    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}