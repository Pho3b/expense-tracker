package com.example.expensetracker.model;

import com.example.expensetracker.enumerator.TransactionType;

import java.time.LocalDate;

public class Transaction {
    public int id = -1;
    public double amount;
    public String comment;
    public int category_id;
    public LocalDate date;
    public TransactionType type;
    public boolean isHeader;

    public Transaction(
            double amount,
            String comment,
            int categoryId,
            LocalDate date,
            TransactionType type,
            boolean isHeader
    ) {
        this.amount = amount;
        this.comment = comment;
        this.date = date;
        this.category_id = categoryId;
        this.type = type;
        this.isHeader = isHeader;
    }

    public Transaction(
            int id,
            double amount,
            String comment,
            int categoryId,
            LocalDate date,
            TransactionType type
    ) {
        this(amount, comment, categoryId, date, type, false);
        this.id = id;
    }
}
