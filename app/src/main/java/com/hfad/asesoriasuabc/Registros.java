package com.hfad.asesoriasuabc;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Registros extends AppCompatActivity {

    Button asesorado;
    Button asesor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);
        asesorado = (Button) findViewById(R.id.asesorado_button);
        asesor = (Button) findViewById(R.id.asesor_button);
    }
    @Override
    protected void onStart() {
        super.onStart();
        asesorado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registros.this, Formulario1.class);
                startActivity(intent);
            }
        });
        asesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itemintent = new Intent(Registros.this, Formulario2.class);
                Registros.this.startActivity(itemintent);
            }
        });
    }

}