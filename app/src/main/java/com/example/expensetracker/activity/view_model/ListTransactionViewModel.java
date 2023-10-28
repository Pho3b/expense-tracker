package com.example.expensetracker.activity.view_model;

import android.app.Application;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.expensetracker.R;
import com.example.expensetracker.shared.enums.TimeSpanSelection;
import com.example.expensetracker.shared.enums.TransactionType;
import com.example.expensetracker.shared.service.GlobalSelections;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

public class ListTransactionViewModel extends ViewModel {
    public MutableLiveData<Drawable> incomeBackground = new MutableLiveData<>(null);
    public MutableLiveData<Drawable> expenseBackground = new MutableLiveData<>(null);

    public MutableLiveData<String> transactionTotalAmountTxt = new MutableLiveData<>();
    public MutableLiveData<String> monthYearTxt = new MutableLiveData<>();

    public MutableLiveData<Boolean> startCreateTransactionClicked = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> transactionTypeBtnClicked = new MutableLiveData<>(false);

    // TimeSpan selection bar related properties
    public MutableLiveData<Integer> customBtnTextColor = new MutableLiveData<>(Color.WHITE);
    public MutableLiveData<Integer> monthBtnTextColor = new MutableLiveData<>(Color.BLACK);
    public MutableLiveData<Integer> yearBtnTextColor = new MutableLiveData<>(Color.WHITE);

    private final Application application;


    /**
     * Constructor with given Application.
     *
     * @param application the current application context instance.
     */
    public ListTransactionViewModel(Application application) {
        this.application = application;

        this.updateSelectedDateTxt();
        GlobalSelections.updateSelectedTransactionType(application, expenseBackground, incomeBackground);
    }

    /**
     * Handles the onClick event for the view button with ID 'init_create_transaction_activity_btn'.
     * Sets to true the boolean Observable 'startCreateTransactionClicked' in order to notify
     * the listening Observables.
     */
    public void initCreateTransactionActivityBtnOnClick() {
        startCreateTransactionClicked.setValue(true);
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

    public void updateAmountsTexts(Integer amount) {
        transactionTotalAmountTxt.setValue(String.format(Locale.getDefault(), "€ %d", amount));
    }

    public void rightArrowOnClick(View view) {
        LocalDate selectedDate = Objects.requireNonNull(GlobalSelections.selectedDate.getValue());

        switch (GlobalSelections.selectedTimeSpan) {
            case Month:
                GlobalSelections.selectedDate.setValue(selectedDate.plusMonths(1));
                break;
            case Year:
                GlobalSelections.selectedDate.setValue(selectedDate.plusYears(1));
                break;
            case Custom:
                break;
        }
    }

    public void leftArrowOnClick(View view) {
        LocalDate selectedDate = Objects.requireNonNull(GlobalSelections.selectedDate.getValue());

        switch (GlobalSelections.selectedTimeSpan) {
            case Month:
                GlobalSelections.selectedDate.setValue(selectedDate.minusMonths(1));
                break;
            case Year:
                GlobalSelections.selectedDate.setValue(selectedDate.minusYears(1));
                break;
            case Custom:
                break;
        }
    }

    public void timeSpanBtnOnClick(View view) {
        String viewIdName = this.application.getResources().getResourceEntryName(view.getId());
        customBtnTextColor.setValue(Color.WHITE);
        monthBtnTextColor.setValue(Color.WHITE);
        yearBtnTextColor.setValue(Color.WHITE);

        switch (viewIdName) {
            case "custom_time_span":
                customBtnTextColor.setValue(Color.BLACK);
                GlobalSelections.selectedTimeSpan = TimeSpanSelection.Custom;
                break;
            case "month_time_span":
                monthBtnTextColor.setValue(Color.BLACK);
                GlobalSelections.selectedTimeSpan = TimeSpanSelection.Month;
                break;
            case "year_time_span":
                yearBtnTextColor.setValue(Color.BLACK);
                GlobalSelections.selectedTimeSpan = TimeSpanSelection.Year;
                break;
        }

        GlobalSelections.selectedDate.setValue(GlobalSelections.selectedDate.getValue());
    }
}
