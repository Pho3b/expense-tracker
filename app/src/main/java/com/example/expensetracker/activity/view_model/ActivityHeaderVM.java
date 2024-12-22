package com.example.expensetracker.activity.view_model;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class ActivityHeaderVM extends ViewModel {
    public MutableLiveData<Boolean> openDrawerBtnClicked = new MutableLiveData<>(false);

    /**
     * Handles the onClick event for the view button with ID 'open_drawer_btn'.
     */
    public void openDrawerBtnOnClick(View ignored) {
        openDrawerBtnClicked.setValue(true);
    }
}
