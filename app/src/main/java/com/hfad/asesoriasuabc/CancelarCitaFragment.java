package com.hfad.asesoriasuabc;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.hfad.asesoriasuabc.Database.CitasDatabaseHelper;

public class CancelarCitaFragment extends DialogFragment {
    private SQLiteDatabase db;
    SQLiteOpenHelper citasDatabaseHelper;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle mArgs = getArguments();
        int mid = mArgs.getInt("ID");
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.cancelada)
                .setPositiveButton("Si, cancelar",  new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        citasDatabaseHelper = new CitasDatabaseHelper(getContext());
                        try{
                            db = citasDatabaseHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put("ESTADO", "CANCELADA");

                            String[] args = new String[]{Integer.toString(mid)};
                            db.update("CITAS", values, "_id=?", args);
                            db.close();
                        } catch (SQLiteException e){
                            Toast toast = Toast.makeText(getContext(), "Database unavaible: onResume Citas", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getDialog().dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
