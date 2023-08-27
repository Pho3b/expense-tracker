package com.example.expensetracker.db.model;

import java.time.LocalDate;

public class Expense extends AbstractTransaction {

    public Expense(double amount, String comment, int category_id, LocalDate date) {
        super(amount, comment, category_id, date);
    }
}
