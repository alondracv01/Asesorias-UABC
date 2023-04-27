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
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.fragment.app.Fragment;

import com.hfad.asesoriasuabc.Database.AsesoresDatabaseHelper;
import com.hfad.asesoriasuabc.Database.MateriasDatabaseHelper;
import com.hfad.asesoriasuabc.Database.UsuariosDatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SolicitarFragment extends Fragment{
    private SQLiteDatabase db;
    private SQLiteDatabase db2;
    public MainActivity main;
    private String matricula = "";
    private String matriculaAsesor = "";
    private String materia = "";
    private String nombre = "";

    private TextView mAsesor;
    private TextView mHorario;
    private TextView noDisponibles;
    private RadioGroup mMateriasGroup;
    private RadioGroup mAsesoresGroup;
    private RadioGroup mHorarioGroup;

    private RadioButton button;
    private CalendarView mCalendar;
    private Spinner mHorariosAsesor;
    SQLiteOpenHelper materiasDatabaseHelper;
    SQLiteOpenHelper asesoresDatabaseHelper;

//    Date currentTime = Calendar.getInstance().getTime();
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
//    String currentDateandTime = sdf.format(new Date());
    long currentDate = new Date().getTime();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_solicitar, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        main = (MainActivity) getActivity();
        matricula = main.matricula;
    }

    public List<String> getHorarios(String dia, String matriculaAsesor){
        List<String> labels = new ArrayList<String>();

        asesoresDatabaseHelper = new AsesoresDatabaseHelper(getContext());
        try{
            db = asesoresDatabaseHelper.getReadableDatabase();
            Cursor fila = db.rawQuery("select "+ dia +" from ASESORES where MATRICULA = '" + matriculaAsesor ,null);
            while(fila.moveToNext()) {
                labels.add(fila.getString(0));
            }
            fila.close();
            db.close();
        } catch (SQLiteException e){
            Toast toast = Toast.makeText(getContext(), "Database unavaible: onStart", Toast.LENGTH_SHORT);
            toast.show();
        }


        // returning lables
        return labels;
    }

    @Override
    public void onStart(){
        super.onStart();
        View view = getView();

        mMateriasGroup = (RadioGroup) view.findViewById(R.id.groupMaterias);
        mAsesoresGroup = (RadioGroup) view.findViewById(R.id.groupAsesores);
        mHorarioGroup = (RadioGroup) view.findViewById(R.id.groupHorario);

        mAsesor = (TextView) view.findViewById(R.id.solicitarAsesorText);
        mHorario = (TextView) view.findViewById(R.id.solicitarHorarioText);
        noDisponibles = (TextView) view.findViewById(R.id.noDisponiblesText);

        mCalendar = (CalendarView) view.findViewById(R.id.calendario);
        mHorariosAsesor = (Spinner) view.findViewById(R.id.horarios);

        mAsesor.setVisibility(View.INVISIBLE);
        mAsesoresGroup.setVisibility(View.INVISIBLE);
        mHorario.setVisibility(View.INVISIBLE);
        mHorarioGroup.setVisibility(View.INVISIBLE);
        noDisponibles.setVisibility(View.INVISIBLE);

        mCalendar.setMinDate(currentDate);

        materiasDatabaseHelper = new MateriasDatabaseHelper(getContext());
        try{
            db = materiasDatabaseHelper.getReadableDatabase();
            Cursor fila=db.rawQuery("select NOMBRE from MATERIAS where MATRICULA = '" + matricula + "' and CURSANDO = 1",null);
            while(fila.moveToNext()) {
                materia=fila.getString(0);
                button = new RadioButton(getContext());
                button.setText(materia);
                mMateriasGroup.addView(button);
            }
            fila.close();
            db.close();
        } catch (SQLiteException e){
            Toast toast = Toast.makeText(getContext(), "Database unavaible: onStart", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        View view = getView();
        mMateriasGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton) view.findViewById(checkedId);
                String radioText = rb.getText().toString();

                mHorario.setVisibility(View.INVISIBLE);
                mHorarioGroup.setVisibility(View.INVISIBLE);

                mAsesor.setVisibility(View.VISIBLE);
                noDisponibles.setVisibility(View.VISIBLE);
                mAsesoresGroup.removeAllViews();
                mHorarioGroup.removeAllViews();

                materiasDatabaseHelper = new MateriasDatabaseHelper(getContext());
                try{
                    db = materiasDatabaseHelper.getReadableDatabase();
                    Cursor fila=db.rawQuery("select MATRICULA from MATERIAS where NOMBRE = '" + radioText + "' and ASESOR = 1",null);
                    while(fila.moveToNext()) {
                        matriculaAsesor=fila.getString(0);
                        asesoresDatabaseHelper = new AsesoresDatabaseHelper(getContext());
                        try{
                            db2 = asesoresDatabaseHelper.getReadableDatabase();
                            Cursor fila2 =db2.rawQuery("select NOMBRE, APELLIDO from ASESORES where MATRICULA = '" + matriculaAsesor + "'",null);
                            while(fila2.moveToNext()) {
                                noDisponibles.setVisibility(View.INVISIBLE);
                                mAsesoresGroup.setVisibility(View.VISIBLE);
                                nombre=fila2.getString(0) + " " + fila2.getString(1);
                                button = new RadioButton(getContext());
                                button.setText(nombre);
                                mAsesoresGroup.addView(button);
                            }
                            fila2.close();
                            db2.close();
                        } catch (SQLiteException e){
                            Toast toast = Toast.makeText(getContext(), "Database unavaible: onResume Asesores", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                    fila.close();
                    db.close();
                } catch (SQLiteException e){
                    Toast toast = Toast.makeText(getContext(), "Database unavaible: onResume Materias", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        mAsesoresGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton) view.findViewById(checkedId);
                String radioText = rb.getText().toString();
                String[] parts = radioText.split(" ");

                mHorario.setVisibility(View.VISIBLE);
                mHorarioGroup.setVisibility(View.VISIBLE);
                mHorarioGroup.removeAllViews();

                asesoresDatabaseHelper = new AsesoresDatabaseHelper(getContext());
                try{
                    db = asesoresDatabaseHelper.getReadableDatabase();
                    Cursor fila=db.rawQuery("select LUNES, MARTES, MIERCOLES, JUEVES, VIERNES from ASESORES where NOMBRE = '" + parts[0] + "' and APELLIDO = '" + parts[1] + "'",null);
                    while(fila.moveToNext()) {
                        //Esto nomas es una base, en lugar de esto se pondria el calendario
                        button = new RadioButton(getContext());
                        button.setText("Lunes: " + fila.getString(0));
                        mHorarioGroup.addView(button);

                        button = new RadioButton(getContext());
                        button.setText("Martes: " + fila.getString(1));
                        mHorarioGroup.addView(button);

                        button = new RadioButton(getContext());
                        button.setText("Miercoles: " + fila.getString(2));
                        mHorarioGroup.addView(button);

                        button = new RadioButton(getContext());
                        button.setText("Jueves: " + fila.getString(3));
                        mHorarioGroup.addView(button);

                        button = new RadioButton(getContext());
                        button.setText("Viernes: " + fila.getString(4));
                        mHorarioGroup.addView(button);
                    }
                    fila.close();
                    db.close();
                } catch (SQLiteException e){
                    Toast toast = Toast.makeText(getContext(), "Database unavaible: onResume Asesores", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
