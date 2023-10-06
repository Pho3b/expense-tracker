package com.example.expensetracker.activity.view_model;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.expensetracker.shared.enums.TransactionType;
import com.example.expensetracker.shared.service.GlobalSelections;
import com.example.expensetracker.ui.model.CategoryIcon;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CreateTransactionViewModel extends ViewModel {
    public MutableLiveData<Drawable> incomeBackground = new MutableLiveData<>(null);
    public MutableLiveData<Drawable> expenseBackground = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> openDatePickerFragmentClicked = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> addTransactionClicked = new MutableLiveData<>(false);
    public MutableLiveData<String> amount = new MutableLiveData<>("0");
    public MutableLiveData<String> comment = new MutableLiveData<>();
    public Integer categoryId = 0;
    public LocalDate date = LocalDate.now();
    private final Application application;

    /**
     * Constructor
     *
     * @param application the current application context instance.
     */
    public CreateTransactionViewModel(Application application) {
        this.application = application;
        GlobalSelections.updateSelectedTransactionType(application, expenseBackground, incomeBackground);
    }

    /**
     * Handles the onClick event for the view button with ID 'expense_type_btn'.
     *
     * @param view current View instance.
     */
    public void onExpenseTypeBtnClick(View view) {
        GlobalSelections.selectedTransactionType = TransactionType.Expense;
        GlobalSelections.updateSelectedTransactionType(application, expenseBackground, incomeBackground);
    }

    /**
     * Handles the onClick event for the view button with ID 'income_type_btn'.
     *
     * @param view current View instance.
     */
    public void onIncomeTypeBtnClick(View view) {
        GlobalSelections.selectedTransactionType = TransactionType.Income;
        GlobalSelections.updateSelectedTransactionType(application, expenseBackground, incomeBackground);
    }

    public void onOpenDatePickerClick(View view) {
        openDatePickerFragmentClicked.setValue(true);
    }

    public void onAddTransactionClick(View view) {
        addTransactionClicked.setValue(true);
    }

    public void onIconClicked(View categoryIcon) {
        categoryId = ((CategoryIcon) categoryIcon).categoryId;
    }

    public void onDateSelected(int year, int month, int day) {
        this.date = LocalDate.parse(
                String.format(Locale.ITALIAN,"%d-%d-%d", day, month, year),
                DateTimeFormatter.ofPattern("d-M-yyyy")
        );
    }
}
