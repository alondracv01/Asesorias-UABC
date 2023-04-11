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

public class AsesoresFragment extends Fragment{
    private SQLiteDatabase db;
    private SQLiteDatabase db2;
    public MainActivity main;

    private String matricula = "";
    private String matriculaAsesor = "";
    private String materias = "";
    private Asesor asesor;
    private ArrayList<Asesor> mLista = new ArrayList<>();
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

        main = (MainActivity) getActivity();
        matricula = main.matricula;

        SQLiteOpenHelper asesoresDatabaseHelper = new AsesoresDatabaseHelper(getContext());
        try{
            db = asesoresDatabaseHelper.getReadableDatabase();
            Cursor fila=db.rawQuery("select NOMBRE, APELLIDO, CORREO, LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, MATRICULA from ASESORES",null);
            while(fila.moveToNext()) {
                matriculaAsesor = fila.getString(8);

                SQLiteOpenHelper materiasDatabaseHelper = new MateriasDatabaseHelper(getContext());
                try{
                    db2 = materiasDatabaseHelper.getReadableDatabase();
                    Cursor cursor=db2.rawQuery("select NOMBRE from MATERIAS where MATRICULA = '" + matriculaAsesor + "' and ASESOR = 1",null);
                    int i = 0;
                    while(cursor.moveToNext()) {
                        if (i == 0 )
                            materias = cursor.getString(0);
                        else
                            materias = materias + "\n" + cursor.getString(0);
                        i++;
                    }
                    cursor.close();
                    db2.close();
                } catch (SQLiteException e){
                    Toast toast = Toast.makeText(getContext(), "Database unavaible: onCreate", Toast.LENGTH_SHORT);
                    toast.show();
                }
                asesor = new Asesor(fila.getString(0) + " " + fila.getString(1), fila.getString(2), materias, fila.getString(3), fila.getString(4), fila.getString(5), fila.getString(6), fila.getString(7));
                mLista.add(asesor);
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

        pAsesores = (ListView) view.findViewById(R.id.asesoresListView);

        AsesorListAdapter adapter = new AsesorListAdapter(getContext(), R.layout.adapter_view_layout, mLista);
        pAsesores.setAdapter(adapter);
    }
}