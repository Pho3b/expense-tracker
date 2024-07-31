package com.example.expensetracker.activity.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelsFactory implements ViewModelProvider.Factory {
    private final Application application;
    private static TransactionTypeSelectionVM transactionTypeSelectionInstance;
    private static CreateEditTransactionVM createTransactionInstance;


    public ViewModelsFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        // TransactionTypeSelectionVM
        if (modelClass.isAssignableFrom(TransactionTypeSelectionVM.class)) {
            if (transactionTypeSelectionInstance == null) {
                transactionTypeSelectionInstance = new TransactionTypeSelectionVM(application);
            }

            return (T) transactionTypeSelectionInstance;
        }

        // CreateTransactionVM
        if (modelClass.isAssignableFrom(CreateEditTransactionVM.class)) {
            if (createTransactionInstance == null) {
                createTransactionInstance = new CreateEditTransactionVM(application.getApplicationContext());
            }

            return (T) createTransactionInstance;
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
