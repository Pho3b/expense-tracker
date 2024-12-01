package com.example.expensetracker.activity;

import static com.example.expensetracker.model.Constants.DATE_PICKER_TAG;
import static com.example.expensetracker.model.Constants.ET_LOGS_TAG;
import static com.example.expensetracker.model.Constants.ET_LOGS_TAG_DEV;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.fragment.DatePickerFragment;
import com.example.expensetracker.model.Transaction;
import com.example.expensetracker.service.Global;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

public class CreateTransactionActivity extends BaseCreateEditActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeUI();
        observeCreateButton();
        observeDatePickerFragment();
    }

    private void initializeUI() {
        Log.d(ET_LOGS_TAG_DEV, "CreateTransactionActivity method(initializeUI)");

        LocalDate nowDate = LocalDate.now();

        deleteBtn.setVisibility(View.INVISIBLE);
        vm.editBtnText.setValue(getApplication().getString(R.string.transaction_crud_create));
        vm.amount.setValue("");
        vm.comment.setValue("");
        vm.selectedCategoryId.setValue(0);
        vm.date = nowDate;
        vm.uiDate.setValue(
                String.format(
                        Locale.ITALIAN,
                        "%d/%d/%d",
                        nowDate.getDayOfMonth(),
                        nowDate.getMonthValue(),
                        nowDate.getYear()
                )
        );
    }

    private void observeCreateButton() {
        vm.addEditBtnClicked.observe(
                this,
                (Boolean clicked) -> {
                    if (clicked) {
                        try {
                            db.insertNewTransaction(
                                    new Transaction(
                                            Double.parseDouble(vm.amount.getValue()),
                                            vm.comment.getValue(),
                                            Objects.requireNonNull(vm.selectedCategoryId.getValue()),
                                            vm.date,
                                            Global.selectedTransactionType,
                                            false
                                    )
                            );
                        } catch (NullPointerException e) {
                            Log.e(ET_LOGS_TAG, "Transaction not created cause View selected category id is NULL");
                        } catch (NumberFormatException e) {
                            Log.e(ET_LOGS_TAG, "Transaction not created cause the current amount value is not a number");
                        }

                        vm.addEditBtnClicked.setValue(false);
                        startActivity(new Intent(this, ListTransactionActivity.class));
                    }
                }
        );
    }

    private void observeDatePickerFragment() {
        vm.openDatePickerFragmentClicked.observe(
                this,
                (Boolean clicked) -> {
                    if (clicked) {
                        DatePickerFragment datePickerFragment = new DatePickerFragment();
                        datePickerFragment.datePickerListener = vm;
                        datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);

                        vm.openDatePickerFragmentClicked.setValue(false);
                    }
                }
        );
    }
}
