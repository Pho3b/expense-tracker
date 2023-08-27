package com.example.expensetracker.db;

import android.provider.BaseColumns;

public class IncomeContract {
    private IncomeContract() {
    }

    public static class IncomeEntry implements BaseColumns {
        public static final String TABLE_NAME = "incomes";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_CATEGORY_ID = "category_id";
        public static final String COLUMN_NAME_COMMENT = "comment";
        public static final String COLUMN_NAME_DATE = "date";
    }
}
