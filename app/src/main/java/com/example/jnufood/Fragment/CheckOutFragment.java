package com.example.jnufood.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jnufood.R;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckOutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckOutFragment extends Fragment implements SSLCTransactionResponseListener {
    TextView total_price, payable_amount, proceed_btn, check_name;
    EditText check_mobile, delivery_address;
    String total_amount,total_check_amount,mobile,email,name;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CheckOutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckOutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckOutFragment newInstance(String param1, String param2) {
        CheckOutFragment fragment = new CheckOutFragment();
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
        View view = inflater.inflate(R.layout.fragment_check_out, container, false);
        Bundle bundle=this.getArguments();
        mobile=bundle.getString("mobile");
        total_amount=bundle.getString("total_item");
        total_check_amount=bundle.getString("pay_amount");

        check_name = view.findViewById(R.id.checkout_name);
        check_mobile=view.findViewById(R.id.checkout_mobile);
        delivery_address=view.findViewById(R.id.checkout_destination);
        total_price=view.findViewById(R.id.itemtotal_checkout);
        payable_amount=view.findViewById(R.id.payable_amount);
        proceed_btn=view.findViewById(R.id.checkout_proceed_btn);
        check_mobile.setText(mobile);
        total_price.setText(total_amount);
        payable_amount.setText(total_check_amount);
        databaseReference.child("Customer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(mobile)) {
                     name = snapshot.child(mobile).child("Name").getValue(String.class);
                     email=snapshot.child(mobile).child("Name").getValue(String.class);
                    final String dept = snapshot.child(mobile).child("Department").getValue(String.class);
                    check_name.setText(name);
                    delivery_address.setText(dept+" Department,Jagannath University");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        proceed_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        final SSLCommerzInitialization sslCommerzInitialization = new SSLCommerzInitialization ("jnufo6356baa181be6","jnufo6356baa181be6@ssl", Double.parseDouble(total_check_amount), SSLCCurrencyType.BDT,"123456789098765", "yourProductType", SSLCSdkType.TESTBOX);
        final SSLCCustomerInfoInitializer customerInfoInitializer = new SSLCCustomerInfoInitializer(""+name, ""+email,""+ delivery_address.getText().toString(), "dhaka", "1214", "Bangladesh",""+ mobile);
        IntegrateSSLCommerz
                .getInstance(getActivity())
                .addSSLCommerzInitialization(sslCommerzInitialization)
                .addCustomerInfoInitializer(customerInfoInitializer)
                .buildApiCall(this);


        return view;
    }

    @Override
    public void transactionSuccess(SSLCTransactionInfoModel sslcTransactionInfoModel) {
        Toast.makeText(getActivity(), "Payment Successful", Toast.LENGTH_SHORT).show();
        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment ,new CartFragment(),null).addToBackStack(null).commit();
    }

    @Override
    public void transactionFail(String s) {

    }

    @Override
    public void merchantValidationError(String s) {

    }
}