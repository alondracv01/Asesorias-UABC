package com.hfad.asesoriasuabc;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CitaListAdapter extends ArrayAdapter<Cita>{
    private static final String TAG = "CitaListAdapter";
    private Context mContext;
    int mResource;
    public MainActivity main;
    private int estAsesor = 0;

    public CitaListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Cita> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

        main = (MainActivity) mContext;
        estAsesor = main.asesor;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String asesor = getItem(position).getAsesor();
        String materia = getItem(position).getMateria();
        String fecha = getItem(position).getFecha();
        String hora = getItem(position).getHora();
        String estado = getItem(position).getEstado();
        int id = getItem(position).getId();

        Cita cita = new Cita(asesor,materia,fecha,hora,estado,id);

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
        if (estado.equals("CONFIRMADA")){
            boton.setText("Cancelar");
        }else {
            if (estado.equals("EN ESPERA") && estAsesor == 0){
                boton.setText("Cancelar");
            } else if (estado.equals("EN ESPERA") && estAsesor == 1) {
                boton.setText("Responder");
            } else if (estado.equals("FINALIZADA") || (estado.equals("ASESOR N/A") && estAsesor == 0) || (estado.equals("ASESORADO N/A") && estAsesor == 1)){
                boton.setText("Evaluar");
            }else {
                boton.setVisibility(View.INVISIBLE);
            }
        }

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("Materia", materia);
                args.putString("Asesor", asesor);
                args.putString("Fecha", fecha);
                args.putString("Hora", hora);
                args.putString("Estado", estado);
                args.putInt("Id", id);
                DetallesFragment detallesFragment = new DetallesFragment();
                detallesFragment.setArguments(args);
                detallesFragment.show(((AppCompatActivity)mContext).getFragmentManager(), "DetallesFragment");
            }
        });
        return convertView;
    }
}
