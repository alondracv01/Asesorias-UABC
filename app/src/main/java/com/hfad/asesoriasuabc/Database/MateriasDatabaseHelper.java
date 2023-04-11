package com.hfad.asesoriasuabc.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MateriasDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_TITLE = "Materias";
    private static final int DB_VERSION = 1;

    public MateriasDatabaseHelper(Context context){
        super(context, DB_TITLE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        updateMyDataBase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        updateMyDataBase(db, oldVersion, newVersion);
    }

    private static void insertDatos(SQLiteDatabase db,
                                    String matricula,
                                    String nombre,
                                    boolean cursando,
                                    int calificacion,
                                    boolean asesor){
        ContentValues datosValues = new ContentValues();
        datosValues.put("MATRICULA", matricula);
        datosValues.put("NOMBRE", nombre);
        datosValues.put("CURSANDO", cursando);
        datosValues.put("CALIFICACION", calificacion);
        datosValues.put("ASESOR", asesor);
        db.insert("MATERIAS", null, datosValues);
    }

    private void updateMyDataBase(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < 1){
            db.execSQL("CREATE TABLE MATERIAS ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "MATRICULA STRING,"
                    + "NOMBRE STRING,"
                    + "CURSANDO BOOLEAN,"
                    + "CALIFICACION INTEGER,"
                    + "ASESOR BOOLEAN);");
            insertDatos(db, "1270851","Metodologia de la programacion",true, -1, false);
            insertDatos(db, "1270851","Introduccion a la ingenieria",true, -1, false);
            insertDatos(db, "1270851","Algebra superior",true, -1, false);
            insertDatos(db, "1270851","Comunicacion oral y escrita",true, -1, false);
            insertDatos(db, "1270851","Ingles 1",true, -1, false);
            insertDatos(db, "1270851","Calculo diferencial",true, -1, false);
            insertDatos(db, "1270673","Metodologia de la programacion",true, 80, true);
            insertDatos(db, "1270673","Programacion y metodos numericos",true, 95, true);
            insertDatos(db, "1270673","Algebra superior",true, 90, true);
            insertDatos(db, "1270673","Programacion estructurada",true, 93, false);
            insertDatos(db, "1270673","Introduccion a la ingenieria",true, 99, false);
            insertDatos(db, "1270673","Comunicacion oral y escrita",true, 100, false);
            insertDatos(db, "1270673","Ingles 1",true, 98, false);
            insertDatos(db, "1270673","Calculo diferencial",true, 88, false);
        }
    }
}
