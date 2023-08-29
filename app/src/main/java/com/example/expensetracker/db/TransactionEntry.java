package com.example.expensetracker.db;

import android.provider.BaseColumns;

public class TransactionEntry implements BaseColumns {
    public static final String COLUMN_NAME_AMOUNT = "amount";
    public static final String COLUMN_NAME_CATEGORY_ID = "category_id";
    public static final String COLUMN_NAME_COMMENT = "comment";
    public static final String COLUMN_NAME_DATE = "date";
}
