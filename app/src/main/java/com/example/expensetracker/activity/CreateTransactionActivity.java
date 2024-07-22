package com.example.expensetracker.activity;

import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.Nullable;

import com.example.expensetracker.model.Transaction;
import com.example.expensetracker.service.GlobalSelections;

import java.util.Objects;

public class CreateTransactionActivity extends BaseCreateEditActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vm.addTransactionClicked.observe(
                this,
                (Boolean clicked) -> {
                    if (clicked) {
                        db.insertNewTransaction(
                                new Transaction(
                                        Double.parseDouble(Objects.requireNonNull(vm.amount.getValue())),
                                        vm.comment.getValue(),
                                        vm.categoryId,
                                        vm.date,
                                        GlobalSelections.selectedTransactionType
                                )
                        );

                        startActivity(new Intent(this, ListTransactionActivity.class));
                        vm.addTransactionClicked.setValue(false);
                    }
                }
        );
    }
}
