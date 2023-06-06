package com.hfad.asesoriasuabc;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.hfad.asesoriasuabc.Database.AsesoresDatabaseHelper;
import com.hfad.asesoriasuabc.Database.CitasDatabaseHelper;
import com.hfad.asesoriasuabc.Database.MateriasDatabaseHelper;
import com.hfad.asesoriasuabc.Database.UsuariosDatabaseHelper;

import java.util.ArrayList;

public class CitasFragment extends Fragment{
    private SQLiteDatabase db;
    private SQLiteDatabase db2;
    private Cursor fila;
    private Cursor cursor;
    public MainActivity main;

    private String matricula = "";
    private String matricula2 = "";
    private String nombre = "";
    private int asesor = 0;
    private Cita cita;
    private ArrayList<Cita> mLista = new ArrayList<>();
    private ListView pCitas;
    private TextView noCitas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_citas, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        main = (MainActivity) getActivity();
        matricula = main.matricula;
        asesor = main.asesor;
    }

    @Override
    public void onStart(){
        super.onStart();
        View view = getView();

        noCitas = (TextView) view.findViewById(R.id.noCitasTextView);
    }

    @Override
    public void onResume() {
        super.onResume();
        View view = getView();
        noCitas.setVisibility(View.VISIBLE);

        SQLiteOpenHelper citasDatabaseHelper = new CitasDatabaseHelper(getContext());
        try{
            db = citasDatabaseHelper.getReadableDatabase();
            if(asesor == 0)
                fila=db.rawQuery("select MATRICULA_ASESOR, MATERIA, DAY, MONTH, YEAR, HORA_INICIO, HORA_FIN, ESTADO, _id from CITAS where MATRICULA_ASESORADO = '" + matricula + "'",null);
            else
                fila=db.rawQuery("select MATRICULA_ASESORADO, MATERIA, DAY, MONTH, YEAR, HORA_INICIO, HORA_FIN, ESTADO, _id from CITAS where MATRICULA_ASESOR = '" + matricula + "'",null);
            while(fila.moveToNext()) {
                noCitas.setVisibility(View.INVISIBLE);
                matricula2 = fila.getString(0);

                try{
                    if (asesor == 0) {
                        SQLiteOpenHelper asesoresDatabaseHelper = new AsesoresDatabaseHelper(getContext());
                        db2 = asesoresDatabaseHelper.getReadableDatabase();
                        cursor = db2.rawQuery("select NOMBRE, APELLIDO from ASESORES where MATRICULA = '" + matricula2 + "'", null);
                    }else{
                        SQLiteOpenHelper usuariosDatabaseHelper = new UsuariosDatabaseHelper(getContext());
                        db2 = usuariosDatabaseHelper.getReadableDatabase();
                        cursor = db2.rawQuery("select NOMBRE, APELLIDO from USUARIOS where MATRICULA = '" + matricula2 + "'", null);
                    }
                    int i = 0;
                    while(cursor.moveToNext()) {
                        nombre = cursor.getString(0) + " " + cursor.getString(1);
                    }
                    cursor.close();
                    db2.close();
                } catch (SQLiteException e){
                    Toast toast = Toast.makeText(getContext(), "Database unavaible: onCreate", Toast.LENGTH_SHORT);
                    toast.show();
                }
                cita = new Cita(nombre, fila.getString(1), fila.getString(2) + "/" + fila.getString(3) + "/" + fila.getString(4), fila.getString(5) + "-" + fila.getString(6), fila.getString(7), fila.getInt(8));
                mLista.add(cita);
            }
            fila.close();
            db.close();
        } catch (SQLiteException e){
            Toast toast = Toast.makeText(getContext(), "Database unavaible: onStart", Toast.LENGTH_SHORT);
            toast.show();
        }

        pCitas = (ListView) view.findViewById(R.id.citasListView);

        CitaListAdapter adapter = new CitaListAdapter(getContext(), R.layout.adapter_view_layout_citas, mLista);
        pCitas.setAdapter(adapter);
    }
}
