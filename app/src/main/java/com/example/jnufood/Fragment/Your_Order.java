package com.example.jnufood.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jnufood.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Your_Order#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Your_Order extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
String mobile;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Your_Order() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Your_Order.
     */
    // TODO: Rename and change types and number of parameters
    public static Your_Order newInstance(String param1, String param2) {
        Your_Order fragment = new Your_Order();
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
        View view=inflater.inflate(R.layout.fragment_your__order, container, false);
        // Inflate the layout for this fragment
        Bundle bundle=this.getArguments();
        if(getArguments().getString("otp_id")!=null){
            mobile = bundle.getString("otp_id");
        }
        Toast.makeText(getActivity(),"login:"+mobile,Toast.LENGTH_SHORT).show();
        return view;
    }
}