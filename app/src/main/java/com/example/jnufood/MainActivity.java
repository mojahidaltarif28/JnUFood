package com.example.jnufood;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;


import com.example.jnufood.Fragment.AddFoodItem;
import com.example.jnufood.Fragment.Add_Admin;
import com.example.jnufood.Fragment.Add_Menu_Item;
import com.example.jnufood.Fragment.Admin_Account;
import com.example.jnufood.Fragment.Administration;
import com.example.jnufood.Fragment.ApplyForDeliveryBoy;
import com.example.jnufood.Fragment.Contact_Us;
import com.example.jnufood.Fragment.Customer_List;
import com.example.jnufood.Fragment.Delivery_Boy_Account;
import com.example.jnufood.Fragment.Delivery_Boy_List;
import com.example.jnufood.Fragment.History;
import com.example.jnufood.Fragment.History_Delivery_Boy;
import com.example.jnufood.Fragment.HomeFragment;
import com.example.jnufood.Fragment.Login;
import com.example.jnufood.Fragment.Logout;
import com.example.jnufood.Fragment.My_Account;
import com.example.jnufood.Fragment.Your_Order;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jnufood-default-rtdb.firebaseio.com/");

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawerLayout;
    protected NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Bundle bundle = getIntent().getExtras();
        Bundle bundle1 = new Bundle();
        if (savedInstanceState == null) {
            HomeFragment homeFragment=new HomeFragment();
            if (bundle != null) {
                if (bundle.getString("login_code") != null && bundle.getString("type")!=null) {
                    String mobile = bundle.getString("mobile");
                    String type=bundle.getString("type");
                    bundle1.putString("otp_id", mobile);
                    bundle1.putString("type",type);
                    homeFragment.setArguments(bundle1);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, homeFragment).commit();
                }
            }else {

                bundle1.putString("otp_id","");
                bundle1.putString("type","Customer");
                homeFragment.setArguments(bundle1);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, homeFragment).commit();
            }
            navigationView.setCheckedItem(R.id.nav_home);
        }




        if (bundle != null) {
            if (bundle.getString("login_code") != null) {
                String login_value = bundle.getString("login_code");
                String mobile = bundle.getString("mobile");
                if (login_value.equals("-505")) {
                    //customer
                   // Toast.makeText(this, mobile, Toast.LENGTH_SHORT).show();
                    navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
                    navigationView.getMenu().findItem(R.id.my_Account).setVisible(true);
                    navigationView.getMenu().findItem(R.id.history).setVisible(true);
                    navigationView.getMenu().findItem(R.id.your_Order).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_applyForDeliveryBoy).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_administration).setVisible(false);

                    navigationView.getMenu().findItem(R.id.add_Menu_Item).setVisible(false);
                    navigationView.getMenu().findItem(R.id.customer_List).setVisible(false);
                    navigationView.getMenu().findItem(R.id.delivery_Boy_List).setVisible(false);
                    navigationView.getMenu().findItem(R.id.add_Admin).setVisible(false);
                    navigationView.getMenu().findItem(R.id.admin_Account).setVisible(false);

                    navigationView.getMenu().findItem(R.id.history_Delivery_Boy).setVisible(false);
                    navigationView.getMenu().findItem(R.id.delivery_Boy_Account).setVisible(false);

                }
                else if(login_value.equals("-50")){
                    //admin
                   // Toast.makeText(this, mobile, Toast.LENGTH_SHORT).show();
                    navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
                    navigationView.getMenu().findItem(R.id.my_Account).setVisible(false);
                    navigationView.getMenu().findItem(R.id.history).setVisible(false);
                    navigationView.getMenu().findItem(R.id.your_Order).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_applyForDeliveryBoy).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_administration).setVisible(false);

                    navigationView.getMenu().findItem(R.id.add_Menu_Item).setVisible(false);
                    navigationView.getMenu().findItem(R.id.customer_List).setVisible(true);
                    navigationView.getMenu().findItem(R.id.delivery_Boy_List).setVisible(true);
                    navigationView.getMenu().findItem(R.id.add_Admin).setVisible(true);
                    navigationView.getMenu().findItem(R.id.admin_Account).setVisible(true);
                    navigationView.getMenu().findItem(R.id.history_Delivery_Boy).setVisible(false);
                    navigationView.getMenu().findItem(R.id.delivery_Boy_Account).setVisible(false);

                }
                else if(login_value.equals("-100")){
                    //delivery boy
                 //   Toast.makeText(this, mobile, Toast.LENGTH_SHORT).show();
                    navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
                    navigationView.getMenu().findItem(R.id.my_Account).setVisible(false);
                    navigationView.getMenu().findItem(R.id.history).setVisible(false);
                    navigationView.getMenu().findItem(R.id.your_Order).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_applyForDeliveryBoy).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_administration).setVisible(false);

                    navigationView.getMenu().findItem(R.id.add_Menu_Item).setVisible(false);
                    navigationView.getMenu().findItem(R.id.customer_List).setVisible(false);
                    navigationView.getMenu().findItem(R.id.delivery_Boy_List).setVisible(false);
                    navigationView.getMenu().findItem(R.id.add_Admin).setVisible(false);
                    navigationView.getMenu().findItem(R.id.admin_Account).setVisible(false);

                    navigationView.getMenu().findItem(R.id.history_Delivery_Boy).setVisible(true);
                    navigationView.getMenu().findItem(R.id.delivery_Boy_Account).setVisible(true);

                }else if(login_value.equals("-1010")){
                    navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
                    navigationView.getMenu().findItem(R.id.my_Account).setVisible(false);
                    navigationView.getMenu().findItem(R.id.history).setVisible(false);
                    navigationView.getMenu().findItem(R.id.your_Order).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_applyForDeliveryBoy).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_administration).setVisible(false);

                    navigationView.getMenu().findItem(R.id.add_Menu_Item).setVisible(true);
                    navigationView.getMenu().findItem(R.id.customer_List).setVisible(false);
                    navigationView.getMenu().findItem(R.id.delivery_Boy_List).setVisible(false);
                    navigationView.getMenu().findItem(R.id.add_Admin).setVisible(false);
                    navigationView.getMenu().findItem(R.id.admin_Account).setVisible(false);

                    navigationView.getMenu().findItem(R.id.history_Delivery_Boy).setVisible(false);
                    navigationView.getMenu().findItem(R.id.delivery_Boy_Account).setVisible(false);
                }
                else {

                  //  Toast.makeText(this, "Logout:"+mobile, Toast.LENGTH_SHORT).show();
                    navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
                    navigationView.getMenu().findItem(R.id.my_Account).setVisible(false);
                    navigationView.getMenu().findItem(R.id.history).setVisible(false);
                    navigationView.getMenu().findItem(R.id.your_Order).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_applyForDeliveryBoy).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_administration).setVisible(true);
                }
            }
        }


    }

    @Override
    public void onBackPressed() {


        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Bundle bundle = getIntent().getExtras();


        switch (item.getItemId()) {

            case R.id.nav_home:
                HomeFragment homeFragment=new HomeFragment();
                if (bundle != null) {
                    if (bundle.getString("login_code") != null) {
                        String mobile = bundle.getString("mobile");
                        String type=bundle.getString("type");
                        Bundle bundle1 = new Bundle();

                        bundle1.putString("otp_id", mobile);
                        bundle1.putString("type",type);
                        homeFragment.setArguments(bundle1);
                        String ROOT_FRAGMENT_TAG="HomeFragment";
                        FragmentManager fm=getSupportFragmentManager();
                        FragmentTransaction ft=fm.beginTransaction();
                        ft.replace(R.id.fragment, homeFragment,null).addToBackStack(null).commit();
//                        fm.popBackStack();
                    }
                }else {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("otp_id","");
                    bundle1.putString("type","Customer");
                    homeFragment.setArguments(bundle1);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, homeFragment).commit();
                }
                break;
            case R.id.nav_login:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Login(),null).addToBackStack(null).commit();
                break;
            case R.id.my_Account:
                if (bundle != null) {
                    if (bundle.getString("login_code") != null) {
                        String mobile = bundle.getString("mobile");
                        Bundle bundle1 = new Bundle();
                        My_Account my_account = new My_Account();
                        bundle1.putString("otp_id", mobile);
                        my_account.setArguments(bundle1);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, my_account,null).addToBackStack(null).commit();
                    }
                }
                break;
            case R.id.nav_applyForDeliveryBoy:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new ApplyForDeliveryBoy(),null).addToBackStack(null).commit();
                break;
            case R.id.nav_administration:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Administration(),null).addToBackStack(null).commit();
                break;
            case R.id.your_Order:
                if (bundle != null) {
                    if (bundle.getString("login_code") != null) {
                        String mobile = bundle.getString("mobile");
                        Bundle bundle1 = new Bundle();
                        Your_Order your_order = new Your_Order();
                        bundle1.putString("otp_id", mobile);
                        your_order.setArguments(bundle1);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, your_order,null).addToBackStack(null).commit();
                    }
                }
                break;
            case R.id.history:
                if (bundle != null) {
                    if (bundle.getString("login_code") != null) {
                        String mobile = bundle.getString("mobile");
                        Bundle bundle1 = new Bundle();
                        History history = new History();
                        bundle1.putString("otp_id", mobile);
                        history.setArguments(bundle1);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, history,null).addToBackStack(null).commit();
                    }
                }
                break;
            case R.id.nav_logout:
                if (bundle != null) {
                    if (bundle.getString("login_code") != null) {
                        String mobile = bundle.getString("mobile");
                        String type=bundle.getString("type");
                        Bundle bundle1 = new Bundle();
                        Logout logout = new Logout();
                        bundle1.putString("type",type);
                        bundle1.putString("otp_id", mobile);
                        logout.setArguments(bundle1);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, logout,null).addToBackStack(null).commit();
                    }
                }
                   break;
            case R.id.contact_us:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Contact_Us(),null).addToBackStack(null).commit();
                break;

            case R.id.add_Menu_Item:
                String mobile = bundle.getString("mobile");
                databaseReference.child("Administration").child("Restaurant").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                      final String  res_name =snapshot.child(mobile).child("restaurant_name").getValue(String.class);
                        Bundle bundle1 = new Bundle();
                        AddFoodItem add_menu_item=new AddFoodItem();
                        bundle1.putString("otp_id", mobile);
                        bundle1.putString("res_name",res_name);
                        add_menu_item.setArguments(bundle1);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, add_menu_item,null).addToBackStack(null).commit();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

               break;
            case R.id.customer_List:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Customer_List(),null).addToBackStack(null).commit();
                break;
            case R.id.delivery_Boy_List:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Delivery_Boy_List(),null).addToBackStack(null).commit();
                break;
            case R.id.add_Admin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Add_Admin(),null).addToBackStack(null).commit();
                break;
            case R.id.admin_Account:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Admin_Account(),null).addToBackStack(null).commit();
                break;
            case R.id.history_Delivery_Boy:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new History_Delivery_Boy(),null).addToBackStack(null).commit();
                break;

            case R.id.delivery_Boy_Account:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Delivery_Boy_Account(),null).addToBackStack(null).commit();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

}