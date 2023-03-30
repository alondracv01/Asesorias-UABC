package com.hfad.asesoriasuabc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private int actualFrag = R.id.nav_materias;

    public String matricula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fragment fragment = null;

        if (savedInstanceState == null) {
            matricula= getIntent().getStringExtra("matricula");
            Log.d("MainActivity", "Matricula que envio LogIn: " + matricula);
        } else {
            matricula= (String) savedInstanceState.getSerializable("matricula");
            Log.d("MainActivity", "Matricula que envio LogIn: " + matricula);
        }

        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            startActivity(new Intent(MainActivity.this, LogInActivity.class));
        }

        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).commit();

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.nav_open_drawer,
                R.string.nav_close_drawer);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState != null){
            fragment = new MateriasFragment();
            switch (savedInstanceState.getInt("fragmento")){
                case R.id.nav_citas:
                    fragment = new CitasFragment();
                    actualFrag = R.id.nav_citas;
                    break;
                case R.id.nav_asesores:
                    fragment = new AsesoresFragment();
                    actualFrag = R.id.nav_asesores;
                    break;
                case R.id.nav_solicitar:
                    fragment = new SolicitarFragment();
                    actualFrag = R.id.nav_solicitar;
                    break;
                default:
                    fragment = new MateriasFragment();
                    actualFrag = R.id.nav_materias;
                    break;
            }
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        } else {
            fragment = new MateriasFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.content_frame, fragment);
            ft.commit();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        Fragment fragment = null;
        switch (actualFrag){
            case R.id.nav_citas:
                fragment = new CitasFragment();
                actualFrag = R.id.nav_citas;
                break;
            case R.id.nav_asesores:
                fragment = new AsesoresFragment();
                actualFrag = R.id.nav_asesores;
                break;
            case R.id.nav_solicitar:
                fragment = new SolicitarFragment();
                actualFrag = R.id.nav_asesores;
                break;
            default:
                fragment = new MateriasFragment();
                actualFrag = R.id.nav_materias;
                break;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();

        Fragment fragment = null;
        Intent intent = null;
        switch (id){
            case R.id.nav_citas:
                fragment = new CitasFragment();
                actualFrag = R.id.nav_citas;
                break;
            case R.id.nav_asesores:
                fragment = new AsesoresFragment();
                actualFrag = R.id.nav_asesores;
                break;
            case R.id.nav_solicitar:
                fragment = new SolicitarFragment();
                actualFrag = R.id.nav_asesores;
                break;
            default:
                fragment = new MateriasFragment();
                actualFrag = R.id.nav_materias;
                break;
        }

        if (fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        } else {
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("fragmento", actualFrag);
        savedInstanceState.putString("matricula", matricula);
    }
}