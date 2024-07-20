package com.example.expensetracker.activity.view_model;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.expensetracker.R;
import com.example.expensetracker.enumerator.TimeSpanSelection;
import com.example.expensetracker.enumerator.TransactionType;
import com.example.expensetracker.service.GlobalSelections;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;


public class TransactionTypeSelectionVM extends ViewModel {
    // Buttons
    public MutableLiveData<Boolean> transactionTypeBtnClicked = new MutableLiveData<>(false);

    // Background Images
    public MutableLiveData<Drawable> incomeBackground = new MutableLiveData<>(null);
    public MutableLiveData<Drawable> expenseBackground = new MutableLiveData<>(null);

    // Text fields
    public MutableLiveData<String> transactionTotalAmountTxt = new MutableLiveData<>();
    public MutableLiveData<String> monthYearTxt = new MutableLiveData<>();

    private final Application application;


    /**
     * Constructor with given Application.
     *
     * @param application the current application context instance.
     */
    public TransactionTypeSelectionVM(Application application) {
        this.application = application;
        GlobalSelections.updateSelectedTransactionType(application, expenseBackground, incomeBackground);
    }

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

    public void updateAmountsTexts(Integer amount) {
        transactionTotalAmountTxt.setValue(String.format(Locale.getDefault(), "€ %d", amount));
    }

    /**
     * Updates the selected dates displayed text.
     * If the selected TimeSpan is 'month' a '{month} {year}' text will be displayed,
     * otherwise if 'year' is selected, the text will display only the '{year}'.
     */
    public void updateSelectedDateTxt() {
        LocalDate selectedDate = Objects.requireNonNull(GlobalSelections.selectedDate.getValue());
        String displayTxt = application.getString(
                R.string.month_year_title,
                selectedDate.getMonth(),
                selectedDate.getYear()
        );

        if (GlobalSelections.selectedTimeSpan == TimeSpanSelection.Year) {
            displayTxt = application.getString(R.string.year_title, selectedDate.getYear());
        }

        monthYearTxt.setValue(displayTxt);
    }
}
