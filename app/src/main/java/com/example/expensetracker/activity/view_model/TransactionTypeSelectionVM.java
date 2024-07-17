package com.example.expensetracker.activity.view_model;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.expensetracker.enumerator.TransactionType;
import com.example.expensetracker.service.GlobalSelections;


public class TransactionTypeSelectionVM extends ViewModel {
    public MutableLiveData<Boolean> transactionTypeBtnClicked = new MutableLiveData<>(false);
    public MutableLiveData<Drawable> incomeBackground = new MutableLiveData<>(null);
    public MutableLiveData<Drawable> expenseBackground = new MutableLiveData<>(null);
    private final Application application = null;
//
//
//    /**
//     * Constructor with given Application.
//     *
//     * @param application the current application context instance.
//     */
//    public TransactionTypeSelectionVM(Application application) {
//        this.application = application;
//
//        GlobalSelections.updateSelectedTransactionType(application, expenseBackground, incomeBackground);
//    }

    /**
     * Handles the onClick event for the view button with ID 'expense_type_btn'.
     *
     * @param view current View instance.
     */
    public void expenseTypeBtnOnClick(View view) {
        GlobalSelections.selectedTransactionType = TransactionType.Expense;
        GlobalSelections.updateSelectedTransactionType(application, expenseBackground, incomeBackground);
        transactionTypeBtnClicked.setValue(true);
    }

    /**
     * Handles the onClick event for the view button with ID 'income_type_btn'.
     *
     * @param view current View instance.
     */
    public void incomeTypeBtnOnClick(View view) {
        GlobalSelections.selectedTransactionType = TransactionType.Income;
        GlobalSelections.updateSelectedTransactionType(application, expenseBackground, incomeBackground);
        transactionTypeBtnClicked.setValue(true);
    }
}
