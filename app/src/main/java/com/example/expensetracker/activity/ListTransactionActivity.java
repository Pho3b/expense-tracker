package com.example.expensetracker.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.view_model.ListTransactionVM;
import com.example.expensetracker.databinding.ActivityListTransactionBinding;
import com.example.expensetracker.db.TransactionTrackerDbHelper;
import com.example.expensetracker.model.Transaction;
import com.example.expensetracker.enumerator.TimeSpanSelection;
import com.example.expensetracker.enumerator.TransactionType;
import com.example.expensetracker.service.GlobalSelections;
import com.example.expensetracker.service.TransactionAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ListTransactionActivity extends AppCompatActivity {
    protected ListTransactionVM viewModel;
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
        viewModel = new ListTransactionVM(getApplication());
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
                        viewModel.updateAmountsTexts(
                                retrieveTransactionsAmountSum(
                                        GlobalSelections.selectedTransactionType,
                                        GlobalSelections.selectedTimeSpan,
                                        GlobalSelections.selectedDate.getValue()
                                )
                        );

                        updateRecyclerView(
                                retrieveTransactions(
                                        GlobalSelections.selectedTransactionType,
                                        GlobalSelections.selectedTimeSpan,
                                        GlobalSelections.selectedDate.getValue()
                                )
                        );
                    }
                }
        );

        GlobalSelections.selectedDate.observe(
                this,
                (LocalDate selectedDate) -> {
                    viewModel.updateSelectedDateTxt();
                    viewModel.updateAmountsTexts(
                            retrieveTransactionsAmountSum(
                                    GlobalSelections.selectedTransactionType,
                                    GlobalSelections.selectedTimeSpan,
                                    selectedDate
                            )
                    );

                    updateRecyclerView(
                            retrieveTransactions(
                                    GlobalSelections.selectedTransactionType,
                                    GlobalSelections.selectedTimeSpan,
                                    selectedDate
                            )
                    );
                }
        );
    }

    private void updateRecyclerView(ArrayList<Transaction> transactions) {
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

        return dbHelper.retrieveTransactions(
                selectedType,
                LocalDate.parse(startDate.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalDate.parse(endDate.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
    }

    private Integer retrieveTransactionsAmountSum(
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

        return dbHelper.retrieveTransactionsAmountSum(
                selectedType,
                LocalDate.parse(startDate.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalDate.parse(endDate.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
    }

    private void setupDbConnection() {
        dbHelper = new TransactionTrackerDbHelper(this);
    }
}
