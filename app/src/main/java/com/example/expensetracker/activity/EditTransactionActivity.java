package com.example.expensetracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.expensetracker.enumerator.TransactionType;
import com.example.expensetracker.model.Transaction;
import com.example.expensetracker.service.GlobalSelections;
import com.example.expensetracker.ui.model.CategoryIconView;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Objects;

public class EditTransactionActivity extends BaseCreateEditActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve transaction data from the DB
        Transaction transaction = db.retrieveTransaction(
                getIntent().getIntExtra("_id", -1),
                TransactionType.Expense
        );

        DecimalFormat df = new DecimalFormat("0.##");
        vm.amount.setValue(df.format(transaction.amount));
        vm.comment.setValue(transaction.comment);
        vm.uiDate.setValue(String.valueOf(transaction.date));
        vm.categoryId = transaction.category_id;

        CategoryIconView categoryIcon = findViewById(transaction.category_id);
        categoryIcon.callOnClick();

        Toast.makeText(
                this,
                String.format("ID is: %s", transaction.id),
                Toast.LENGTH_SHORT
        ).show();

        vm.addTransactionClicked.observe(
                this,
                (Boolean clicked) -> {
                    if (clicked) {
                        transaction.amount = Double.parseDouble(vm.amount.getValue());
                        transaction.comment = vm.comment.getValue();
                        transaction.date = LocalDate.parse(vm.uiDate.getValue());
                        transaction.category_id = vm.categoryId;
                        db.updateTransaction(transaction);

                        startActivity(new Intent(this, ListTransactionActivity.class));
                        vm.addTransactionClicked.setValue(false);
                    }
                }
        );
    }
}
