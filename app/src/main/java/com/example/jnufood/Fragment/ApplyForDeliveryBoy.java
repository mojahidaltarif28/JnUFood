package com.example.jnufood.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.jnufood.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApplyForDeliveryBoy#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplyForDeliveryBoy extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ApplyForDeliveryBoy() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApplyForDeliveryBoy.
     */
    // TODO: Rename and change types and number of parameters
    public static ApplyForDeliveryBoy newInstance(String param1, String param2) {
        ApplyForDeliveryBoy fragment = new ApplyForDeliveryBoy();
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
        View view=inflater.inflate(R.layout.fragment_apply_for_delivery_boy, container, false);
        // Inflate the layout for this fragment
        LinearLayout dba_form=view.findViewById(R.id.dba_app_form);
        LinearLayout dba_msg=view.findViewById(R.id.dba_success_msg);
        Button dba_submit_btn=view.findViewById(R.id.dba_btn);
        dba_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dba_form.setVisibility(View.GONE);
                dba_msg.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }
}