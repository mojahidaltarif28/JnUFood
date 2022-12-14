package com.example.jnufood.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jnufood.Get_My_Cart_Modal;
import com.example.jnufood.Get_history_modal;
import com.example.jnufood.History_Adapter;
import com.example.jnufood.My_Cart_Adapter;
import com.example.jnufood.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link History#newInstance} factory method to
 * create an instance of this fragment.
 */
public class History extends Fragment {
    TextView empty_history;
    LinearLayout my_history_show;
    RecyclerView recyclerView;
    History_Adapter history_adapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String mobile;

    public History() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment History.
     */
    // TODO: Rename and change types and number of parameters
    public static History newInstance(String param1, String param2) {
        History fragment = new History();
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
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        Bundle bundle = this.getArguments();
        if (getArguments().getString("otp_id") != null) {
            mobile = bundle.getString("otp_id");
        }
        empty_history=view.findViewById(R.id.empty_history);
        my_history_show=view.findViewById(R.id.my_history_show);
        recyclerView=view.findViewById(R.id.recycle_view_my_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<Get_history_modal> options = new FirebaseRecyclerOptions.Builder<Get_history_modal>().setQuery(FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/").child("History").child(mobile), Get_history_modal.class).build();
        options.getSnapshots();
        history_adapter = new History_Adapter(options);
        recyclerView.setAdapter(history_adapter);
        history_adapter.setOnclickEvent(new History_Adapter.OnClickEventHistory() {
            @Override
            public void onClickHistroy(String time) {
               History_View history_view=new History_View();
               Bundle bundle1=new Bundle();
               bundle1.putString("time",time);
               bundle1.putString("mobile",mobile);
               history_view.setArguments(bundle1);
               getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,history_view,null).addToBackStack(null).commit();
            }
        });
        databaseReference.child("History").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(mobile)){
                    my_history_show.setVisibility(View.VISIBLE);
                    empty_history.setVisibility(View.GONE);
                }else {
                    my_history_show.setVisibility(View.GONE);
                    empty_history.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        history_adapter.startListening();
    }
}