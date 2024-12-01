package com.example.expensetracker.activity;

import static com.example.expensetracker.model.Constants.CLICKED_TRANS_ID_EXTRA;
import static com.example.expensetracker.model.Constants.DATE_PICKER_TAG;
import static com.example.expensetracker.model.Constants.DEL_TRANSACTION_ID;
import static com.example.expensetracker.model.Constants.ET_LOGS_TAG;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.fragment.DatePickerFragment;
import com.example.expensetracker.model.Transaction;
import com.example.expensetracker.service.Global;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Objects;

public class EditTransactionActivity extends BaseCreateEditActivity {

    @Override
    protected void onResume() {
        super.onResume();

        Transaction transaction = db.retrieveTransaction(
                getIntent().getIntExtra(CLICKED_TRANS_ID_EXTRA, -1),
                Global.selectedTransactionType
        );

        initializeUI(transaction);
        populateViewModel(transaction);
        observeUpdateButton(transaction);
        observeDatePickerFragment(transaction);
        observeDeleteButton(transaction);
    }

    private void initializeUI(Transaction transaction) {
        deleteBtn.setVisibility(View.VISIBLE);
        vm.editBtnText.setValue(getApplication().getString(R.string.transaction_crud_update));
        vm.uiDate.setValue(String.format(
                        Locale.ITALIAN,
                        "%d/%d/%d",
                        transaction.date.getDayOfMonth(),
                        transaction.date.getMonthValue(),
                        transaction.date.getYear()
                )
        );
    }

    private void populateViewModel(Transaction transaction) {
        vm.amount.setValue(new DecimalFormat("0.##").format(transaction.amount));
        vm.comment.setValue(transaction.comment);
        vm.selectedCategoryId.setValue(transaction.category_id);
        vm.date = transaction.date;
    }

    private void observeUpdateButton(Transaction transaction) {
        vm.addEditBtnClicked.observe(
                this,
                (Boolean clicked) -> {
                    if (clicked) {
                        try {
                            transaction.amount = Double.parseDouble(vm.amount.getValue());
                            transaction.comment = vm.comment.getValue();
                            transaction.date = vm.date;
                            transaction.category_id = Objects.requireNonNull(vm.selectedCategoryId.getValue());
                            db.updateTransaction(transaction);
                        } catch (NullPointerException e) {
                            Log.e(
                                    ET_LOGS_TAG,
                                    String.format(
                                            "Transaction with ID %d not update cause View selected category id is NULL",
                                            transaction.id
                                    )
                            );
                        }

                        vm.addEditBtnClicked.setValue(false);
                        startActivity(new Intent(this, ListTransactionActivity.class));
                    }
                }
        );
    }

    private void observeDatePickerFragment(Transaction transaction) {
        vm.openDatePickerFragmentClicked.observe(
                this,
                (Boolean clicked) -> {
                    if (clicked) {
                        DatePickerFragment datePickerFragment = new DatePickerFragment(
                                transaction.date.getYear(),
                                (transaction.date.getMonth().getValue() - 1),
                                transaction.date.getDayOfMonth()
                        );
                        datePickerFragment.datePickerListener = vm;
                        datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);

                        vm.openDatePickerFragmentClicked.setValue(false);
                    }
                }
        );
    }

    private void observeDeleteButton(Transaction transaction) {
        vm.deleteTransactionBtnClicked.observe(this, clicked -> {
            if (clicked) {
                Intent intent = new Intent(this, ListTransactionActivity.class)
                        .putExtra(DEL_TRANSACTION_ID, transaction.id);
                db.deleteTransaction(transaction);
                vm.deleteTransactionBtnClicked.setValue(false);

                startActivity(intent);
            }
        });
    }

}
