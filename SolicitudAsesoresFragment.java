package com.hfad.asesoriasuabc;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.hfad.asesoriasuabc.Database.AsesoresDatabaseHelper;
import com.hfad.asesoriasuabc.Database.MateriasDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class SolicitudAsesoresFragment extends Fragment{
    private SQLiteDatabase db;
    private SQLiteDatabase db2;
    public MainActivity main;
    
    private ListView pAsesores;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_asesores, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SQLiteOpenHelper AltaAsesoresDatabaseHelper = new AltaAsesoresDatabaseHelper(getContext());
        try{
            db = AltaAsesoresDatabaseHelper.getReadableDatabase();
            Cursor fila=db.rawQuery("select NOMBRE, MATRICULA, PROMEDIO, MATERIAS from SOLICITUDES",null);
            while(fila.moveToNext()) {
                solicitud = new Solicitud(fila.getString(0),  fila.getString(1), fila.getString(2),  fila.getString(3));
                mLista.add(Solicitud);
            }
            fila.close();
            db.close();
        } catch (SQLiteException e){
            Toast toast = Toast.makeText(getContext(), "Database unavaible: onCreate", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        View view = getView();

        pSolicitudesAsesores = (ListView) view.findViewById(R.id.solicitudesasesoresListView);

        SolocitudesAsesoresListAdapter adapter = new SolocitudesAsesoresListAdapter(getContext(), R.layout.adapter_view_layout, mLista);
        pSolicitudesAsesores.setAdapter(adapter);
    }
}