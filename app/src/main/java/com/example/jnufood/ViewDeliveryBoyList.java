package com.example.jnufood;

import static android.os.SystemClock.sleep;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewDeliveryBoyList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewDeliveryBoyList extends Fragment {
    String address, email, mobile, name, nid, photo, profession;
    TextView addresst, emailt, mobilet, namet, professiont;
    ImageView nidI, photoI;
    ProgressBar progressImage, progressnid;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewDeliveryBoyList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewDeliveryBoyList.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewDeliveryBoyList newInstance(String param1, String param2) {
        ViewDeliveryBoyList fragment = new ViewDeliveryBoyList();
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
        View view = inflater.inflate(R.layout.fragment_view_delivery_boy_list, container, false);
        Bundle bundle = this.getArguments();
        progressImage = view.findViewById(R.id.progressimage);
        progressnid = view.findViewById(R.id.progressnid);
        progressnid.setVisibility(View.VISIBLE);
        progressImage.setVisibility(View.VISIBLE);
        address = bundle.getString("address");
        email = bundle.getString("email");
        name = bundle.getString("name");
        nid = bundle.getString("nid");
        photo = bundle.getString("photo");
        profession = bundle.getString("profession");
        mobile = bundle.getString("mobile");
        addresst = view.findViewById(R.id.dbv_address);
        emailt = view.findViewById(R.id.dbv_email);
        namet = view.findViewById(R.id.dbv_name);
        professiont = view.findViewById(R.id.dbv_profession);
        mobilet = view.findViewById(R.id.dbv_mobile);
        nidI = view.findViewById(R.id.dbv_nid);
        photoI = view.findViewById(R.id.dbv_image);
        addresst.setText(address);
        emailt.setText(email);
        namet.setText(name);
        professiont.setText(profession);
        mobilet.setText(mobile);
        Picasso.get().load(photo).into(photoI);
        Picasso.get().load(nid).into(nidI);

        nidI.setVisibility(View.VISIBLE);
        photoI.setVisibility(View.VISIBLE);
        progressnid.setVisibility(View.GONE);
        progressImage.setVisibility(View.GONE);


        return view;
    }
}