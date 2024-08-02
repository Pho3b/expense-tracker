package com.example.expensetracker.activity.view_model;

import android.app.Application;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.expensetracker.R;
import com.example.expensetracker.enumerator.TimeSpanSelection;
import com.example.expensetracker.service.GlobalSelections;

import java.time.LocalDate;
import java.util.Objects;

public class ListTransactionVM extends ViewModel {
    public MutableLiveData<Drawable> incomeBackground = new MutableLiveData<>(null);
    public MutableLiveData<Drawable> expenseBackground = new MutableLiveData<>(null);
    public MutableLiveData<String> transactionTotalAmountTxt = new MutableLiveData<>();
    public MutableLiveData<String> monthYearTxt = new MutableLiveData<>();
    public MutableLiveData<Boolean> startCreateTransactionClicked = new MutableLiveData<>(false);

    // TimeSpan selection bar related properties
    public MutableLiveData<Integer> customBtnTextColor = new MutableLiveData<>(Color.WHITE);
    public MutableLiveData<Integer> monthBtnTextColor = new MutableLiveData<>(Color.WHITE);
    public MutableLiveData<Integer> yearBtnTextColor = new MutableLiveData<>(Color.BLACK);

    private final Application application;


    /**
     * Constructor with given Application.
     *
     * @param application the current application context instance.
     */
    public ListTransactionVM(Application application) {
        this.application = application;

        this.updateSelectedDateTxt();
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
        customBtnTextColor.setValue(Color.BLACK);
        monthBtnTextColor.setValue(Color.BLACK);
        yearBtnTextColor.setValue(Color.BLACK);

        switch (viewIdName) {
            case "custom_time_span":
                customBtnTextColor.setValue(Color.WHITE);
                GlobalSelections.selectedTimeSpan = TimeSpanSelection.Custom;
                break;
            case "month_time_span":
                monthBtnTextColor.setValue(Color.WHITE);
                GlobalSelections.selectedTimeSpan = TimeSpanSelection.Month;
                break;
            case "year_time_span":
                yearBtnTextColor.setValue(Color.WHITE);
                GlobalSelections.selectedTimeSpan = TimeSpanSelection.Year;
                break;
        }

        GlobalSelections.selectedDate.setValue(GlobalSelections.selectedDate.getValue());
    }
}
