package com.hfad.asesoriasuabc;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.hfad.asesoriasuabc.Database.UsuariosDatabaseHelper;

public class LogInActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    String matricula = "";
    String contrasena = "";

    Button ingresar;
    EditText matriculaEdit;
    EditText contrasenaEdit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        matriculaEdit = (EditText) findViewById(R.id.usuario_editText);
        contrasenaEdit = (EditText) findViewById(R.id.contrasena_editText);
        ingresar = (Button) findViewById(R.id.ingresar_button);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matricula = matriculaEdit.getText().toString();
                contrasena = contrasenaEdit.getText().toString();

                if ((matricula != null) && (!matricula.isEmpty())) {
                    if ((contrasena != null) && (!contrasena.isEmpty())) {
                        SQLiteOpenHelper usuariosDatabaseHelper = new UsuariosDatabaseHelper(LogInActivity.this);
                        try {
                            db = usuariosDatabaseHelper.getReadableDatabase();
                            Cursor fila=db.rawQuery("select MATRICULA,CONTRASENA from USUARIOS where MATRICULA = '" + matricula + "' and CONTRASENA = '" + contrasena +"'",null);
                            if(fila.moveToFirst()){
                                String usua=fila.getString(0);
                                String pass=fila.getString(1);
                                if (matricula.equals(usua)&&contrasena.equals(pass)){
                                    Log.d("LoginActivity", "This is a debug message");
                                    Intent intent = new Intent(LogInActivity.this,  MainActivity.class);
                                    intent.putExtra("matricula", matricula);
                                    startActivity(intent);
                                }
                            }
                            else {
                                Log.d("LoginActivity", "Incorrecto");
                                Toast.makeText(LogInActivity.this, "Matricula o contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                            }
                            fila.close();
                            db.close();
                        } catch (Exception e) {
                            Toast.makeText(LogInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast toast = Toast.makeText(LogInActivity.this, "Por favor ingrese su contraseña", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }else {
                    Toast toast = Toast.makeText(LogInActivity.this, "Por favor ingrese su matricula", Toast.LENGTH_SHORT);
                    toast.show();
                }
                Log.d("LoginActivity", "Button pressed");
            }
        });
    }
}
