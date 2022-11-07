package com.example.jnufood.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jnufood.Get_My_Cart_Modal;
import com.example.jnufood.MainActivity;
import com.example.jnufood.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCCustomerInfoInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization;
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCCurrencyType;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCSdkType;
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz;
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.SSLCTransactionResponseListener;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SSLComerz#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SSLComerz extends Fragment implements SSLCTransactionResponseListener {
    String name, de_mobile, mobile, address, email, total_check_amount;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/");
    LinearLayout success, fail;
    TextView home, order;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SSLComerz() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SSLComerz.
     */
    // TODO: Rename and change types and number of parameters
    public static SSLComerz newInstance(String param1, String param2) {
        SSLComerz fragment = new SSLComerz();
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
        View view = inflater.inflate(R.layout.fragment_s_s_l_comerz, container, false);
        Bundle bundle = this.getArguments();
        name = bundle.getString("name");
        address = bundle.getString("address");
        mobile = bundle.getString("mobile");
        de_mobile = bundle.getString("de_mobile");
        total_check_amount = bundle.getString("amount");
        email = bundle.getString("email");


        final SSLCommerzInitialization sslCommerzInitialization = new SSLCommerzInitialization("jnufo6356baa181be6", "jnufo6356baa181be6@ssl", Double.parseDouble(total_check_amount), SSLCCurrencyType.BDT, "123456789098765 ", "Food", SSLCSdkType.TESTBOX);
        final SSLCCustomerInfoInitializer customerInfoInitializer = new SSLCCustomerInfoInitializer( name,  email,  address, "dhaka", "1214", "Bangladesh",  de_mobile);
        IntegrateSSLCommerz
                .getInstance(getActivity())
                .addSSLCommerzInitialization(sslCommerzInitialization)
                .addCustomerInfoInitializer(customerInfoInitializer)
                .buildApiCall(this);

        success = view.findViewById(R.id.successful);
        fail = view.findViewById(R.id.failed);
        order = view.findViewById(R.id.go_order_page);
        home = view.findViewById(R.id.go_home_page);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Your_Order your_order = new Your_Order();
                Bundle bundle1 = new Bundle();
                bundle1.putString("otp_id", mobile);
                your_order.setArguments(bundle1);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, your_order, null).addToBackStack(null).commit();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new HomeFragment()).commit();
                Intent in = new Intent(getActivity(), MainActivity.class);
                in.putExtra("login_code", "-505");
                in.putExtra("mobile", mobile);
                in.putExtra("type", "Customer");
                startActivity(in);
            }
        });
        return view;
    }

    @Override
    public void transactionSuccess(SSLCTransactionInfoModel sslcTransactionInfoModel) {
        databaseReference.child("Cart_List").child(mobile).child("list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {

                for (DataSnapshot dataSnapshot : snapshot1.getChildren()) {
                    Get_My_Cart_Modal get_my_cart_modal = dataSnapshot.getValue(Get_My_Cart_Modal.class);
                    String Name, mobile, net, photo, price, total_item, total_price,restaurant;
                    Name = get_my_cart_modal.getName();
                    mobile = get_my_cart_modal.getMobile();
                    net = get_my_cart_modal.getNet();
                    photo = get_my_cart_modal.getPhoto();
                    price = get_my_cart_modal.getPrice();
                    total_item = get_my_cart_modal.getTotal_item();
                    total_price = get_my_cart_modal.getTotal_price();
                    restaurant=get_my_cart_modal.getRestaurant();

                    databaseReference.child("Order_Table_Restaurant").child(restaurant).child(mobile).child("status").setValue("paid");
                    databaseReference.child("Order_Table_Restaurant").child(restaurant).child(mobile).child("restaurant").setValue(restaurant);
                    databaseReference.child("Order_Table_Restaurant").child(restaurant).child(mobile).child("mobile").setValue(mobile);
                    databaseReference.child("Order_Table_Restaurant").child(restaurant).child(mobile).child("delivery_address").setValue(address);
                    databaseReference.child("Order_Table_Restaurant").child(restaurant).child(mobile).child("delivery_mobile").setValue(de_mobile);
                    databaseReference.child("Order_Table_Restaurant").child(restaurant).child(mobile).child("payment_amount").setValue(total_check_amount);
                    databaseReference.child("Order_Table_Restaurant").child(restaurant).child(mobile).child("list").child(Name).child("Name").setValue(Name);
                    databaseReference.child("Order_Table_Restaurant").child(restaurant).child(mobile).child("list").child(Name).child("price").setValue(price);
                    databaseReference.child("Order_Table_Restaurant").child(restaurant).child(mobile).child("list").child(Name).child("net").setValue(net);
                    databaseReference.child("Order_Table_Restaurant").child(restaurant).child(mobile).child("list").child(Name).child("mobile").setValue(mobile);
                    databaseReference.child("Order_Table_Restaurant").child(restaurant).child(mobile).child("list").child(Name).child("photo").setValue(photo);
                    databaseReference.child("Order_Table_Restaurant").child(restaurant).child(mobile).child("list").child(Name).child("total_item").setValue(total_item);
                    databaseReference.child("Order_Table_Restaurant").child(restaurant).child(mobile).child("list").child(Name).child("total_price").setValue(total_price);

                    databaseReference.child("Order_Table").child(mobile).child("status").setValue("paid");
                    databaseReference.child("Order_Table").child(mobile).child("restaurant").setValue(restaurant);
                    databaseReference.child("Order_Table").child(mobile).child("mobile").setValue(mobile);
                    databaseReference.child("Order_Table").child(mobile).child("delivery_address").setValue(address);
                    databaseReference.child("Order_Table").child(mobile).child("delivery_mobile").setValue(de_mobile);
                    databaseReference.child("Order_Table").child(mobile).child("payment_amount").setValue(total_check_amount);
                    databaseReference.child("Order_Table").child(mobile).child("list").child(Name).child("Name").setValue(Name);
                    databaseReference.child("Order_Table").child(mobile).child("list").child(Name).child("price").setValue(price);
                    databaseReference.child("Order_Table").child(mobile).child("list").child(Name).child("net").setValue(net);
                    databaseReference.child("Order_Table").child(mobile).child("list").child(Name).child("mobile").setValue(mobile);
                    databaseReference.child("Order_Table").child(mobile).child("list").child(Name).child("photo").setValue(photo);
                    databaseReference.child("Order_Table").child(mobile).child("list").child(Name).child("total_item").setValue(total_item);
                    databaseReference.child("Order_Table").child(mobile).child("list").child(Name).child("total_price").setValue(total_price);


                }
                databaseReference.child("Cart_List").child(mobile).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        success.setVisibility(View.VISIBLE);
                        fail.setVisibility(View.GONE);
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void transactionFail(String s) {
        success.setVisibility(View.GONE);
        fail.setVisibility(View.VISIBLE);
    }

    @Override
    public void merchantValidationError(String s) {
        success.setVisibility(View.GONE);
        fail.setVisibility(View.VISIBLE);
    }
}