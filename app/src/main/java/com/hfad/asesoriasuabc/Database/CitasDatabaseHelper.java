package com.hfad.asesoriasuabc.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hfad.asesoriasuabc.MateriasFragment;

/*
 * ESTADOS CITAS:
 *       EN ESPERA          (Nadie califica)                            (Los dos pueden cancelar)
 *       CONFIRMADA         (Nadie califica)                            (Los dos pueden cancelar)
 *       FINALIZADA         (Los dos califican)                         (Nadie puede cancelar)
 *       RECHAZADA          (Nadie califica)                            (Nadie puede cancelar)
 *       CANCELADA          (Nadie califica)                            (Nadie puede cancelar)
 *       ASESOR N/A         (Asesorado puede calificar, Asesor NO)      (Nadie puede cancelar)
 *       ASESORADO N/A      (Asesor puede calificar, Asesorado NO)      (Nadie puede cancelar)
 * */
public class CitasDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_TITLE = "CITAS";
    private static final int DB_VERSION = 1;

    public CitasDatabaseHelper(Context context){
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
                                    String matriculaAsesor,
                                    String matriculaAsesorado,
                                    String materia,
                                    int dia,
                                    int mes,
                                    int year,
                                    String horaInicio,
                                    String horaFin,
                                    String estado){
        ContentValues citasValues = new ContentValues();
        citasValues.put("MATRICULA_ASESOR", matriculaAsesor);
        citasValues.put("MATRICULA_ASESORADO", matriculaAsesorado);
        citasValues.put("MATERIA", materia);
        citasValues.put("DAY", dia);
        citasValues.put("MONTH", mes);
        citasValues.put("YEAR", year);
        citasValues.put("HORA_INICIO", horaInicio);
        citasValues.put("HORA_FIN", horaFin);
        citasValues.put("ESTADO", estado);
        db.insert(DB_TITLE, null, citasValues);
    }

    private void updateMyDataBase(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < 1){
            db.execSQL("CREATE TABLE CITAS ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "MATRICULA_ASESOR TEXT,"
                    + "MATRICULA_ASESORADO TEXT,"
                    + "MATERIA TEXT,"
                    + "DAY INTEGER,"
                    + "MONTH INTEGER,"
                    + "YEAR INTEGER,"
                    + "HORA_INICIO TEXT,"
                    + "HORA_FIN TEXT,"
                    + "ESTADO TEXT);");
        }
    }
}