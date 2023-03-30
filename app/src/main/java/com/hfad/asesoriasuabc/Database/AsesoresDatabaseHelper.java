package com.hfad.asesoriasuabc.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AsesoresDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_TITLE = "ASESORES";
    private static final int DB_VERSION = 1;

    public AsesoresDatabaseHelper(Context context){
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
                                    String nombre,
                                    String apellido,
                                    String correo,
                                    String matricula,
                                    String contrasena,
                                    String lunes,
                                    String martes,
                                    String miercoles,
                                    String jueves,
                                    String viernes){
        ContentValues asesorValues = new ContentValues();
        asesorValues.put("NOMBRE", nombre);
        asesorValues.put("APELLIDO", apellido);
        asesorValues.put("CORREO", correo);
        asesorValues.put("MATRICULA", matricula);
        asesorValues.put("CONTRASENA", contrasena);
        asesorValues.put("LUNES", lunes);
        asesorValues.put("MARTES", martes);
        asesorValues.put("MIERCOLES", miercoles);
        asesorValues.put("JUEVES", jueves);
        asesorValues.put("VIERNES", viernes);
        db.insert("ASESORES", null, asesorValues);
    }

    private void updateMyDataBase(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < 1){
            db.execSQL("CREATE TABLE ASESORES ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NOMBRE TEXT, "
                    + "APELLIDO TEXT,"
                    + "CORREO TEXT,"
                    + "MATRICULA TEXT,"
                    + "CONTRASENA TEXT,"
                    + "LUNES TEXT,"
                    + "MARTES TEXT,"
                    + "MIERCOLES TEXT,"
                    + "JUEVES TEXT,"
                    + "VIERNES TEXT);");
            insertDatos(db, "Tamara", "Rico", "tamara.rico@uabc.edu.mx", "1270673", "1234", "14:00 - 18:00", "13:00 - 17:00", "13:00 - 17:00", "13:00 - 17:00", "13:00 - 17:00");
        }
    }
}
