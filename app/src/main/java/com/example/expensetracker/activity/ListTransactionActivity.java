package com.example.expensetracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.view_model.ListTransactionViewModel;
import com.example.expensetracker.databinding.ActivityListTransactionBinding;
import com.example.expensetracker.shared.service.GlobalService;
import com.example.expensetracker.ui.expenses.Expense;
import com.example.expensetracker.ui.expenses.ExpenseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListTransactionActivity extends AppCompatActivity {

    protected ListTransactionViewModel viewModel;
    private final Map<Integer, Runnable> onClickActions = new HashMap<>();
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_transaction);

        bindViewModel();
        findViews();
        setupViewValues();

        viewModel.navigateToCreateTransaction.observe(
                this,
                event -> startActivity(new Intent(this, CreateTransactionActivity.class))
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        GlobalService.updateSelectedTransactionType(
                getApplication(),
                viewModel.expenseBackground,
                viewModel.incomeBackground
        );
    }


    private void findViews() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setupViewValues() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ExpenseAdapter(this.retrieveExpenses()));
    }

    private void bindViewModel() {
        viewModel = new ListTransactionViewModel(getApplication());
        ActivityListTransactionBinding binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_list_transaction
        );
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

    private List<Expense> retrieveExpenses() {
        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense("Groceries test lkdfjsalfkjsadlfjaslkdfjaslkdfjalsdfkfdl", 50.0));
        expenses.add(new Expense("Dinner", 30.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Restaurant", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Book", 12.0));

        return expenses;
    }
}
