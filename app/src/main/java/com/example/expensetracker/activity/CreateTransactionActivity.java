package com.example.expensetracker.activity;

import static com.example.expensetracker.model.Constants.DATE_PICKER_TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.expensetracker.activity.fragment.DatePickerFragment;
import com.example.expensetracker.model.Transaction;
import com.example.expensetracker.service.Global;

import java.util.Locale;
import java.util.Objects;

public class CreateTransactionActivity extends BaseCreateEditActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vm.editBtnText.setValue("Create");
        vm.amount.setValue("");
        vm.comment.setValue("");
        vm.selectedCategoryId.setValue(0);
        vm.uiDate.setValue(
                String.format(Locale.ITALIAN, "%d/%d/%d", vm.date.getDayOfMonth(), vm.date.getMonthValue(), vm.date.getYear())
        );

        vm.addEditBtnClicked.observe(
                this,
                (Boolean clicked) -> {
                    if (clicked) {
                        db.insertNewTransaction(
                                new Transaction(
                                        Double.parseDouble(Objects.requireNonNull(vm.amount.getValue())),
                                        vm.comment.getValue(),
                                        vm.selectedCategoryId.getValue(),
                                        vm.date,
                                        Global.selectedTransactionType
                                )
                        );

                        startActivity(new Intent(this, ListTransactionActivity.class));
                    }
                }
        );

        vm.openDatePickerFragmentClicked.observe(
                this,
                (Boolean clicked) -> {
                    if (clicked) {
                        DatePickerFragment datePickerFragment = new DatePickerFragment();
                        datePickerFragment.datePickerListener = vm;
                        datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);


                    }
                }
        );
    }
}
