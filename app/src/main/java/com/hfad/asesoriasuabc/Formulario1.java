package com.hfad.asesoriasuabc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hfad.asesoriasuabc.Database.UsuariosDatabaseHelper;

//Asesorado
public class Formulario1 extends AppCompatActivity {

    private SQLiteDatabase db;
    Button registro;
    EditText matriculaEdit;
    EditText contrasenaEdit;
    EditText nombreEdit;
    EditText apellidoEdit;
    EditText correoEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario1);

        matriculaEdit = (EditText) findViewById(R.id.matricula_editText);
        nombreEdit = (EditText) findViewById(R.id.nombre_editText);
        apellidoEdit = (EditText) findViewById(R.id.apellido_editText);
        correoEdit = (EditText) findViewById(R.id.correo_editText);
        contrasenaEdit = (EditText) findViewById(R.id.contrasena_editText);
        registro = (Button) findViewById(R.id.registro_button);

    }
    @Override
    protected void onStart() {
        super.onStart();
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuariosDatabaseHelper usuariosDBHelper = new UsuariosDatabaseHelper(Formulario1.this);
                SQLiteDatabase db = usuariosDBHelper.getWritableDatabase();
                ContentValues userValues = new ContentValues();
                userValues.put("NOMBRE", nombreEdit.getText().toString());
                userValues.put("APELLIDO", apellidoEdit.getText().toString());
                userValues.put("CORREO", correoEdit.getText().toString());
                userValues.put("MATRICULA", matriculaEdit.getText().toString());
                userValues.put("CONTRASENA", contrasenaEdit.getText().toString());
                db.insert("USUARIOS", null, userValues);

                Intent intent = new Intent(Formulario1.this,  MainActivity.class);
                startActivity(intent);
            }
        });
    }
}