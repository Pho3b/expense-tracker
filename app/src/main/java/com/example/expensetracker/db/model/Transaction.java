package com.example.expensetracker.db.model;

import com.example.expensetracker.shared.enums.TransactionType;

import java.time.LocalDate;

public class Transaction {
    public double amount;
    public String comment;
    public int category_id;
    public LocalDate date;
    public TransactionType type;

    public Transaction(
            double amount,
            String comment,
            int category_id,
            LocalDate date,
            TransactionType type
    ) {
        this.amount = amount;
        this.comment = comment;
        this.date = date;
        this.category_id = category_id;
        this.type = type;
    }

    public Transaction(
            double amount,
            String comment,
            int category_id,
            LocalDate date
    ) {
        this(amount, comment, category_id, date, TransactionType.Expense);
    }
}
