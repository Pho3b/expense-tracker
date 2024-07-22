package com.example.expensetracker.activity.view_model;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.expensetracker.model.Constants;
import com.example.expensetracker.ui.model.CategoryIconView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CreateEditTransactionVM extends ViewModel {
    public LocalDate date = LocalDate.now();
    public Integer categoryId = 0;
    public MutableLiveData<Drawable> incomeBackground = new MutableLiveData<>();
    public MutableLiveData<Drawable> expenseBackground = new MutableLiveData<>();
    public MutableLiveData<Boolean> openDatePickerFragmentClicked = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> addTransactionClicked = new MutableLiveData<>(false);
    public MutableLiveData<String> uiDate = new MutableLiveData<>();
    public MutableLiveData<String> amount = new MutableLiveData<>();
    public MutableLiveData<String> comment = new MutableLiveData<>();


    public void setupUI() {
        uiDate.setValue(String.format(
                Locale.ITALIAN, "%d-%d-%d", date.getDayOfMonth(), date.getMonthValue(), date.getYear())
        );
    }

    public void onOpenDatePickerClick(View view) {
        Log.d(Constants.MY_DEBUG_LOG_TAG, "CLICKED !!!!");
        openDatePickerFragmentClicked.setValue(true);
    }

    public void onAddTransactionClick(View view) {
        addTransactionClicked.setValue(true);
    }

    public void onCategoryIconClick(View categoryIcon) {
        CategoryIconView iconImageView = (CategoryIconView) categoryIcon;

        categoryId = iconImageView.categoryId;
        iconImageView.setBackgroundColor(Color.WHITE);
    }

    public void onDateSelected(int year, int month, int day) {
        uiDate.setValue(String.format(Locale.ITALIAN, "%d-%d-%d", day, month, year));

        this.date = LocalDate.parse(
                String.format(Locale.ITALIAN, "%d-%d-%d", day, month, year),
                DateTimeFormatter.ofPattern("d-M-yyyy")
        );
    }
}
