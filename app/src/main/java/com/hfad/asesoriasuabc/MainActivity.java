package com.hfad.asesoriasuabc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.navigation.NavigationView;
import com.hfad.asesoriasuabc.Database.AsesoresDatabaseHelper;
import com.hfad.asesoriasuabc.Database.MateriasDatabaseHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private int actualFrag = R.id.nav_materias;

    public String matricula;
    private SQLiteDatabase db;
    public int asesor = 0;
    NavigationView navigationView;
    MenuItem visasesor, visasesorado, solicitar;

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

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        visasesor = menu.findItem(R.id.nav_visasesor);
        visasesor.setVisible(false);
        visasesorado = menu.findItem(R.id.nav_visasesorado);
        visasesorado.setVisible(false);
        solicitar = menu.findItem(R.id.nav_solicitar);
        solicitar.setVisible(true);

        SQLiteOpenHelper asesoresDatabaseHelper = new AsesoresDatabaseHelper(this);
        try{
            db = asesoresDatabaseHelper.getReadableDatabase();
            Cursor fila=db.rawQuery("select MATRICULA from ASESORES where MATRICULA = '" + matricula + "'",null);
            while(fila.moveToNext()) {
                visasesor.setVisible(true);
            }
            fila.close();
            db.close();
        } catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavaible: onCreate", Toast.LENGTH_SHORT);
            toast.show();
        }

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
                case R.id.nav_visasesor:
                    asesor = 1;
                    visasesor.setVisible(false);
                    visasesorado.setVisible(true);
                    solicitar.setVisible(false);
                    fragment = new MateriasFragment();
                    actualFrag = R.id.nav_materias;
                    break;
                case R.id.nav_visasesorado:
                    asesor = 0;
                    visasesor.setVisible(true);
                    visasesorado.setVisible(false);
                    solicitar.setVisible(true);
                    fragment = new MateriasFragment();
                    actualFrag = R.id.nav_materias;
                    break;
                case R.id.nav_cerrarsesion:
                    //No me salio esto jaja
                    startActivity(new Intent(MainActivity.this, LogInActivity.class));
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

        Menu menu = navigationView.getMenu();
        visasesor = menu.findItem(R.id.nav_visasesor);
        visasesor.setVisible(false);
        visasesorado = menu.findItem(R.id.nav_visasesorado);
        visasesorado.setVisible(false);
        solicitar = menu.findItem(R.id.nav_solicitar);
        solicitar.setVisible(true);

        SQLiteOpenHelper asesoresDatabaseHelper = new AsesoresDatabaseHelper(this);
        try{
            db = asesoresDatabaseHelper.getReadableDatabase();
            Cursor fila=db.rawQuery("select MATRICULA from ASESORES where MATRICULA = '" + matricula + "'",null);
            while(fila.moveToNext()) {
                visasesor.setVisible(true);
            }
            fila.close();
            db.close();
        } catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavaible: onCreate", Toast.LENGTH_SHORT);
            toast.show();
        }

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
            case R.id.nav_visasesor:
                asesor = 1;
                visasesor.setVisible(false);
                visasesorado.setVisible(true);
                solicitar.setVisible(false);
                fragment = new MateriasFragment();
                actualFrag = R.id.nav_materias;
                break;
            case R.id.nav_visasesorado:
                asesor = 0;
                visasesor.setVisible(true);
                visasesorado.setVisible(false);
                solicitar.setVisible(true);
                fragment = new MateriasFragment();
                actualFrag = R.id.nav_materias;
                break;
            case R.id.nav_cerrarsesion:
                //No me salio esto jaja
                startActivity(new Intent(MainActivity.this, LogInActivity.class));
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
            case R.id.nav_visasesor:
                asesor = 1;
                visasesor.setVisible(false);
                visasesorado.setVisible(true);
                solicitar.setVisible(false);
                fragment = new MateriasFragment();
                actualFrag = R.id.nav_materias;
                break;
            case R.id.nav_visasesorado:
                asesor = 0;
                visasesor.setVisible(true);
                visasesorado.setVisible(false);
                solicitar.setVisible(true);
                fragment = new MateriasFragment();
                actualFrag = R.id.nav_materias;
                break;
            case R.id.nav_cerrarsesion:
                //No me salio esto jaja
                startActivity(new Intent(MainActivity.this, LogInActivity.class));
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
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("fragmento", actualFrag);
        savedInstanceState.putString("matricula", matricula);
        savedInstanceState.putInt("asesor", asesor);
    }
}