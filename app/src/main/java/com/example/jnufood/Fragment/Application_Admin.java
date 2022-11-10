package com.example.jnufood.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import com.example.jnufood.Get_DBA_Modal;
import com.example.jnufood.Get_DBA_Modal_Adapter;
import com.example.jnufood.Get_Restaurant_Adapter;
import com.example.jnufood.Get_Restaurant_Model;
import com.example.jnufood.MainActivity;
import com.example.jnufood.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Application_Admin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Application_Admin extends Fragment {
    RecyclerView recyclerView,recyclerView1;
    Get_DBA_Modal_Adapter get_dba_modal_adapter;
    TextView db_btn, res_btn;
    LinearLayout db_show, res_show;
    String mobile_log;
    Get_Restaurant_Adapter get_restaurant_adapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/");
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_application__admin, container, false);
        Bundle bundle=this.getArguments();
        mobile_log=bundle.getString("mobile");
        recyclerView = (RecyclerView) view.findViewById(R.id.admin_db_application_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<Get_DBA_Modal> options =
                new FirebaseRecyclerOptions.Builder<Get_DBA_Modal>()
                        .setQuery(FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/").child("DB Application"), Get_DBA_Modal.class)
                        .build();
        get_dba_modal_adapter = new Get_DBA_Modal_Adapter(options);
        recyclerView.setAdapter(get_dba_modal_adapter);
        get_dba_modal_adapter.setOnClickEvent(new Get_DBA_Modal_Adapter.OnClickEvent_DBA_admin() {
            @Override
            public void on_DBA_Admin_Click(String name, String mobile, String email, String profession, String address, String image, String nid) {
                databaseReference.child("Administration").child("Delivery_man").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Random rnd = new Random();
                        int number = rnd.nextInt(999999);
                        String password = String.format("%06d", number);
                        databaseReference.child("Administration").child("Delivery_man").child(mobile).child("mobile").setValue(mobile);
                        databaseReference.child("Administration").child("Delivery_man").child(mobile).child("address").setValue(address);
                        databaseReference.child("Administration").child("Delivery_man").child(mobile).child("email").setValue(email);
                        databaseReference.child("Administration").child("Delivery_man").child(mobile).child("name").setValue(name);
                        databaseReference.child("Administration").child("Delivery_man").child(mobile).child("nid").setValue(nid);
                        databaseReference.child("Administration").child("Delivery_man").child(mobile).child("password").setValue(password);
                        databaseReference.child("Administration").child("Delivery_man").child(mobile).child("photo").setValue(image);
                        databaseReference.child("Administration").child("Delivery_man").child(mobile).child("profession").setValue(profession);
                        try {
                            String stringSenderEmail = "mojahidaltarif78+jnufood@gmail.com";
                            String stringReceiverEmail =email;
                            String stringPasswordSenderEmail = "ajfccqcabtfjesca";

                            String stringHost = "smtp.gmail.com";
                            Properties properties = System.getProperties();
                            properties.put("mail.smtp.host", stringHost);
                            properties.put("mail.smtp.port", "465");
                            properties.put("mail.smtp.ssl.enable", "true");
                            properties.put("mail.smtp.auth", "true");


                            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                                @Override
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                                }
                            });
                            MimeMessage mimeMessage = new MimeMessage(session);
                            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));
                            mimeMessage.setSubject("Deliver Boy Request Accepted ");
                       //   mimeMessage.setText("send");
                             mimeMessage.setText("Dear, " + name + "\n" + "You are successfully join to JnUFood as a Delivery Boy.\n\n" + "mobile:" + mobile + "\nPassword:" + password+"\n\nTHANK YOU");
                           Thread thread=new Thread(new Runnable() {
                               @Override
                               public void run() {
                                   try {
                                       Transport.send(mimeMessage);
                                   } catch (MessagingException e) {
                                       e.printStackTrace();
                                   }
                               }
                           });
                           thread.start();

                        } catch (AddressException e) {
                            e.printStackTrace();
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }

                        databaseReference.child("DB Application").child(mobile).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getActivity(), "Successfully Accept", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void decline_click(String mobile) {
                databaseReference.child("DB Application").child(mobile).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        db_btn = view.findViewById(R.id.admin_db_btn);
        res_btn = view.findViewById(R.id.admin_res_btn);
        db_show = view.findViewById(R.id.delivery_boy_application);
        res_show = view.findViewById(R.id.restaurant_application);
        db_btn.setBackgroundResource(R.color.blue);
        db_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_show.setVisibility(View.VISIBLE);
                res_show.setVisibility(View.GONE);
                db_btn.setBackgroundResource(R.color.blue);
                res_btn.setBackgroundResource(R.color.orange);

            }
        });
        res_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_show.setVisibility(View.GONE);
                res_show.setVisibility(View.VISIBLE);
                db_btn.setBackgroundResource(R.color.orange);
                res_btn.setBackgroundResource(R.color.blue);
            }
        });

        recyclerView1= (RecyclerView) view.findViewById(R.id.admin_restaurant_application_recycle_view);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<Get_Restaurant_Model> options1 =
                new FirebaseRecyclerOptions.Builder<Get_Restaurant_Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/").child("Restaurant_Application"), Get_Restaurant_Model.class)
                        .build();
        get_restaurant_adapter = new Get_Restaurant_Adapter(options1);
        recyclerView1.setAdapter(get_restaurant_adapter);
        get_restaurant_adapter.setOnClickEvent(new Get_Restaurant_Adapter.OnClickEvent_RES_admin() {
            @Override
            public void on_RES_Admin_Click(String food_category, String address, String email, String image, String mobile, String res_name) {
                databaseReference.child("Administration").child("Restaurant").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Random rnd = new Random();
                        int number = rnd.nextInt(999999);
                        String password = String.format("%06d", number);
                        databaseReference.child("food_Item").child(res_name).child("name").setValue(food_category);
                        databaseReference.child("food_Item").child(res_name).child("photo").setValue(image);
                        databaseReference.child("food_Item").child(res_name).child("restaurant").setValue(res_name);
                        databaseReference.child("Administration").child("Restaurant").child(mobile).child("mobile").setValue(mobile);
                        databaseReference.child("Administration").child("Restaurant").child(mobile).child("address").setValue(address);
                        databaseReference.child("Administration").child("Restaurant").child(mobile).child("email").setValue(email);
                        databaseReference.child("Administration").child("Restaurant").child(mobile).child("restaurant_name").setValue(res_name);
                        databaseReference.child("Administration").child("Restaurant").child(mobile).child("password").setValue(password);
                        databaseReference.child("Administration").child("Restaurant").child(mobile).child("photo").setValue(image);

                        try {
                            String stringSenderEmail = "mojahidaltarif78+jnufood@gmail.com";
                            String stringReceiverEmail =email;
                            String stringPasswordSenderEmail = "ajfccqcabtfjesca";

                            String stringHost = "smtp.gmail.com";
                            Properties properties = System.getProperties();
                            properties.put("mail.smtp.host", stringHost);
                            properties.put("mail.smtp.port", "465");
                            properties.put("mail.smtp.ssl.enable", "true");
                            properties.put("mail.smtp.auth", "true");


                            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                                @Override
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                                }
                            });
                            MimeMessage mimeMessage = new MimeMessage(session);
                            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));
                            mimeMessage.setSubject("Restaurant Request Accepted ");
                            //   mimeMessage.setText("send");
                            mimeMessage.setText("Restaurant Name: " + res_name+ ".\n\n" + "Your restaurant successfully added to JnUFood .\n\n" + "mobile:" + mobile + "\nPassword:" + password+"\n\nTHANK YOU");
                            Thread thread=new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Transport.send(mimeMessage);
                                    } catch (MessagingException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            thread.start();

                        } catch (AddressException e) {
                            e.printStackTrace();
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }

                        databaseReference.child("Restaurant_Application").child(mobile).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getActivity(), "Successfully Accept", Toast.LENGTH_SHORT).show();
                                Intent in = new Intent(getActivity(), MainActivity.class);
                                in.putExtra("login_code", "-50");
                                in.putExtra("mobile", mobile_log);
                                in.putExtra("type", "Admin");
                                startActivity(in);

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void decline_Res_click(String mobile) {
                databaseReference.child("Restaurant_Application").child(mobile).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "Successfully Declined", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        get_dba_modal_adapter.startListening();
        get_restaurant_adapter.startListening();
    }
}