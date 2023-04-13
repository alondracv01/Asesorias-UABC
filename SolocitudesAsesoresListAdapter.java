package com.hfad.asesoriasuabc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SolicitudesAsesoresListAdapter extends ArrayAdapter<Asesor> {
    private static final String TAG = "SolicitudesAsesoresListAdapter";
    private Context mContext;
    int mResource;

    public SolicitudesAsesoresListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Solicitudes> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String nombre = getItem(position).getNombre();
        String correo = getItem(position).getMatricula();
        String nombre = getItem(position).getPromedio();
        String materias = getItem(position).getMaterias();

        Asesor asesor = new Asesor(nombre,matricula,promedio,materias);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvNombre = (TextView) convertView.findViewById(R.id.nombreTextView);
        TextView tvMatricula = (TextView) convertView.findViewById(R.id.matriculaTextView);
        TextView tvPromedio = (TextView) convertView.findViewById(R.id.promedioTextView);
        TextView tvMaterias = (TextView) convertView.findViewById(R.id.materiasTextView);

        tvNombre.setText(nombre);
        tvMatricula.setText(matricula);
        tvPromedio.setText(promedio);
        tvMaterias.setText(materias);

        return convertView;
    }
}