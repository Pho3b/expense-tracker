package com.example.expensetracker.activity;

import static com.example.expensetracker.model.Constants.DATE_PICKER_TAG;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.expensetracker.activity.fragment.DatePickerFragment;
import com.example.expensetracker.enumerator.TransactionType;
import com.example.expensetracker.model.Transaction;
import com.example.expensetracker.service.Global;

import java.text.DecimalFormat;
import java.util.Locale;

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
        deleteBtn.setVisibility(View.VISIBLE);
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
        vm.uiDate.setValue(String.format(Locale.ITALIAN, "%d/%d/%d", transaction.date.getDayOfMonth(), transaction.date.getMonthValue(), transaction.date.getYear()));
        vm.selectedCategoryId.setValue(transaction.category_id);
        vm.selectedCategoryId.setValue(transaction.category_id);

        vm.addEditBtnClicked.observe(
                this,
                (Boolean clicked) -> {
                    if (clicked) {
                        transaction.amount = Double.parseDouble(vm.amount.getValue());
                        transaction.comment = vm.comment.getValue();
                        transaction.date = vm.date;
                        transaction.category_id = vm.selectedCategoryId.getValue();
                        db.updateTransaction(transaction);

                        startActivity(new Intent(this, ListTransactionActivity.class));
                    }
                }
        );

        vm.openDatePickerFragmentClicked.observe(
                this,
                (Boolean clicked) -> {
                    if (clicked) {
                        DatePickerFragment datePickerFragment = new DatePickerFragment(
                                transaction.date.getYear(),
                                transaction.date.getMonthValue(),
                                transaction.date.getDayOfMonth()
                        );
                        datePickerFragment.datePickerListener = vm;
                        datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);

                        vm.openDatePickerFragmentClicked.setValue(false);
                    }
                }
        );
    }
}
