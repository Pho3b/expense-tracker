package com.example.expensetracker.service;

import static com.example.expensetracker.model.Constants.ET_LOGS_TAG_DEV;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.expensetracker.db.TransactionTrackerDbHelper;
import com.example.expensetracker.enumerator.TransactionType;
import com.example.expensetracker.model.Transaction;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CSVImportService {
    private final TransactionTrackerDbHelper dbHelper;

    public CSVImportService(Context context) {
        dbHelper = new TransactionTrackerDbHelper(context);
    }

    public void importCSV(InputStream inputStream) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        try {
            db.beginTransaction();

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(","); // Assuming CSV is comma-separated

                Log.i(ET_LOGS_TAG_DEV, "Line: " + line);
                Log.i(ET_LOGS_TAG_DEV, "Column length: " + columns.length);
                if (columns.length == 5) { // Adjust based on your table structure
                    Log.i(ET_LOGS_TAG_DEV, "Importing values");

                    Transaction toInsert = new Transaction(
                            Double.parseDouble(columns[4]),
                            columns[3],
                            1,
                            LocalDate.parse(columns[0], DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            TransactionType.Expense,
                            false
                    );

                    dbHelper.insertNewTransaction(toInsert);
                }
            }

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(ET_LOGS_TAG_DEV, "Error reading CSV", e);
        } finally {
            db.endTransaction();

            try {
                reader.close();
            } catch (Exception e) {
                Log.e(ET_LOGS_TAG_DEV, "Error closing reader", e);
            }
        }
    }
}
