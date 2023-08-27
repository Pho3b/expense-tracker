package com.example.expensetracker.db.model;

import java.time.LocalDate;

public class AbstractTransaction {
    public double amount;
    public String comment;
    public int category_id;
    public LocalDate date;

    public AbstractTransaction(double amount, String comment, int category_id, LocalDate date) {
        this.amount = amount;
        this.comment = comment;
        this.date = date;
        this.category_id = category_id;
    }
}
