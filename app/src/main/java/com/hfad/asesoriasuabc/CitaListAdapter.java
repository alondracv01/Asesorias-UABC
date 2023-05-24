package com.hfad.asesoriasuabc;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CitaListAdapter extends ArrayAdapter<Cita>{
    private static final String TAG = "CitaListAdapter";
    private Context mContext;
    int mResource;

    public CitaListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Cita> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String asesor = getItem(position).getAsesor();
        String materia = getItem(position).getMateria();
        String fecha = getItem(position).getFecha();
        String hora = getItem(position).getHora();
        String estado = getItem(position).getEstado();

        Cita cita = new Cita(asesor,materia,fecha,hora,estado);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvAsesor = (TextView) convertView.findViewById(R.id.asesorCitaTextView);
        TextView tvMateria = (TextView) convertView.findViewById(R.id.materiaTextView);
        TextView tvFecha = (TextView) convertView.findViewById(R.id.fechaTextView);
        TextView tvHora = (TextView) convertView.findViewById(R.id.horaTextView);
        TextView tvEstado = (TextView) convertView.findViewById(R.id.estadoTextView);
        Button boton = (Button) convertView.findViewById(R.id.buttonCE);

        tvAsesor.setText(asesor);
        tvMateria.setText(materia);
        tvFecha.setText(fecha);
        tvHora.setText(hora);
        tvEstado.setText(estado);
        if (estado.equals("EN ESPERA") || estado.equals("CONFIRMADA")){
            boton.setText("Cancelar");
        }else {
            if (estado.equals("FINALIZADA") || estado.equals("ASESOR N/A")){
                boton.setText("Evaluar asesoria");
            }else {
                boton.setVisibility(View.INVISIBLE);
            }
        }

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetallesFragment detallesFragment = new DetallesFragment();
                detallesFragment.show(((AppCompatActivity)mContext).getFragmentManager(), "DetallesPopup");
            }
        });

        return convertView;
    }
}
