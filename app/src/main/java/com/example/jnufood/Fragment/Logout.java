package com.example.jnufood.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jnufood.MainActivity;
import com.example.jnufood.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Logout#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Logout extends Fragment {

    AlertDialog.Builder builder;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Logout() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Logout.
     */
    // TODO: Rename and change types and number of parameters
    public static Logout newInstance(String param1, String param2) {
        Logout fragment = new Logout();
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
      View view=inflater.inflate(R.layout.fragment_logout, container, false);
        // Inflate the layout for this fragment
        builder=new AlertDialog.Builder(getActivity());
       builder.setTitle("Alert!!")
               .setMessage("Do you want to Logged out")
               .setCancelable(true)
               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       getActivity().finish();
                       Toast.makeText(getActivity(),"Successfully Logged Out",Toast.LENGTH_SHORT).show();
                       Intent in=new Intent(getActivity(), MainActivity.class);
                       in.putExtra("login_code","-50");
                       in.putExtra("mobile","");
                       startActivity(in);
                   }
               })
               .setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       dialogInterface.cancel();
                       getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new HomeFragment()).commit();
                   }
               })
               .show();
        return view;
    }
}