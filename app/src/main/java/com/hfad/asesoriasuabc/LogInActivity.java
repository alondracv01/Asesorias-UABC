package com.hfad.asesoriasuabc;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {

    Button ingresar;
    EditText usuarioEdit;
    EditText contrasenaEdit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuarioEdit = (EditText) findViewById(R.id.usuario_editText);
        contrasenaEdit = (EditText) findViewById(R.id.contrasena_editText);
        ingresar = (Button) findViewById(R.id.ingresar_button);
    }
}
