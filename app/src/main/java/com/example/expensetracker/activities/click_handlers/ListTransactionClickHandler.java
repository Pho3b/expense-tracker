package com.example.expensetracker.activities.click_handlers;

import android.content.Intent;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.expensetracker.R;
import com.example.expensetracker.activities.CreateTransactionActivity;
import com.example.expensetracker.activities.ListTransactionActivity;
import com.example.expensetracker.enums.TransactionType;
import com.example.expensetracker.models.Constants;

public class ListTransactionClickHandler {
    private final ListTransactionActivity activity;


    public ListTransactionClickHandler(ListTransactionActivity activity) {
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

    public void handleAddTransactionClick() {
        Intent intent = new Intent(activity, CreateTransactionActivity.class);
        intent.putExtra(Constants.TRANSACTION_TYPE_EXTRA, activity.selectedTransactionType);

        activity.startActivity(intent);
    }
}
