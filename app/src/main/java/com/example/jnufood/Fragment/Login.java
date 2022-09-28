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
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends Fragment {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/");
    EditText password_show;
    TextView incorrect_pass;
    private EditText login_phone,login_password;
    Button login_btn;
    public static final String SHARED_PREFS="sharedPrefs";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
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


    //private FragmentHomeBinding binding;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        TextView btn = view.findViewById(R.id.signup);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new Registration()).commit();

            }
        });
        //Login part from firebase
         login_phone = view.findViewById(R.id.login_phone);
         login_password = view.findViewById(R.id.login_password);
         login_btn = view.findViewById(R.id.btn_login);
         incorrect_pass=view.findViewById(R.id.incorrect_password);
       final ProgressBar progressBar=view.findViewById(R.id.login_progressbar);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login_phone_s=login_phone.getText().toString();
                String login_password_s=login_password.getText().toString();
                if(login_phone_s.isEmpty()){
                    showError(login_phone,"please enter your phone number");
                }
                else if(login_phone_s.length()!=11){
                    incorrect_pass.setVisibility(View.VISIBLE);
                }
                else if(login_password_s.isEmpty()||login_password_s.length()<6){
                   incorrect_pass.setVisibility(View.VISIBLE);
                }
                else{
                    databaseReference.child("Customer").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(login_phone_s)){
                                final String getpassword=snapshot.child(login_phone_s).child("Password").getValue(String.class);
                                if(getpassword.equals(login_password_s)){
                                    incorrect_pass.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.VISIBLE);
                                    btn.setVisibility(View.GONE);
                                    getActivity().finish();
                                    Toast.makeText(getActivity(),"Successfully Logged In",Toast.LENGTH_SHORT).show();

                                    Intent in=new Intent(getActivity(), MainActivity.class);
                                    in.putExtra("login_code","-505");
                                    in.putExtra("mobile",login_phone_s);
                                    startActivity(in);


                                }
                                else{
                                    incorrect_pass.setVisibility(View.VISIBLE);
                                }
                            }
                            else {
                               incorrect_pass.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                      }
            }
        });



        //password show hide part
        password_show = view.findViewById(R.id.login_password);

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