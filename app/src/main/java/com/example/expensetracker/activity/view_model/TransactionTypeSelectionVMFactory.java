package com.example.expensetracker.activity.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TransactionTypeSelectionVMFactory implements ViewModelProvider.Factory {
    private final Application application;
    private static TransactionTypeSelectionVM instance;

    public TransactionTypeSelectionVMFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TransactionTypeSelectionVM.class)) {
            if (instance == null) {
                instance = new TransactionTypeSelectionVM(application);
            }

            return (T) instance;
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
