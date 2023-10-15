package com.example.expensetracker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.expensetracker.db.model.Transaction;
import com.example.expensetracker.shared.enums.TransactionType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TransactionTrackerDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "transaction_tracker.db";
    private SQLiteDatabase dbWrite;
    private SQLiteDatabase dbRead;

    public TransactionTrackerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_EXPENSES_TABLE = "CREATE TABLE " + ExpenseContract.ExpenseEntry.TABLE_NAME + " (" +
                ExpenseContract.ExpenseEntry._ID + " INTEGER PRIMARY KEY," +
                ExpenseContract.ExpenseEntry.COLUMN_NAME_AMOUNT + " REAL," +
                ExpenseContract.ExpenseEntry.COLUMN_NAME_CATEGORY_ID + " INTEGER," +
                ExpenseContract.ExpenseEntry.COLUMN_NAME_COMMENT + " TEXT," +
                ExpenseContract.ExpenseEntry.COLUMN_NAME_DATE + " TEXT)";

        String SQL_CREATE_INCOMES_TABLE = "CREATE TABLE " + IncomeContract.IncomeEntry.TABLE_NAME + " (" +
                IncomeContract.IncomeEntry._ID + " INTEGER PRIMARY KEY," +
                IncomeContract.IncomeEntry.COLUMN_NAME_AMOUNT + " REAL," +
                IncomeContract.IncomeEntry.COLUMN_NAME_CATEGORY_ID + " INTEGER," +
                IncomeContract.IncomeEntry.COLUMN_NAME_COMMENT + " TEXT," +
                IncomeContract.IncomeEntry.COLUMN_NAME_DATE + " TEXT)";

        db.execSQL(SQL_CREATE_EXPENSES_TABLE);
        db.execSQL(SQL_CREATE_INCOMES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ExpenseContract.ExpenseEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + IncomeContract.IncomeEntry.TABLE_NAME);
        onCreate(db);
    }

    public boolean insertNewTransaction(Transaction transaction) {
        initWriteDbInstanceIfNeeded();

        ContentValues values = new ContentValues();
        values.put(TransactionEntry.COLUMN_NAME_AMOUNT, transaction.amount);
        values.put(TransactionEntry.COLUMN_NAME_COMMENT, transaction.comment);
        values.put(TransactionEntry.COLUMN_NAME_CATEGORY_ID, transaction.category_id);
        values.put(TransactionEntry.COLUMN_NAME_DATE, String.valueOf(transaction.date));

        return dbWrite.insert(
                formatTableName(transaction.type),
                null,
                values
        ) != -1;
    }

    public ArrayList<Transaction> queryReadTransactions(
            TransactionType type,
            LocalDate startDate,
            LocalDate endDate
    ) {
        checkReadDbInstance();

        ArrayList<Transaction> res = new ArrayList<>();
        Cursor cursor = dbRead.query(
                formatTableName(type),
                null,
                "date BETWEEN ? AND ?",
                new String[]{startDate.toString(), endDate.toString()},
                null,
                null,
                "date DESC"
        );

        int idColumnIndex = cursor.getColumnIndex(TransactionEntry._ID);
        int commentColumnIndex = cursor.getColumnIndex(TransactionEntry.COLUMN_NAME_COMMENT);
        int amountColumnIndex = cursor.getColumnIndex(TransactionEntry.COLUMN_NAME_AMOUNT);
        int categoryIdColumnIndex = cursor.getColumnIndex(TransactionEntry.COLUMN_NAME_CATEGORY_ID);
        int dateColumnIndex = cursor.getColumnIndex(TransactionEntry.COLUMN_NAME_DATE);

        while (cursor.moveToNext()) {
            if (idColumnIndex >= 0 && commentColumnIndex >= 0 && amountColumnIndex >= 0 &&
                    categoryIdColumnIndex >= 0 && dateColumnIndex >= 0
            ) {
                String comment = cursor.getString(commentColumnIndex);
                double amount = cursor.getDouble(amountColumnIndex);
                int categoryId = cursor.getInt(amountColumnIndex);
                LocalDate date = LocalDate.parse(
                        cursor.getString(dateColumnIndex),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")
                );

                res.add(new Transaction(amount, comment, categoryId, date, type));
            }
        }

        cursor.close();
        return res;
    }

    private void initWriteDbInstanceIfNeeded() {
        if (dbWrite == null) {
            dbWrite = this.getWritableDatabase();
        }
    }

    private void checkReadDbInstance() {
        if (dbRead == null) {
            dbRead = this.getWritableDatabase();
        }
    }

    /**
     * Given a TransactionType returns its Database table name correctly formatted.
     *
     * @param type The transaction type for which to retrieve the Table name.
     * @return The correctly formatted DB table name
     */
    private String formatTableName(TransactionType type) {
        return type.toString().toLowerCase() + "s";
    }
}
