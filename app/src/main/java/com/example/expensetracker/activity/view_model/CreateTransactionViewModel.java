package com.example.expensetracker.activity.view_model;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.expensetracker.shared.enums.TransactionType;
import com.example.expensetracker.shared.service.GlobalService;

public class CreateTransactionViewModel extends ViewModel {
    public MutableLiveData<String> amount = new MutableLiveData<>("0");
    public MutableLiveData<Drawable> incomeBackground = new MutableLiveData<>(null);
    public MutableLiveData<Drawable> expenseBackground = new MutableLiveData<>(null);
    private final Application application;

    /**
     * Constructor
     *
     * @param application the current application context instance.
     */
    public CreateTransactionViewModel(Application application) {
        this.application = application;
        GlobalService.updateSelectedTransactionType(application, expenseBackground, incomeBackground);
    }

    /**
     * Handles the onClick event for the view button with ID 'expense_type_btn'.
     *
     * @param view current View instance.
     */
    public void expenseTypeBtnOnClick(View view) {
        GlobalService.selectedTransactionType = TransactionType.Expense;
        GlobalService.updateSelectedTransactionType(application, expenseBackground, incomeBackground);
    }

    /**
     * Handles the onClick event for the view button with ID 'income_type_btn'.
     *
     * @param view current View instance.
     */
    public void incomeTypeBtnOnClick(View view) {
        GlobalService.selectedTransactionType = TransactionType.Income;
        GlobalService.updateSelectedTransactionType(application, expenseBackground, incomeBackground);
    }

}
