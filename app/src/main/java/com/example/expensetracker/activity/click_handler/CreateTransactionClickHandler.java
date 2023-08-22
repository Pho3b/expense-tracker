package com.example.expensetracker.activity.click_handler;

import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.CreateTransactionActivity;
import com.example.expensetracker.shared.enums.TransactionType;

public class CreateTransactionClickHandler {
    protected final CreateTransactionActivity activity;


    public CreateTransactionClickHandler(CreateTransactionActivity activity) {
        this.activity = activity;
    }

    public void handleExpenseTypeClick(TextView textView) {
        activity.selectedTransactionType = TransactionType.Expense;
        textView.setBackground(
                ContextCompat.getDrawable(activity, R.drawable.rounded_background)
        );
        activity.incomeTypeTxt.setBackground(null);
    }

    public void handleIncomeTypeClick(TextView textView) {
        activity.selectedTransactionType = TransactionType.Income;
        textView.setBackground(
                ContextCompat.getDrawable(activity, R.drawable.rounded_background)
        );
        activity.expenseTypeTxt.setBackground(null);
    }
}
