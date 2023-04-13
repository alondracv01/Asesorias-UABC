package com.hfad.asesoriasuabc.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AltaAsesoresDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_TITLE = "SolicitudAsesor";
    private static final int DB_VERSION = 1;

    AltaAsesoresDatabaseHelper(Context context){
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
                                    int index,
                                    String matricula,
                                    String nombre,
                                    int promedioGeneral,
                                    String materias){
        ContentValues datosValues = new ContentValues();
        datosValues.put("CURRENT_INDEX", index);
        datosValues.put("MATRICULA", matricula);
        datosValues.put("NOMBRE", nombre);
        datosValues.put("PROMEDIO", promedioGeneral);
        datosValues.put("MATERIAS", materias);
        db.insert("SolicitudAsesor", null, datosValues);
    }

    private void updateMyDataBase(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < 1){
            db.execSQL("CREATE TABLE SOLICITUDES ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "CURRENT_INDEX INTEGER, "
                    + "MATRICULA STRING,"
                    + "NOMBRE STRING,"
                    + "PROMEDIO INTEGER,"
                    + "MATERIAS STRING);");
            insertDatos(db, 1,"01272986","Herrera Angela","86.2", "Metodologia de la programacion, calculo integral, probabilidad y estadistica");
        }
    }
}
