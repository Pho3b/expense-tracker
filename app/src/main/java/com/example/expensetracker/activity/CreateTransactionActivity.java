package com.example.expensetracker.activity;

import static com.example.expensetracker.model.Constants.DATE_PICKER_TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.expensetracker.activity.fragment.DatePickerFragment;
import com.example.expensetracker.model.Transaction;
import com.example.expensetracker.service.Global;

import java.util.Locale;

public class CreateTransactionActivity extends BaseCreateEditActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        deleteBtn.setVisibility(View.INVISIBLE);
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
                Log.d("MY-DEBUG", String.format("%s, clicked value: %b","inside addEditBtnClicked", clicked));

                if (clicked) {

                    String amount = vm.amount.getValue();

                    if (amount != null && !amount.isEmpty()) {
                        {
                            db.insertNewTransaction(
                                    new Transaction(
                                            Double.parseDouble(amount),
                                            vm.comment.getValue(),
                                            vm.selectedCategoryId.getValue(),
                                            vm.date,
                                            Global.selectedTransactionType
                                    )
                            );
                        }
                    }

                    vm.addEditBtnClicked.setValue(false);
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

    @Override
    protected void onStop() {
        super.onStop();

        vm.addEditBtnClicked.setValue(false);
    }
}
