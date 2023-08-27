package com.example.expensetracker.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.view_model.ListTransactionViewModel;
import com.example.expensetracker.databinding.ActivityListTransactionBinding;
import com.example.expensetracker.shared.service.GlobalService;
import com.example.expensetracker.db.model.Expense;
import com.example.expensetracker.db.service.ExpenseAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ListTransactionActivity extends AppCompatActivity {

    protected ListTransactionViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_transaction);

        bindViewModel();
        setupObservers();
        setupRecyclerView();
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

    /**
     * Binds the current Activity to its ViewModel
     */
    private void bindViewModel() {
        viewModel = new ListTransactionViewModel(getApplication());
        ActivityListTransactionBinding binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_list_transaction
        );
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

    private void setupObservers() {
        // Button responsible to start the 'CreateTransactionActivity'
        viewModel.startCreateTransactionClicked.observe(
                this,
                (Boolean clicked) -> {
                    if (clicked) {
                        startActivity(new Intent(this, CreateTransactionActivity.class));
                    }
                }
        );
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense(123f, "Groceries test lkdfjsalfkjsadlfjaslkdfjaslkdfjalsdfkfdl", 2, LocalDate.now()));
        expenses.add(new Expense(31f, "saldkfjalksfjlaksdf", 1, LocalDate.now()));
        expenses.add(new Expense(3f, "test", 2, LocalDate.now()));
        expenses.add(new Expense(31f, "Cinme", 2, LocalDate.now()));


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ExpenseAdapter(expenses));
    }
}
