package com.example.expensetracker.activity.click_handler;

import androidx.core.content.ContextCompat;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.CreateTransactionActivity;
import com.example.expensetracker.activity.view_model.CreateTransactionViewModel;
import com.example.expensetracker.shared.enums.TransactionType;

public class CreateTransactionClickHandler {
    protected final CreateTransactionActivity activity;
    protected final CreateTransactionViewModel viewModel;


    public CreateTransactionClickHandler(
            CreateTransactionActivity activity,
            CreateTransactionViewModel viewModel
    ) {
        this.activity = activity;
        this.viewModel = viewModel;
    }

    public void handleExpenseTypeClick() {
        activity.selectedTransactionType = TransactionType.Expense;
        viewModel.expenseBackground = ContextCompat.getDrawable(activity, R.drawable.rounded_background);
        viewModel.incomeBackground = null;
    }

    public void handleIncomeTypeClick() {
        activity.selectedTransactionType = TransactionType.Income;
        viewModel.incomeBackground = ContextCompat.getDrawable(activity, R.drawable.rounded_background);
        viewModel.expenseBackground = null;
    }
}
