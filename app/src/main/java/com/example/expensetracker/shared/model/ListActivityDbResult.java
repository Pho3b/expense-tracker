package com.example.expensetracker.shared.model;

import com.example.expensetracker.db.model.Transaction;

import java.util.ArrayList;

public class ListActivityDbResult {
    public ArrayList<Transaction> transactions;
    public Integer transactionsAmountSum;


    public ListActivityDbResult(ArrayList<Transaction> transactions, Integer transactionsAmountSum) {
        this.transactions = transactions;
        this.transactionsAmountSum = transactionsAmountSum;
    }
}
