package com.example.expensetracker.ui.expenses;

public class Expense {
    private String name;
    private double amount;

    public Expense(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
