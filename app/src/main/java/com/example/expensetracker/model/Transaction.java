package com.example.expensetracker.model;

import com.example.expensetracker.enumerator.TransactionType;

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
            int categoryId,
            LocalDate date,
            TransactionType type
    ) {
        this.amount = amount;
        this.comment = comment;
        this.date = date;
        this.category_id = categoryId;
        this.type = type;
    }

    public Transaction(
            double amount,
            String comment,
            int categoryId,
            LocalDate date
    ) {
        this(amount, comment, categoryId, date, TransactionType.Expense);
    }
}
