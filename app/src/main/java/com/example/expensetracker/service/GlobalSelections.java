package com.example.expensetracker.service;

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.expensetracker.R;
import com.example.expensetracker.enumerator.TimeSpanSelection;
import com.example.expensetracker.enumerator.TransactionType;

import java.time.LocalDate;

public class GlobalSelections extends Service {
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
