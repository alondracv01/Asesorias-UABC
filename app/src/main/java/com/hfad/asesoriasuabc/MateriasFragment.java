package com.hfad.asesoriasuabc;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.hfad.asesoriasuabc.Database.FotosDatabaseHelper;
import com.hfad.asesoriasuabc.Database.MateriasDatabaseHelper;
import com.hfad.asesoriasuabc.Database.UsuariosDatabaseHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MateriasFragment extends Fragment{
    private SQLiteDatabase db;
    private Cursor fila;
    public MainActivity main;

    private String matricula = "";
    private String nombre = "";
    private String apellido = "";
    private String materia = "";
    private int asesor = 0;
    private List<String> mLista = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;

    private TextView pNombre;
    private TextView tMaterias;
    private ListView pMaterias;
    //private ImageView fotoPerfil;
    //private ImageButton editar1;

    private int numImagen = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_materias, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        main = (MainActivity) getActivity();
        matricula = main.matricula;
        asesor = main.asesor;
        Log.d("MateriasFragment", "Matricula: " + matricula);

        SQLiteOpenHelper usuariosDatabaseHelper = new UsuariosDatabaseHelper(getContext());
        try {
            db = usuariosDatabaseHelper.getReadableDatabase();
            Cursor fila = db.rawQuery("select NOMBRE,APELLIDO from USUARIOS where MATRICULA = '" + matricula + "'", null);
            if (fila.moveToFirst()) {
                nombre = fila.getString(0);
                apellido = fila.getString(1);
            } else {
                Log.d("MateriasFragment", "Incorrecto");
                Toast.makeText(getContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
            }
            fila.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getContext(), "Database unavaible: onCreate", Toast.LENGTH_SHORT);
            toast.show();
        }

        SQLiteOpenHelper materiasDatabaseHelper = new MateriasDatabaseHelper(getContext());
        try {
            db = materiasDatabaseHelper.getReadableDatabase();
            if(asesor == 0)
                fila = db.rawQuery("select NOMBRE from MATERIAS where MATRICULA = '" + matricula + "' and CURSANDO = 1", null);
            else
                fila=db.rawQuery("select NOMBRE from MATERIAS where MATRICULA = '" + matricula + "' and ASESOR = 1",null);
            while (fila.moveToNext()) {
                materia = fila.getString(0);
                mLista.add(materia);
            }
            fila.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getContext(), "Database unavaible: onCreate", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        View view = getView();


        boolean clicked = false;

        pNombre = (TextView) view.findViewById(R.id.nombre);
        pMaterias = (ListView) view.findViewById(R.id.materiasListView);
        tMaterias = (TextView) view.findViewById(R.id.materiasText);

        /*fotoPerfil = (ImageView) view.findViewById(R.id.fotoPerfil);
        editar1 = (ImageButton) view.findViewById(R.id.editar_button1);*/

        pNombre.setText(nombre + " " + apellido);
        mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mLista);
        pMaterias.setAdapter(mAdapter);

        if (asesor != 0 ){
            tMaterias.setText("Materias que asesoro");
        }

        /*SQLiteOpenHelper fotosDatabaseHelper = new FotosDatabaseHelper(getContext());
        try{
            db = fotosDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("IMAGENES",
                    new String[]{"CLICKED_PERFIL"},
                    "_id LIKE ?", new String[]{String.valueOf(1)}, null, null, null);
            while(cursor.moveToNext()){
                int numCol = cursor.getColumnIndex("CLICKED_PERFIL");
                if(numCol >= 0){
                    clicked = cursor.getInt(numCol) > 0;
                }
            }
            cursor.close();

            if (clicked){
                cursor = db.query("IMAGENES",
                        new String[]{"IMG_PERFIL"},
                        "_id LIKE ?", new String[]{String.valueOf(1)}, null, null, null);
                while(cursor.moveToNext()){
                    int numCol = cursor.getColumnIndex("IMG_PERFIL");
                    if(numCol >= 0){
                        byte[] blob = cursor.getBlob(numCol);
                        ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                        Bitmap bitmap = BitmapFactory.decodeStream(bais);
                        fotoPerfil.setImageBitmap(bitmap);
                    }
                }
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e){
            Toast toast = Toast.makeText(getContext(), "Database unavaible: update question", Toast.LENGTH_SHORT);
            toast.show();
        }

        editar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numImagen = 1;
                cargarImagen();
            }
        });*/
    }

   /* private void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(Intent.createChooser(intent, "Seleccione la aplicacion"), 10);
    }*/
/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == MainActivity.RESULT_OK){
            Uri path = data.getData();
            if (numImagen == 1){
                fotoPerfil.setImageURI(path);
                Bitmap bitmap = ((BitmapDrawable) fotoPerfil.getDrawable()).getBitmap();
                ExifInterface ei = null;
                try {
                    ei = new ExifInterface(String.valueOf(path));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                Bitmap rotatedBitmap = null;
                switch(orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotatedBitmap = rotateImage(bitmap, 90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotatedBitmap = rotateImage(bitmap, 180);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotatedBitmap = rotateImage(bitmap, 270);
                        break;
                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        rotatedBitmap = bitmap;
                }

                guardarImagen(rotatedBitmap, 1);
            }
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public void guardarImagen(Bitmap bitmap, int img){
        // tamaño del baos depende del tamaño de tus imagenes en promedio
        ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60 , baos);
        byte[] blob = baos.toByteArray();
        // aqui tenemos el byte[] con el imagen comprimido, ahora lo guardemos en SQLite
        SQLiteOpenHelper fotosDatabaseHelper = new FotosDatabaseHelper(getContext());
        try{
            db = fotosDatabaseHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            if (img == 1){
                cv.put("IMG_PERFIL",blob);
                cv.put("CLICKED_PERFIL", true);
            }
            db.update("IMAGENES", cv,"_id = 1", null);
            db.close();
        } catch (SQLiteException e){
            Toast toast = Toast.makeText(getContext(), "Database unavaible: guardar imagen", Toast.LENGTH_SHORT);
            toast.show();
        }
    }*/
}
