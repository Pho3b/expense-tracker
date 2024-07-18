package com.example.expensetracker.activity.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TransactionTypeSelectionVMFactory implements ViewModelProvider.Factory {
    private final Application application;

    public TransactionTypeSelectionVMFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TransactionTypeSelectionVM.class)) {
            return (T) new TransactionTypeSelectionVM(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
