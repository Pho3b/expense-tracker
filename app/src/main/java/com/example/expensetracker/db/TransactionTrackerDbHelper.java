package com.example.expensetracker.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.expensetracker.model.Transaction;
import com.example.expensetracker.enumerator.TransactionType;

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

    @SuppressLint("Range")
    public ArrayList<Transaction> retrieveTransactions(
            TransactionType type,
            LocalDate startDate,
            LocalDate endDate
    ) {
        checkReadDbInstance();

        ArrayList<Transaction> res = new ArrayList<>();
        String query = String.format(
                "SELECT * FROM '%s' WHERE date BETWEEN ? AND ? ORDER BY date DESC",
                formatTableName(type)
        );
        Cursor cursor = dbRead.rawQuery(query, new String[]{startDate.toString(), endDate.toString()});

        while (cursor.moveToNext()) {
            String comment = cursor.getString(cursor.getColumnIndex(TransactionEntry.COLUMN_NAME_COMMENT));
            double amount = cursor.getDouble(cursor.getColumnIndex(TransactionEntry.COLUMN_NAME_AMOUNT));
            int categoryId = cursor.getInt(cursor.getColumnIndex(TransactionEntry.COLUMN_NAME_CATEGORY_ID));
            LocalDate date = LocalDate.parse(
                    cursor.getString(cursor.getColumnIndex(TransactionEntry.COLUMN_NAME_DATE)),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")
            );

            res.add(new Transaction(amount, comment, categoryId, date, type));
        }

        cursor.close();
        return res;
    }

    @SuppressLint("Range")
    public Integer retrieveTransactionsAmountSum(
            TransactionType type,
            LocalDate startDate,
            LocalDate endDate
    ) {
        checkReadDbInstance();

        int res = 0;
        String query = String.format(
                "SELECT SUM(amount) AS sum FROM '%s' WHERE date BETWEEN ? AND ?",
                formatTableName(type)
        );
        Cursor cursor = dbRead.rawQuery(query, new String[]{startDate.toString(), endDate.toString()});

        while (cursor.moveToNext()) {
            res = cursor.getInt(cursor.getColumnIndex("sum"));
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
