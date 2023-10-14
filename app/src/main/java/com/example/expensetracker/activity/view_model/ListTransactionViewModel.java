package com.example.expensetracker.activity.view_model;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.expensetracker.R;
import com.example.expensetracker.shared.enums.TimeSpanBtnBackground;
import com.example.expensetracker.shared.enums.TransactionType;
import com.example.expensetracker.shared.service.GlobalSelections;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ListTransactionViewModel extends ViewModel {
    public MutableLiveData<Drawable> incomeBackground = new MutableLiveData<>(null);
    public MutableLiveData<Drawable> expenseBackground = new MutableLiveData<>(null);

    public MutableLiveData<String> monthAmountTxt = new MutableLiveData<>();
    public MutableLiveData<String> monthYearTxt = new MutableLiveData<>();

    public MutableLiveData<Boolean> startCreateTransactionClicked = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> transactionTypeBtnClicked = new MutableLiveData<>(false);
    public List<MutableLiveData<Drawable>> timeSpanBtnBackgrounds = new ArrayList<>(3);

    private final Application application;


    /**
     * Constructor with given Application.
     *
     * @param application the current application context instance.
     */
    public ListTransactionViewModel(Application application) {
        this.application = application;

        this.updateSelectedDateTxt();
        monthAmountTxt.setValue("222€");

        // Init time span backgrounds list
        timeSpanBtnBackgrounds.add(new MutableLiveData<>(null));
        timeSpanBtnBackgrounds.add(new MutableLiveData<>(null));
        timeSpanBtnBackgrounds.add(new MutableLiveData<>(null));

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

    public void updateSelectedDateTxt() {
        monthYearTxt.setValue(
                application.getString(
                        R.string.month_year_title,
                        Objects.requireNonNull(GlobalSelections.selectedDate.getValue()).getMonth(),
                        Objects.requireNonNull(GlobalSelections.selectedDate.getValue()).getYear()
                )
        );
    }

    public void rightArrowOnClick(View view) {
        LocalDate selectedDate = Objects.requireNonNull(GlobalSelections.selectedDate.getValue());
        GlobalSelections.selectedDate.setValue(selectedDate.plusMonths(1));
    }

    public void leftArrowOnClick(View view) {
        LocalDate selectedDate = Objects.requireNonNull(GlobalSelections.selectedDate.getValue());
        GlobalSelections.selectedDate.setValue(selectedDate.minusMonths(1));
    }

    public void timeSpanBtnOnClick(View view) {
        for (MutableLiveData<Drawable> background : timeSpanBtnBackgrounds) {
            background.setValue(null);
        }

        timeSpanBtnBackgrounds.get(TimeSpanBtnBackground.Month.ordinal()).setValue(
                ContextCompat.getDrawable(application, R.drawable.rounded_blue_background)
        );
    }
}
