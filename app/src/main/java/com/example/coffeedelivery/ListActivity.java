package com.example.coffeedelivery;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;


public class ListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        toolbar = findViewById(R.id.toolbar); // Faz o menu ficar por cima da toolbar
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_about:
                gtAbout();
                break;

            case R.id.nav_logout:
                gtLogin();
                break;

            case R.id.nav_request:
                gtRequest();
                break;

            case R.id.nav_account:
                Toast.makeText(getApplicationContext(), "Em breve...", Toast.LENGTH_LONG).show();
                break;
        }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        @Override
        public void onBackPressed () {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

    private void gtAbout(){
        Intent i = new Intent(ListActivity.this, AboutActivity.class);
        startActivity(i);
        finish();
    }

    private void gtRequest(){
        Intent i = new Intent(ListActivity.this, RequestActivity.class);
        startActivity(i);
        finish();
    }

    private void gtLogin(){
        Intent i = new Intent(ListActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}