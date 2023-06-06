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

public class EvaluarAsesorFragment extends DialogFragment {
    private SQLiteDatabase db;
    SQLiteOpenHelper citasDatabaseHelper;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle mArgs = getArguments();
        int mid = mArgs.getInt("ID");
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.evaluacionRealizada);
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
