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

public class AsesorListAdapter extends ArrayAdapter<Asesor> {
    private static final String TAG = "AsesorListAdapter";
    private Context mContext;
    int mResource;

    public AsesorListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Asesor> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String nombre = getItem(position).getNombre();
        String correo = getItem(position).getCorreo();
        String materias = getItem(position).getMaterias();
        String lunes = getItem(position).getLunes();
        String martes = getItem(position).getMartes();
        String miercoles = getItem(position).getMiercoles();
        String jueves = getItem(position).getJueves();
        String viernes = getItem(position).getViernes();

        Asesor asesor = new Asesor(nombre,correo,materias,lunes,martes,miercoles,jueves,viernes);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvNombre = (TextView) convertView.findViewById(R.id.asesorTextView);
        TextView tvCorreo = (TextView) convertView.findViewById(R.id.correoTextView);
        TextView tvMaterias = (TextView) convertView.findViewById(R.id.materiasTextView);
        TextView tvLunes = (TextView) convertView.findViewById(R.id.lunesTextView);
        TextView tvMartes = (TextView) convertView.findViewById(R.id.martesTextView);
        TextView tvMiercoles = (TextView) convertView.findViewById(R.id.miercolesTextView);
        TextView tvJueves = (TextView) convertView.findViewById(R.id.juevesTextView);
        TextView tvViernes = (TextView) convertView.findViewById(R.id.viernesTextView);

        tvNombre.setText(nombre);
        tvCorreo.setText(correo);
        tvMaterias.setText(materias);
        tvLunes.setText(lunes);
        tvMartes.setText(martes);
        tvMiercoles.setText(miercoles);
        tvJueves.setText(jueves);
        tvViernes.setText(viernes);

        return convertView;
    }
}
