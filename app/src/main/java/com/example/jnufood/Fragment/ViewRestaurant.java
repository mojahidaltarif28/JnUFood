package com.example.jnufood.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jnufood.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewRestaurant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewRestaurant extends Fragment {
    String address, email, mobile, photo, restaurant_name;
    TextView addresst, emailt, mobilet, restaurant_namet;
    ImageView photoI;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewRestaurant() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewRestaurant.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewRestaurant newInstance(String param1, String param2) {
        ViewRestaurant fragment = new ViewRestaurant();
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
        View view = inflater.inflate(R.layout.fragment_view_restaurant, container, false);
        Bundle bundle = this.getArguments();
        address = bundle.getString("address");
        email = bundle.getString("email");
        restaurant_name = bundle.getString("name");
        photo = bundle.getString("photo");
        mobile = bundle.getString("mobile");

        addresst = view.findViewById(R.id.res_address);
        emailt = view.findViewById(R.id.res_email);
        restaurant_namet = view.findViewById(R.id.res_name);
        mobilet = view.findViewById(R.id.res_mobile);
        photoI = view.findViewById(R.id.res_image);
        addresst.setText(address);
        emailt.setText(email);
        restaurant_namet.setText(restaurant_name);
        mobilet.setText(mobile);
        Picasso.get().load(photo).into(photoI);
        return view;
    }
}