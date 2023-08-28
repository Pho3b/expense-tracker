package com.example.expensetracker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.expensetracker.db.model.Transaction;
import com.example.expensetracker.shared.enums.TransactionType;

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
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_AMOUNT, transaction.amount);
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_COMMENT, transaction.comment);
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_CATEGORY_ID, transaction.category_id);
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_DATE, String.valueOf(transaction.date));

        return dbWrite.insert(
                transaction.type.toString().toLowerCase() + "s",
                null,
                values
        ) != -1;
    }

    public boolean readExpense() {
        return true;
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
}
