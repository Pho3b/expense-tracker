package com.example.expensetracker.activity.view_model;

import android.graphics.drawable.Drawable;

import androidx.lifecycle.ViewModel;

public class CreateTransactionViewModel extends ViewModel {
    public String amount = "0";
    public String transactionAmountHint;

    public Drawable incomeBackground = null;
    public Drawable expenseBackground = null;
}
