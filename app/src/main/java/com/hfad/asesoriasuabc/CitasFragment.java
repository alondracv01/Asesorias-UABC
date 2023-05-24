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

import com.hfad.asesoriasuabc.Database.AsesoresDatabaseHelper;
import com.hfad.asesoriasuabc.Database.CitasDatabaseHelper;
import com.hfad.asesoriasuabc.Database.MateriasDatabaseHelper;

import java.util.ArrayList;

public class CitasFragment extends Fragment{
    private SQLiteDatabase db;
    private SQLiteDatabase db2;
    public MainActivity main;

    private String matricula = "";
    private String matriculaAsesor = "";
    private String nombreAsesor = "";
    private String materia = "";
    private String fecha = "";
    private String hora = "";
    private String estado = "";
    private int id;
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
            Cursor fila=db.rawQuery("select MATRICULA_ASESOR, MATERIA, DAY, MONTH, YEAR, HORA_INICIO, HORA_FIN, ESTADO from CITAS where MATRICULA_ASESORADO = '" + matricula + "'",null);
            while(fila.moveToNext()) {
                noCitas.setVisibility(View.INVISIBLE);
                matriculaAsesor = fila.getString(0);

                SQLiteOpenHelper asesoresDatabaseHelper = new AsesoresDatabaseHelper(getContext());
                try{
                    db2 = asesoresDatabaseHelper.getReadableDatabase();
                    Cursor cursor=db2.rawQuery("select NOMBRE, APELLIDO from ASESORES where MATRICULA = '" + matriculaAsesor + "'",null);
                    int i = 0;
                    while(cursor.moveToNext()) {
                        nombreAsesor = cursor.getString(0) + " " + cursor.getString(1);
                    }
                    cursor.close();
                    db2.close();
                } catch (SQLiteException e){
                    Toast toast = Toast.makeText(getContext(), "Database unavaible: onCreate", Toast.LENGTH_SHORT);
                    toast.show();
                }
                cita = new Cita(nombreAsesor, fila.getString(1), fila.getString(2) + "/" + fila.getString(3) + "/" + fila.getString(4), fila.getString(5) + "-" + fila.getString(6), fila.getString(7));
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
