package com.example.expensetracker.activity;

import static com.example.expensetracker.model.Constants.DATE_PICKER_TAG;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.fragment.DatePickerFragment;
import com.example.expensetracker.enumerator.TransactionType;
import com.example.expensetracker.model.Transaction;
import com.example.expensetracker.service.GlobalSelections;

import java.util.Objects;

public class EditTransactionActivity extends CreateTransactionActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve transaction data from the DB
        Transaction transaction = dbHelper.retrieveTransaction(
                getIntent().getIntExtra("_id", -1),
                TransactionType.Expense
        );

        vm.amount.setValue(String.valueOf(transaction.amount));
        vm.comment.setValue(transaction.comment);
        vm.uiDate.setValue(String.valueOf(transaction.date));

        Toast.makeText(
                this,
                String.format("ID is: %s", transaction.id),
                Toast.LENGTH_SHORT
        ).show();

    }
}
