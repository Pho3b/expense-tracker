package com.example.expensetracker.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.expensetracker.R;
import com.example.expensetracker.enums.TransactionType;
import com.example.expensetracker.models.Constants;

public class CreateTransactionActivity extends AppCompatActivity {
    private TextView expenseTypeTxt;
    private TextView incomeTypeTxt;
    private TransactionType selectedTransactionType;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transaction);

        expenseTypeTxt = findViewById(R.id.expense_type_btn);
        incomeTypeTxt = findViewById(R.id.income_type_btn);


        selectedTransactionType = (TransactionType) getIntent().
                getSerializableExtra(Constants.TRANSACTION_TYPE_EXTRA);

        updateTransactionTypeTxt(selectedTransactionType);
    }

    @Override
    protected void onResume() {
        super.onResume();

        selectedTransactionType = (TransactionType) getIntent().
                getSerializableExtra(Constants.TRANSACTION_TYPE_EXTRA);

        updateTransactionTypeTxt(selectedTransactionType);
    }

    private void updateTransactionTypeTxt(TransactionType transactionType) {
        if (transactionType == TransactionType.Expense) {
            expenseTypeTxt.setBackground(
                    ContextCompat.getDrawable(this, R.drawable.rounded_background)
            );
            incomeTypeTxt.setBackground(null);
        } else {
            incomeTypeTxt.setBackground(
                    ContextCompat.getDrawable(this, R.drawable.rounded_background)
            );
            expenseTypeTxt.setBackground(null);
        }
    }
}
