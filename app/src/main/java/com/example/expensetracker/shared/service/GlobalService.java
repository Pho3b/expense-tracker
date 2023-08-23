package com.example.expensetracker.shared.service;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.expensetracker.R;
import com.example.expensetracker.shared.enums.TransactionType;

public class GlobalService {
    public static TransactionType selectedTransactionType = TransactionType.Expense;


    /**
     * Visually updates the transaction type buttons on top of the view.
     * They are updated based on the 'selectedTransactionType' current value.
     */
    public static void updateSelectedTransactionType(
            Application application,
            MutableLiveData<Drawable> expenseBg,
            MutableLiveData<Drawable> incomeBg
    ) {
        switch (selectedTransactionType) {
            case Expense:
                expenseBg.setValue(ContextCompat.getDrawable(application, R.drawable.rounded_background));
                incomeBg.setValue(null);
                break;
            case Income:
                incomeBg.setValue(ContextCompat.getDrawable(application, R.drawable.rounded_background));
                expenseBg.setValue(null);
                break;
            default:
                break;
        }
    }
}
