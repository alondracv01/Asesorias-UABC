package com.hfad.asesoriasuabc.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * ESTADOS CITAS:
 *       BORRADA
 *       ENVIADA
 *       CONFIRMADA
 *       FINALIZADA
 *       RECHAZADA
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
                                    String nombreAsesor,
                                    String apellidoAsesor,
                                    String nombreAsesorado,
                                    String apellidoAsesorado,
                                    String matriculaAsesor,
                                    String matriculaAsesorado,
                                    int dia,
                                    int mes,
                                    int year,
                                    String horaInicio,
                                    String horaFin,
                                    String estado){
        ContentValues citasValues = new ContentValues();
        citasValues.put("NOMBRE_ASESOR", nombreAsesor);
        citasValues.put("APELLIDO_ASESOR", apellidoAsesor);
        citasValues.put("NOMBRE_ASESORADO", nombreAsesorado);
        citasValues.put("APELLIDO_ASESORADO", apellidoAsesorado);
        citasValues.put("MATRICULA_ASESOR", matriculaAsesor);
        citasValues.put("MATRICULA_ASESORADO", matriculaAsesorado);
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
            db.execSQL("CREATE TABLE CITA ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NOMBRE_ASESOR TEXT, "
                    + "APELLIDO_ASESOR TEXT,"
                    + "NOMBRE_ASESORADO TEXT,"
                    + "APELLIDO_ASESORADO TEXT,"
                    + "MATRICULA_ASESOR TEXT,"
                    + "MATRICULA_ASESORADO TEXT,"
                    + "DAY INTEGER,"
                    + "MONTH INTEGER,"
                    + "YEAR INTEGER,"
                    + "HORA_INICIO TEXT,"
                    + "HORA_FIN TEXT,"
                    + "ESTADO TEXT);");
            //insertDatos(db, "Tamara", "Rico", "tamara.rico@uabc.edu.mx", "1270673", "1234", "14:00-18:00", "13:00-17:00", "13:00-17:00", "13:00-17:00", "13:00-17:00");
        }
    }
}