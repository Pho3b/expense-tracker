package com.example.expensetracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.fragment.TransactionTypeSelectionFragment;
import com.example.expensetracker.activity.view_model.ListTransactionVM;
import com.example.expensetracker.activity.view_model.TransactionTypeSelectionVM;
import com.example.expensetracker.activity.view_model.ViewModelsFactory;
import com.example.expensetracker.databinding.ActivityListTransactionBinding;
import com.example.expensetracker.db.TransactionTrackerDbHelper;
import com.example.expensetracker.model.Transaction;
import com.example.expensetracker.enumerator.TimeSpanSelection;
import com.example.expensetracker.enumerator.TransactionType;
import com.example.expensetracker.service.Global;
import com.example.expensetracker.service.TransactionAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ListTransactionActivity extends AppCompatActivity {
    protected ListTransactionVM vm;
    protected TransactionTypeSelectionVM transactionTypeSelectionVM;
    private TransactionTrackerDbHelper dbHelper;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_transaction);
        dbHelper = new TransactionTrackerDbHelper(this);

        // Adds the TransactionTypeSelectionFragment to the activity
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.type_selection_fragment_container, new TransactionTypeSelectionFragment());
        transaction.commit();

        initViewModels();
        setupObservers();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Global.updateSelectedTransactionType(
                getApplication(),
                vm.expenseBackground,
                vm.incomeBackground
        );
    }

    /**
     * Binds the current Activity to its ViewModel
     */
    private void initViewModels() {
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, new ViewModelsFactory(getApplication()));
        vm = viewModelProvider.get(ListTransactionVM.class);
        transactionTypeSelectionVM = viewModelProvider.get(TransactionTypeSelectionVM.class);

        ActivityListTransactionBinding binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_list_transaction
        );
        binding.setViewModel(vm);
        binding.setLifecycleOwner(this);

        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setupObservers() {
        vm.startCreateTransactionClicked.observe(
                this,
                (Boolean clicked) -> {
                    if (clicked) {
                        startActivity(new Intent(this, CreateTransactionActivity.class));
                    }
                }
        );


        transactionTypeSelectionVM.transactionTypeBtnClicked.observe(
                this,
                (Boolean clicked) -> {
                    if (clicked) {
                        updateRecyclerView(
                                retrieveTransactions(
                                        Global.selectedTransactionType,
                                        Global.selectedTimeSpan,
                                        Global.selectedDate.getValue()
                                )
                        );
                    }
                }
        );

        Global.selectedDate.observe(
                this,
                (LocalDate selectedDate) -> {
                    vm.updateSelectedDateTxt();

                    updateRecyclerView(
                            retrieveTransactions(Global.selectedTransactionType, Global.selectedTimeSpan, selectedDate)
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
}
