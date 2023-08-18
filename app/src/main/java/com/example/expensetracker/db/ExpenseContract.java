package com.example.expensetracker.db;

import android.provider.BaseColumns;

public class ExpenseContract {
    private ExpenseContract() {}

    public static class ExpenseEntry implements BaseColumns {
        public static final String TABLE_NAME = "expenses";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_DATE = "date";
    }
}
