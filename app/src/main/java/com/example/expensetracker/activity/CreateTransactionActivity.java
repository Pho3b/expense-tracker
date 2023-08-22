package com.example.expensetracker.activity;

import static com.example.expensetracker.shared.Constants.TRANSACTION_TYPE_EXTRA;

import android.content.Intent;
import android.os.Bundle;
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
    public TextView expenseTypeTxt;
    public TextView incomeTypeTxt;
    private final Map<Integer, Runnable> onClickActions = new HashMap<>();


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

        CreateTransactionViewModel vm = new ViewModelProvider(this)
                .get(CreateTransactionViewModel.class);
        ActivityCreateTransactionBinding binding = DataBindingUtil
                 .setContentView(this, R.layout.activity_create_transaction);
        binding.setViewModel(vm);
        binding.setLifecycleOwner(this);

        vm.setAmount("test");

        findViews();
        setupViewValues();
        associateViewListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupViewValues();
    }

    private void findViews() {
        expenseTypeTxt = findViewById(R.id.expense_type_btn);
        incomeTypeTxt = findViewById(R.id.income_type_btn);
    }

    private void setupViewValues() {
        selectedTransactionType = (TransactionType) getIntent().
                getSerializableExtra(TRANSACTION_TYPE_EXTRA);

        if (selectedTransactionType == TransactionType.Expense) {
            expenseTypeTxt.setBackground(
                    ContextCompat.getDrawable(this, R.drawable.rounded_background)
            );
            incomeTypeTxt.setBackground(null);
        }

        if (selectedTransactionType == TransactionType.Income) {
            incomeTypeTxt.setBackground(
                    ContextCompat.getDrawable(this, R.drawable.rounded_background)
            );
            expenseTypeTxt.setBackground(null);
        }
    }

    private void associateViewListeners() {
        CreateTransactionClickHandler handler = new CreateTransactionClickHandler(this);

        onClickActions.put(expenseTypeTxt.getId(), () -> handler.handleExpenseTypeClick(expenseTypeTxt));
        onClickActions.put(incomeTypeTxt.getId(), () -> handler.handleIncomeTypeClick(incomeTypeTxt));

        expenseTypeTxt.setOnClickListener(onClickListener);
        incomeTypeTxt.setOnClickListener(onClickListener);
    }

    private final View.OnClickListener onClickListener = v -> {
        Runnable action = onClickActions.get(v.getId());

        if (action != null) {
            action.run();
        }
    };
}
