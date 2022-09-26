package com.example.jnufood;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


import com.example.jnufood.Fragment.Administration;
import com.example.jnufood.Fragment.ApplyForDeliveryBoy;
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

        Toolbar toolbar  = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }


        Bundle bundle = getIntent().getExtras();

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

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
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
        Bundle bundle1= new Bundle();

        switch (item.getItemId()){

            case R.id.nav_home:
                if (bundle != null) {
                    if (bundle.getString("login_code") != null) {
                        String mobile = bundle.getString("mobile");
                bundle1.putString("otp_id",mobile);
                HomeFragment homeFragment=new HomeFragment();
                homeFragment.setArguments(bundle1);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,homeFragment).commit();}}
                else {
                    bundle1.putString("otp_id","");
                    HomeFragment homeFragment=new HomeFragment();
                    homeFragment.setArguments(bundle1);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, homeFragment).commit();

                }
                break;
            case R.id.nav_login:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new Login()).commit();
                break;
            case R.id.my_Account:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new My_Account()).commit();
                break;
            case R.id.nav_applyForDeliveryBoy:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new ApplyForDeliveryBoy()).commit();
                break;
            case R.id.nav_administration:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new Administration()).commit();
                break;
            case R.id.your_Order:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new Your_Order()).commit();
                break;
            case R.id.history:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new History()).commit();
                break;
            case R.id.nav_logout:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new Logout()).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

}