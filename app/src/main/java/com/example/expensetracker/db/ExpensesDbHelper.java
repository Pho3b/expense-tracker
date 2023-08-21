package com.example.expensetracker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.expensetracker.ui.expenses.Expense;

import java.time.LocalDate;

public class ExpensesDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "expenses.db";
    private SQLiteDatabase dbWrite;
    private SQLiteDatabase dbRead;

    public ExpensesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_EXPENSES_TABLE = "CREATE TABLE " + ExpenseContract.ExpenseEntry.TABLE_NAME + " (" +
                ExpenseContract.ExpenseEntry._ID + " INTEGER PRIMARY KEY," +
                ExpenseContract.ExpenseEntry.COLUMN_NAME_NAME + " TEXT," +
                ExpenseContract.ExpenseEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                ExpenseContract.ExpenseEntry.COLUMN_NAME_AMOUNT + " REAL," +
                ExpenseContract.ExpenseEntry.COLUMN_NAME_DATE + " TEXT)";

        db.execSQL(SQL_CREATE_EXPENSES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean insertNewExpense(SQLiteDatabase db, Expense expense) {
        checkWriteDbInstance();

        ContentValues values = new ContentValues();
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_NAME, expense.getName());
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_AMOUNT, expense.getAmount());
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_DATE, String.valueOf(LocalDate.now()));

        return db.insert(ExpenseContract.ExpenseEntry.TABLE_NAME, null, values) != -1;
    }

    public boolean readExpense() {
        return true;
    }

    private void checkWriteDbInstance() {
        if (dbWrite == null) {
            dbWrite = this.getWritableDatabase();
        }
    }

    private void checkReadDbInstance() {
        if (dbRead == null) {
            dbRead = this.getWritableDatabase();
        }
    }

    //        ExpensesDbHelper dbHelper = new ExpensesDbHelper(this);
    //        SQLiteDatabase db = dbHelper.getWritableDatabase();
    //            boolean res = dbHelper.insertNewExpense(db, new Expense("Cinema Test", 11));
    //            String resMsg = res ? "Insertion Success" : "Insertion Error";
    //
    //            Toast.makeText(
    //                    ListExpenseActivity.this,
    //                    resMsg,
    //                    Toast.LENGTH_SHORT
    //            ).show();
}