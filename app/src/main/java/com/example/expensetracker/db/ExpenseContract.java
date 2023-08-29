package com.example.expensetracker.db;

public class ExpenseContract {
    private ExpenseContract() {
    }

    public static class ExpenseEntry extends TransactionEntry {
        public static final String TABLE_NAME = "expenses";
    }
}
