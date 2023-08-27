package com.example.expensetracker.db.model;

import java.time.LocalDate;

public class Income extends AbstractTransaction {

    public Income(double amount, String comment, int category_id, LocalDate date) {
        super(amount, comment, category_id, date);
    }
}
