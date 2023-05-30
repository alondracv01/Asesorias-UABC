package com.hfad.asesoriasuabc;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Layout;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hfad.asesoriasuabc.Database.CitasDatabaseHelper;

public class DetallesFragment extends DialogFragment {

    String materia;
    String asesor;
    String fecha;
    String hora;
    String estado;
    int id;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popup, container, false);
        Bundle mArgs = getArguments();
        materia = mArgs.getString("Materia");
        asesor = mArgs.getString("Asesor");
        fecha = mArgs.getString("Fecha");
        hora = mArgs.getString("Hora");
        estado = mArgs.getString("Estado");
        id = mArgs.getInt("Id");
        return view;
    }

    @Override
    public void dismiss() {
        //Este fue mi intento por intentar dar refresh al fragment pero nomas no se pudo
        Fragment currentFragment = getActivity().getFragmentManager().findFragmentById(R.id.CitasFragment);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(currentFragment);
        fragmentTransaction.attach(currentFragment);
        fragmentTransaction.commit();
        super.dismiss();
    }


    public void onResume() {
        super.onResume();

        TextView tvAsesor = (TextView) getDialog().findViewById(R.id.asesorPopup);
        TextView tvMateria = (TextView) getDialog().findViewById(R.id.materiaPopup);
        TextView tvFecha = (TextView) getDialog().findViewById(R.id.fechaPopup);
        TextView tvHora = (TextView) getDialog().findViewById(R.id.horaPopup);
        TextView tvEstado = (TextView) getDialog().findViewById(R.id.estadoPopup);
        Button cerrar = (Button) getDialog().findViewById(R.id.cerrar);
        Button evaluar = (Button) getDialog().findViewById(R.id.evaluar);
        Button cancelar = (Button) getDialog().findViewById(R.id.cancelar);

        tvAsesor.setText(asesor);
        tvMateria.setText(materia);
        tvFecha.setText(fecha);
        tvHora.setText(hora);
        tvEstado.setText(estado);

        if (estado.equals("EN ESPERA") || estado.equals("CONFIRMADA")){
            evaluar.setVisibility(View.INVISIBLE);
            cancelar.setVisibility(View.VISIBLE);
        }else {
            if (estado.equals("FINALIZADA") || estado.equals("ASESOR N/A")){
                evaluar.setVisibility(View.VISIBLE);
                cancelar.setVisibility(View.INVISIBLE);
            }else {
                evaluar.setVisibility(View.INVISIBLE);
                cancelar.setVisibility(View.INVISIBLE);
            }
        }

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putInt("ID", id);
                CancelarCitaFragment notificacion = new CancelarCitaFragment();
                notificacion.setArguments(args);
                notificacion.show(getFragmentManager(), "cita");
                getDialog().dismiss();
            }
        });

        Window window = getDialog().getWindow();
        Point size = new Point();

        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        int width = size.x;

        window.setLayout((int) (width * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
    }
}
