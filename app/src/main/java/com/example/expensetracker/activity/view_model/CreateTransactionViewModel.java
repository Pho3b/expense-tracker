package com.example.expensetracker.activity.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateTransactionViewModel extends ViewModel {
    public String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String newData) {
        this.amount = newData;
    }
}
