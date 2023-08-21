package com.example.expensetracker.activities;

import static com.example.expensetracker.shared.Constants.TRANSACTION_TYPE_EXTRA;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.expensetracker.R;
import com.example.expensetracker.activities.click_handlers.CreateTransactionClickHandler;
import com.example.expensetracker.activities.click_handlers.ListTransactionClickHandler;
import com.example.expensetracker.shared.enums.TransactionType;
import com.example.expensetracker.shared.Constants;

import java.util.HashMap;
import java.util.Map;

public class CreateTransactionActivity extends AppCompatActivity {
    public TransactionType selectedTransactionType;
    public TextView expenseTypeTxt;
    public TextView incomeTypeTxt;
    private final Map<Integer, Runnable> onClickActions = new HashMap<>();


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(TRANSACTION_TYPE_EXTRA, selectedTransactionType);
        setResult(200, intent);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transaction);

        findViews();
        setupViewValues();
        associateViewListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupViewValues();
    }

    private void findViews() {
        expenseTypeTxt = findViewById(R.id.expense_type_btn);
        incomeTypeTxt = findViewById(R.id.income_type_btn);
    }

    private void setupViewValues() {
        selectedTransactionType = (TransactionType) getIntent().
                getSerializableExtra(TRANSACTION_TYPE_EXTRA);

        if (selectedTransactionType == TransactionType.Expense) {
            expenseTypeTxt.setBackground(
                    ContextCompat.getDrawable(this, R.drawable.rounded_background)
            );
            incomeTypeTxt.setBackground(null);
        }

        if (selectedTransactionType == TransactionType.Income) {
            incomeTypeTxt.setBackground(
                    ContextCompat.getDrawable(this, R.drawable.rounded_background)
            );
            expenseTypeTxt.setBackground(null);
        }
    }

    private void associateViewListeners() {
        CreateTransactionClickHandler handler = new CreateTransactionClickHandler(this);

        onClickActions.put(expenseTypeTxt.getId(), () -> handler.handleExpenseTypeClick(expenseTypeTxt));
        onClickActions.put(incomeTypeTxt.getId(), () -> handler.handleIncomeTypeClick(incomeTypeTxt));

        expenseTypeTxt.setOnClickListener(onClickListener);
        incomeTypeTxt.setOnClickListener(onClickListener);
    }

    private final View.OnClickListener onClickListener = v -> {
        Runnable action = onClickActions.get(v.getId());

        if (action != null) {
            action.run();
        }
    };
}
