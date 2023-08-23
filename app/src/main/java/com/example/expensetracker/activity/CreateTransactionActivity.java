package com.example.expensetracker.activity;

import static com.example.expensetracker.shared.Constants.TRANSACTION_TYPE_EXTRA;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.click_handler.CreateTransactionClickHandler;
import com.example.expensetracker.activity.view_model.CreateTransactionViewModel;
import com.example.expensetracker.databinding.ActivityCreateTransactionBinding;
import com.example.expensetracker.shared.enums.TransactionType;

import java.util.HashMap;
import java.util.Map;

public class CreateTransactionActivity extends AppCompatActivity {
    public TransactionType selectedTransactionType;
    private final Map<Integer, Runnable> onClickActions = new HashMap<>();

    protected CreateTransactionViewModel viewModel;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(TRANSACTION_TYPE_EXTRA, selectedTransactionType);
        setResult(200, intent);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transaction);

        viewModel = new ViewModelProvider(this)
                .get(CreateTransactionViewModel.class);
        ActivityCreateTransactionBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_create_transaction);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.amount = "13.51";
        viewModel.transactionAmountHint = getResources().getString(R.string.transaction_amount);

        setupViewValues();
        associateViewListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupViewValues();
    }

    private void setupViewValues() {
        selectedTransactionType = (TransactionType) getIntent().
                getSerializableExtra(TRANSACTION_TYPE_EXTRA);

        if (selectedTransactionType == TransactionType.Expense) {
            viewModel.incomeBackground = null;
            viewModel.expenseBackground = ContextCompat.getDrawable(
                    this,
                    R.drawable.rounded_background
            );
        }

        if (selectedTransactionType == TransactionType.Income) {
            viewModel.expenseBackground = null;
            viewModel.incomeBackground = ContextCompat.getDrawable(
                    this,
                    R.drawable.rounded_background
            );
        }
    }

    private void associateViewListeners() {
        CreateTransactionClickHandler handler = new CreateTransactionClickHandler(this, viewModel);

        onClickActions.put(R.id.expense_type_btn, handler::handleExpenseTypeClick);
        onClickActions.put(R.id.income_type_btn, handler::handleIncomeTypeClick);
    }

    private final View.OnClickListener onClickListener = v -> {
        Runnable action = onClickActions.get(v.getId());

        if (action != null) {
            action.run();
        }
    };
}
