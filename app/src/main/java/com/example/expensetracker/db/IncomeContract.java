package com.example.expensetracker.db;

public class IncomeContract {
    private IncomeContract() {
    }

    public static class IncomeEntry extends TransactionEntry {
        public static final String TABLE_NAME = "incomes";
    }
}
