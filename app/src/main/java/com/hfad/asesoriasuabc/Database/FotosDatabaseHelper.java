package com.hfad.asesoriasuabc.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FotosDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_TITLE = "fotosDB"; //the title of our database
    private static final int DB_VERSION = 1; //the version of the database

    public FotosDatabaseHelper(Context context){
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

    private static void insertImg(SQLiteDatabase db,
                                  byte[] imgPerfil,
                                  boolean clickedPerfil){
        ContentValues imagenesValues = new ContentValues();
        imagenesValues.put("IMG_PERFIL", imgPerfil);
        imagenesValues.put("CLICKED_PERFIL", clickedPerfil);
        db.insert("IMAGENES", null, imagenesValues);
    }

    private void updateMyDataBase(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < 1){
            db.execSQL("CREATE TABLE IMAGENES ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "IMG_PERFIL BLOB, "
                    + "CLICKED_PERFIL BOOLEAN);");
            insertImg(db, null,false);
        }
    }
}
