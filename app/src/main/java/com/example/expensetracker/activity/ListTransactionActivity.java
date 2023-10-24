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
import com.example.expensetracker.db.TransactionTrackerDbHelper;
import com.example.expensetracker.db.model.Transaction;
import com.example.expensetracker.shared.enums.TimeSpanSelection;
import com.example.expensetracker.shared.enums.TransactionType;
import com.example.expensetracker.shared.service.GlobalSelections;
import com.example.expensetracker.db.service.TransactionAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class ListTransactionActivity extends AppCompatActivity {

    protected ListTransactionViewModel viewModel;
    private TransactionTrackerDbHelper dbHelper;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_transaction);

        setupDbConnection();
        bindViewModel();
        setupObservers();
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
        viewModel = new ListTransactionViewModel(getApplication());
        ActivityListTransactionBinding binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_list_transaction
        );
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        recyclerView = findViewById(R.id.recyclerView);
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

        viewModel.transactionTypeBtnClicked.observe(
                this,
                (Boolean clicked) -> {
                    if (clicked) {
                        setupRecyclerView(
                                retrieveTransactions(
                                        GlobalSelections.selectedTransactionType,
                                        GlobalSelections.selectedTimeSpan,
                                        Objects.requireNonNull(GlobalSelections.selectedDate.getValue())
                                )
                        );
                    }
                }
        );

        GlobalSelections.selectedDate.observe(
                this,
                (LocalDate selectedDate) -> {
                    viewModel.updateSelectedDateTxt();
                    setupRecyclerView(
                            retrieveTransactions(
                                    GlobalSelections.selectedTransactionType,
                                    GlobalSelections.selectedTimeSpan,
                                    Objects.requireNonNull(GlobalSelections.selectedDate.getValue())
                            )
                    );
                }
        );
    }

    private void setupRecyclerView(ArrayList<Transaction> transactions) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TransactionAdapter(transactions));
    }

    private ArrayList<Transaction> retrieveTransactions(
            TransactionType selectedType,
            TimeSpanSelection selectedTimeSpan,
            LocalDate selectedDate
    ) {
        LocalDate startDate = selectedTimeSpan == TimeSpanSelection.Month ?
                LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(), 1) :
                LocalDate.of(selectedDate.getYear(), 1, 1);
        LocalDate endDate = selectedTimeSpan == TimeSpanSelection.Month ?
                startDate.plusMonths(1).minusDays(1) :
                startDate.plusYears(1).minusDays(1);

        startDate = LocalDate.parse(startDate.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        endDate = LocalDate.parse(endDate.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return dbHelper.queryReadTransactions(
                selectedType,
                startDate,
                endDate
        );
    }

    private void setupDbConnection() {
        dbHelper = new TransactionTrackerDbHelper(this);
    }
}
