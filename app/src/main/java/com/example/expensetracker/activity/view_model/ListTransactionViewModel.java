package com.example.expensetracker.activity.view_model;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.expensetracker.R;
import com.example.expensetracker.shared.enums.TransactionType;
import com.example.expensetracker.shared.service.GlobalService;

import java.time.LocalDate;

public class ListTransactionViewModel extends ViewModel {
    public MutableLiveData<Drawable> incomeBackground = new MutableLiveData<>(null);
    public MutableLiveData<Drawable> expenseBackground = new MutableLiveData<>(null);

    public MutableLiveData<String> monthAmountTxt = new MutableLiveData<>();
    public MutableLiveData<String> monthYearTxt = new MutableLiveData<>();

    public MutableLiveData<Boolean> startCreateTransactionClicked = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> transactionTypeBtnClicked = new MutableLiveData<>(false);

    private final Application application;


    /**
     * Constructor with given Application.
     *
     * @param application the current application context instance.
     */
    public ListTransactionViewModel(Application application) {
        this.application = application;

        LocalDate currentDate = LocalDate.now();
        monthYearTxt.setValue(
                application.getString(
                        R.string.month_year_title,
                        currentDate.getMonth(),
                        currentDate.getYear()
                )
        );
        monthAmountTxt.setValue("222€");

        GlobalService.updateSelectedTransactionType(application, expenseBackground, incomeBackground);
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
        GlobalService.selectedTransactionType = TransactionType.Expense;
        GlobalService.updateSelectedTransactionType(application, expenseBackground, incomeBackground);
        transactionTypeBtnClicked.setValue(true);
    }

    /**
     * Handles the onClick event for the view button with ID 'income_type_btn'.
     *
     * @param view current View instance.
     */
    public void incomeTypeBtnOnClick(View view) {
        GlobalService.selectedTransactionType = TransactionType.Income;
        GlobalService.updateSelectedTransactionType(application, expenseBackground, incomeBackground);
        transactionTypeBtnClicked.setValue(true);
    }
}
