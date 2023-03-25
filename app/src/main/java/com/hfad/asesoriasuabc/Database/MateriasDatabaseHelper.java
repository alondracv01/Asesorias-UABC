package com.hfad.asesoriasuabc.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MateriasDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_TITLE = "Materias";
    private static final int DB_VERSION = 1;

    MateriasDatabaseHelper(Context context){
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
                                    boolean cursando){
        ContentValues datosValues = new ContentValues();
        datosValues.put("CURRENT_INDEX", index);
        datosValues.put("MATRICULA", matricula);
        datosValues.put("NOMBRE", nombre);
        datosValues.put("CURSANDO", cursando);
        db.insert("Materias", null, datosValues);
    }

    private void updateMyDataBase(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < 1){
            db.execSQL("CREATE TABLE QUIZ ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "CURRENT_INDEX INTEGER, "
                    + "MATRICULA STRING,"
                    + "NOMBRE STRING,"
                    + "CURSANDO BOOLEAN);");
            insertDatos(db, 1,"","",true);
        }
    }
}
