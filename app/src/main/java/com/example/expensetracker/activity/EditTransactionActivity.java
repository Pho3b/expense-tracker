package com.example.expensetracker.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.expensetracker.enumerator.TransactionType;
import com.example.expensetracker.model.Transaction;
import com.example.expensetracker.service.Global;

import java.text.DecimalFormat;
import java.time.LocalDate;

public class EditTransactionActivity extends BaseCreateEditActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupActivity();
    }

    private void setupActivity() {
        vm.editBtnText.setValue("Update");

        // Retrieve transaction data from the DB
        Transaction transaction = db.retrieveTransaction(
                getIntent().getIntExtra("_id", -1),
                TransactionType.Expense,
                Global.selectedTransactionType
        );

        DecimalFormat df = new DecimalFormat("0.##");
        vm.amount.setValue(df.format(transaction.amount));
        vm.comment.setValue(transaction.comment);
        vm.uiDate.setValue(String.valueOf(transaction.date));
        vm.selectedCategoryId.setValue(transaction.category_id);

        vm.addEditBtnClicked.observe(
                this,
                (Boolean clicked) -> {
                    if (clicked) {
                        vm.addEditBtnClicked.setValue(false);
                        transaction.amount = Double.parseDouble(vm.amount.getValue());
                        transaction.comment = vm.comment.getValue();
                        transaction.date = LocalDate.parse(vm.uiDate.getValue());
                        transaction.category_id = vm.selectedCategoryId.getValue();
                        db.updateTransaction(transaction);

                        startActivity(new Intent(this, ListTransactionActivity.class));
                    }
                }
        );
    }
}
