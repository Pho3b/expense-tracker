package com.example.expensetracker.service;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.expensetracker.R;
import com.example.expensetracker.enums.TimeSpanSelection;
import com.example.expensetracker.enums.TransactionType;

import java.time.LocalDate;

public class GlobalSelections {
    public static TransactionType selectedTransactionType = TransactionType.Expense;
    public static MutableLiveData<LocalDate> selectedDate = new MutableLiveData<>(LocalDate.now());
    public static TimeSpanSelection selectedTimeSpan = TimeSpanSelection.Month;


    /**
     * Visually updates the transaction type buttons on top of the 'ListTransactionView'.
     * They are updated based on the 'selectedTransactionType' current value.
     */
    public static void updateSelectedTransactionType(
            Application application,
            MutableLiveData<Drawable> expenseBg,
            MutableLiveData<Drawable> incomeBg
    ) {
        switch (selectedTransactionType) {
            case Expense:
                expenseBg.setValue(ContextCompat.getDrawable(application, R.drawable.rounded_grey_background));
                incomeBg.setValue(null);
                break;
            case Income:
                incomeBg.setValue(ContextCompat.getDrawable(application, R.drawable.rounded_grey_background));
                expenseBg.setValue(null);
                break;
            default:
                break;
        }
    }
}
