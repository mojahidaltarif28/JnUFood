package com.example.jnufood;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;


import com.example.jnufood.Fragment.Administration;
import com.example.jnufood.Fragment.ApplyForDeliveryBoy;
import com.example.jnufood.Fragment.Contact_Us;
import com.example.jnufood.Fragment.History;
import com.example.jnufood.Fragment.HomeFragment;
import com.example.jnufood.Fragment.Login;
import com.example.jnufood.Fragment.Logout;
import com.example.jnufood.Fragment.My_Account;
import com.example.jnufood.Fragment.Your_Order;
import com.google.android.material.navigation.NavigationView;

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
        if (savedInstanceState == null) {
            HomeFragment homeFragment=new HomeFragment();
            if (bundle != null) {
                if (bundle.getString("login_code") != null) {
                    String mobile = bundle.getString("mobile");
                    Bundle bundle1 = new Bundle();

                    bundle1.putString("otp_id", mobile);
                    homeFragment.setArguments(bundle1);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, homeFragment).commit();
                }
            }else {
                Bundle bundle1 = new Bundle();
                bundle1.putString("otp_id","");
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
                    Toast.makeText(this, mobile, Toast.LENGTH_SHORT).show();
                    navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
                    navigationView.getMenu().findItem(R.id.my_Account).setVisible(true);
                    navigationView.getMenu().findItem(R.id.history).setVisible(true);
                    navigationView.getMenu().findItem(R.id.your_Order).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_applyForDeliveryBoy).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_administration).setVisible(false);
                } else {

                    Toast.makeText(this, mobile, Toast.LENGTH_SHORT).show();
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
                        Bundle bundle1 = new Bundle();

                        bundle1.putString("otp_id", mobile);
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
                        Bundle bundle1 = new Bundle();
                        Logout logout = new Logout();
                        bundle1.putString("otp_id", mobile);
                        logout.setArguments(bundle1);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, logout,null).addToBackStack(null).commit();
                    }
                }
                   break;
            case R.id.contact_us:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Contact_Us(),null).addToBackStack(null).commit();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

}