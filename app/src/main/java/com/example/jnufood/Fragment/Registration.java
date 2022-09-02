package com.example.jnufood.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jnufood.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Registration#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Registration extends Fragment {
  EditText confirm_show,password_show ;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
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
        View view= inflater.inflate(R.layout.fragment_registration, container, false);
        TextView btn=view.findViewById(R.id.login_a);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_registration_to_nav_login);
            }
        });

        //password show hide part
        password_show=view.findViewById(R.id.input_password);
        confirm_show=view.findViewById(R.id.confirm_password);

        password_show.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right=2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX()>=password_show.getRight()-password_show.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=password_show.getSelectionEnd();
                        if(password_show.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                            password_show.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            password_show.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_security,0,R.drawable.ic_show,0);

                        }else {
                            password_show.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                            password_show.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_security,0,R.drawable.ic_hide,0);

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
                final int Right=2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX()>=confirm_show.getRight()-confirm_show.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=confirm_show.getSelectionEnd();
                        if(confirm_show.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                            confirm_show.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            confirm_show.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_security,0,R.drawable.ic_show,0);

                        }else {
                            confirm_show.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                            confirm_show.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_security,0,R.drawable.ic_hide,0);

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
}