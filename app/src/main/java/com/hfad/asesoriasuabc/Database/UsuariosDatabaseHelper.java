package com.hfad.asesoriasuabc.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UsuariosDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_TITLE = "USUARIOS";
    private static final int DB_VERSION = 1;

    public UsuariosDatabaseHelper(Context context){
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
                                    boolean asesor,
                                    boolean registrado){
        ContentValues userValues = new ContentValues();
        userValues.put("NOMBRE", nombre);
        userValues.put("APELLIDO", apellido);
        userValues.put("CORREO", correo);
        userValues.put("MATRICULA", matricula);
        userValues.put("CONTRASENA", contrasena);
        userValues.put("ASESOR", asesor);
        userValues.put("REGISTRADO", registrado);
        db.insert("Usuarios", null, userValues);
    }

    private void updateMyDataBase(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < 1){
            db.execSQL("CREATE TABLE USUARIOS ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NOMBRE TEXT, "
                    + "APELLIDO TEXT,"
                    + "CORREO TEXT,"
                    + "MATRICULA TEXT,"
                    + "CONTRASENA TEXT,"
                    + "ASESOR BOOLEAN,"
                    + "REGISTRADO BOOLEAN);");
            insertDatos(db, "","","","", "",false, false);
            insertDatos(db, "Tamara", "Rico", "tamara.rico@uabc.edu.mx", "1270673", "1234", true, true);
        }
    }
}
