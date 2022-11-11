package com.example.jnufood.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jnufood.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Add_Admin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Add_Admin extends Fragment {
    EditText name, email, mobile;
    TextView add_btn;
    ProgressBar progressBar;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/");
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Add_Admin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Add_Admin.
     */
    // TODO: Rename and change types and number of parameters
    public static Add_Admin newInstance(String param1, String param2) {
        Add_Admin fragment = new Add_Admin();
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
        View view = inflater.inflate(R.layout.fragment_add__admin, container, false);
        name = view.findViewById(R.id.ada_name);
        mobile = view.findViewById(R.id.ada_mobile);
        email = view.findViewById(R.id.ada_email);
        add_btn = view.findViewById(R.id.ada_add_btn);
        progressBar = view.findViewById(R.id.ada_progress);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_btn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                if (name.getText().toString().isEmpty()) {
                    showError(name, "Enter Name");
                    add_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }else if(mobile.getText().toString().length()<11){
                    showError(mobile,"Invalid Mobile Number");
                    add_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }else if(email.getText().toString().isEmpty()){
                    showError(email,"Please Enter Email");
                    add_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }else{
                   databaseReference.child("Administration").child("Admin").addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           Random rnd = new Random();
                           int number = rnd.nextInt(999999);
                           String password = String.format("%06d", number);
                           databaseReference.child("Administration").child("Admin").child(mobile.getText().toString()).child("email").setValue(email.getText().toString());
                           databaseReference.child("Administration").child("Admin").child(mobile.getText().toString()).child("name").setValue(name.getText().toString());
                           databaseReference.child("Administration").child("Admin").child(mobile.getText().toString()).child("password").setValue(password);
                           try {
                               String stringSenderEmail = "mojahidaltarif78+jnufood@gmail.com";
                               String stringReceiverEmail =email.getText().toString();
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
                               mimeMessage.setSubject("Congratulation ,JnUFood Admin Added to you");
                               //   mimeMessage.setText("send");
                               mimeMessage.setText("Dear, " + name.getText().toString() + "\n" + "You are successfully added to JnUFood as a Admin.\n\n" + "mobile:" + mobile.getText().toString() + "\nPassword:" + password+"\n\nTHANK YOU");
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
                           email.setText("");
                           mobile.setText("");
                           name.setText("");
                           Toast.makeText(getActivity(),"Successfully Added",Toast.LENGTH_SHORT).show();
                           add_btn.setVisibility(View.VISIBLE);
                           progressBar.setVisibility(View.GONE);
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });
                }
            }
        });


        return view;
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}