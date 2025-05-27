package com.example.expensetracker.service;

import static com.example.expensetracker.model.Constants.ET_LOGS_TAG_DEV;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.expensetracker.db.TransactionTrackerDbHelper;
import com.example.expensetracker.enumerator.TransactionType;
import com.example.expensetracker.model.Transaction;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

public class CSVImportService {
    final String[] CSV_HEADER = {"DATA", "TYPE", "CATEGORY", "DESCRIPTION", "AMOUNT"};

    private final TransactionTrackerDbHelper dbHelper;
    private final Context context;


    public CSVImportService(Context context) {
        this.dbHelper = new TransactionTrackerDbHelper(context);
        this.context = context;
    }

    public boolean validateCsvHeader(String headerRow) {
        String[] columns = headerRow.split(",");

        if (columns.length != 5) {
            Toast.makeText(
                    this.context,
                    String.format(Locale.ITALY, "CSV header columns length must be 5, found %d columns instead", columns.length),
                    Toast.LENGTH_SHORT
            ).show();

            return false;
        }


        for (int i = 0; i < CSV_HEADER.length; i++) {
            if (!Objects.equals(columns[i].toUpperCase().trim(), CSV_HEADER[i])) {
                Log.e(ET_LOGS_TAG_DEV, String.format("This value is not equal %s:%s", columns[i].toUpperCase(), CSV_HEADER[i]));

                Toast.makeText(
                        this.context,
                        String.format(Locale.ITALY, "Incorrect Header columns naming, correct one is: %s", Arrays.toString(CSV_HEADER)),
                        Toast.LENGTH_SHORT
                ).show();

                return false;
            }
        }

        return true;
    }

    public void importCSV(InputStream inputStream) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        int lineNumber = 0;
        String line;


        try {
            db.beginTransaction();

            while ((line = reader.readLine()) != null) {
                if (lineNumber == 0) {
                    if (!validateCsvHeader(line)) {
                        return;
                    }

                    lineNumber++;
                    continue;
                }

                String[] columns = line.split(","); // Assuming CSV is comma-separated

                if (columns.length == CSV_HEADER.length) {
                    TransactionType type = columns[1].equals("Spese") ? TransactionType.Expense : TransactionType.Income;
                    int categoryId = 0;

                    try {
                        Integer.parseInt(columns[2]);
                    } catch (Exception ignored) {
                    }

                    Transaction toInsert = new Transaction(
                            Double.parseDouble(columns[4]),
                            columns[3],
                            categoryId,
                            LocalDate.parse(columns[0], DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            type,
                            false
                    );

                    dbHelper.insertNewTransaction(toInsert);
                } else {
                    Log.e(ET_LOGS_TAG_DEV, "Incorrect number of columns in line " + lineNumber);
                }

                lineNumber++;
            }

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(ET_LOGS_TAG_DEV, "Error reading CSV", e);
        } finally {
            db.endTransaction();

            try {
                reader.close();
                Log.i(ET_LOGS_TAG_DEV, "Reader correctly closed");
            } catch (Exception e) {
                Log.e(ET_LOGS_TAG_DEV, "Error closing reader", e);
            }
        }
    }
}
