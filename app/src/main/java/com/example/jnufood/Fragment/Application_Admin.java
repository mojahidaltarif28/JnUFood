package com.example.jnufood.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jnufood.Get_DBA_Modal;
import com.example.jnufood.Get_DBA_Modal_Adapter;
import com.example.jnufood.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Application_Admin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Application_Admin extends Fragment {
    RecyclerView recyclerView;
    Get_DBA_Modal_Adapter get_dba_modal_adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Application_Admin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Application_Admin.
     */
    // TODO: Rename and change types and number of parameters
    public static Application_Admin newInstance(String param1, String param2) {
        Application_Admin fragment = new Application_Admin();
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
       View view=inflater.inflate(R.layout.fragment_application__admin, container, false);

       recyclerView=(RecyclerView) view.findViewById(R.id.admin_db_application_recycle_view);
       recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<Get_DBA_Modal> options=
                new FirebaseRecyclerOptions.Builder<Get_DBA_Modal>()
                .setQuery(FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/").child("DB Application"),Get_DBA_Modal.class)
                .build();
        get_dba_modal_adapter=new Get_DBA_Modal_Adapter(options);
        recyclerView.setAdapter(get_dba_modal_adapter);
        get_dba_modal_adapter.setOnClickEvent(new Get_DBA_Modal_Adapter.OnClickEvent_DBA_admin() {
            @Override
            public void on_DBA_Admin_Click(String name, String mobile, String email, String profession, String address, String image, String nid) {
                Toast.makeText(getActivity(), name+" "+mobile, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        get_dba_modal_adapter.startListening();
    }
}