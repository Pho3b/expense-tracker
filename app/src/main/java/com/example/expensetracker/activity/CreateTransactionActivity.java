package com.example.expensetracker.activity;

import static com.example.expensetracker.shared.Constants.EXPENSE_CATEGORY_ICON_MODELS;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.view_model.CreateTransactionViewModel;
import com.example.expensetracker.databinding.ActivityCreateTransactionBinding;
import com.example.expensetracker.activity.fragment.DatePickerFragment;
import com.example.expensetracker.db.TransactionTrackerDbHelper;
import com.example.expensetracker.db.model.Transaction;
import com.example.expensetracker.shared.Constants;
import com.example.expensetracker.ui.model.CategoryIconImageView;
import com.example.expensetracker.shared.service.GlobalSelections;

import java.util.Objects;

public class CreateTransactionActivity extends AppCompatActivity {

    protected CreateTransactionViewModel viewModel;
    private static final String DATE_PICKER_TAG = "datePicker";
    private TransactionTrackerDbHelper dbHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transaction);

        bindViewModel();
        setupObservers();
        setupCategoryIconsUI();
        setupDbConnection();
    }

    @Override
    protected void onResume() {
        super.onResume();

        GlobalSelections.updateSelectedTransactionType(
                getApplication(),
                viewModel.expenseBackground,
                viewModel.incomeBackground
        );
    }

    /**
     * Binds the current Activity to its ViewModel
     */
    private void bindViewModel() {
        viewModel = new CreateTransactionViewModel(getApplication());
        ActivityCreateTransactionBinding binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_create_transaction
        );
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

    private void setupObservers() {
        // Opens the date-picker Fragment
        viewModel.openDatePickerFragmentClicked.observe(
                this,
                (Boolean clicked) -> {
                    if (clicked) {
                        DatePickerFragment datePickerFragment = new DatePickerFragment();
                        datePickerFragment.datePickerListener = viewModel;
                        datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);
                    }
                }
        );

        // Insert a new Transaction into the Database
        viewModel.addTransactionClicked.observe(
                this,
                (Boolean clicked) -> {
                    if (clicked) {
                        Transaction transaction = new Transaction(
                                Double.parseDouble(Objects.requireNonNull(viewModel.amount.getValue())),
                                viewModel.comment.getValue(),
                                viewModel.categoryId,
                                viewModel.date,
                                GlobalSelections.selectedTransactionType
                        );

                        dbHelper.insertNewTransaction(transaction);
                        startActivity(new Intent(this, ListTransactionActivity.class));
                    }
                }
        );
    }

    private void setupCategoryIconsUI() {
        LinearLayout wrapper = findViewById(R.id.category_ids_wrapper);
        LinearLayout linearLayout = newLinearLayout();
        wrapper.addView(linearLayout);

        for (int i = 1; i <= EXPENSE_CATEGORY_ICON_MODELS.length; i++) {
            int categoryId = i - 1;

            linearLayout.addView(
                    new CategoryIconImageView(
                            this,
                            viewModel,
                            EXPENSE_CATEGORY_ICON_MODELS[categoryId].categoryId,
                            EXPENSE_CATEGORY_ICON_MODELS[categoryId].iconDrawableId
                    )
            );

            TextView tv = new TextView(this);
            tv.setText(EXPENSE_CATEGORY_ICON_MODELS[categoryId].description);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            linearLayout.addView(tv);

            if (i % 3 == 0) {
                linearLayout = newLinearLayout();
                wrapper.addView(linearLayout);
            }

        }
    }

    private LinearLayout newLinearLayout() {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        );

        return linearLayout;
    }

    private void setupDbConnection() {
        dbHelper = new TransactionTrackerDbHelper(this);
    }
}
