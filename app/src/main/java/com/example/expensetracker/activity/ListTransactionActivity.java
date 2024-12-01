package com.example.expensetracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.expensetracker.model.Constants.DEL_TRANSACTION_ID;
import static com.example.expensetracker.model.Constants.ET_LOGS_TAG;

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
import java.util.Locale;
import java.util.Objects;

public class ListTransactionActivity extends AppCompatActivity {
    protected ListTransactionVM vm;
    protected TransactionTypeSelectionVM transactionTypeSelectionVM;
    private TransactionTrackerDbHelper db;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new TransactionTrackerDbHelper(this);

        initializeUI();
        observeCreateTransactionButton();
        observeTransactionTypeButton();
        observeDateSpanSelectionButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Global.updateSelectedTransactionType(getApplication(), vm.expenseBackground, vm.incomeBackground);

        try {
            Integer del_transaction_id = Objects.requireNonNull(getIntent().getExtras()).getInt(DEL_TRANSACTION_ID);
            getIntent().removeExtra(DEL_TRANSACTION_ID);

            Toast.makeText(
                    this,
                    String.format(Locale.ITALY, "Correctly Deleted Transaction {%d}", del_transaction_id),
                    Toast.LENGTH_SHORT
            ).show();
        } catch (NullPointerException ignored) {
            Log.w(ET_LOGS_TAG, "Null value received after deletion");
        }
    }

    private void initializeUI() {
        // Initializes the Activity ViewModels
        ViewModelProvider vmProvider = new ViewModelProvider(this, new ViewModelsFactory(getApplication()));
        vm = vmProvider.get(ListTransactionVM.class);
        transactionTypeSelectionVM = vmProvider.get(TransactionTypeSelectionVM.class);

        // Binding the ViewModel and the Activity to the layout
        ActivityListTransactionBinding b = DataBindingUtil.setContentView(this, R.layout.activity_list_transaction);
        b.setViewModel(vm);
        b.setLifecycleOwner(this);

        // Initialize the Activity UI elements
        recyclerView = findViewById(R.id.recyclerView);

        // Adds the TransactionTypeSelectionFragment to the activity
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.type_selection_fragment_container, new TransactionTypeSelectionFragment()).
                commit();
    }

    private void observeCreateTransactionButton() {
        vm.startCreateTransactionClicked.observe(
                this,
                (Boolean clicked) -> {
                    if (clicked) {
                        vm.startCreateTransactionClicked.setValue(false);
                        startActivity(new Intent(this, CreateTransactionActivity.class));
                    }
                }
        );
    }

    private void observeTransactionTypeButton() {
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
    }

    private void observeDateSpanSelectionButtons() {
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

    private ArrayList<Transaction> retrieveTransactions(TransactionType type, TimeSpanSelection timeSpan, LocalDate date) {
        LocalDate startDate = timeSpan == TimeSpanSelection.Month ?
                LocalDate.of(date.getYear(), date.getMonth(), 1) :
                LocalDate.of(date.getYear(), 1, 1);
        LocalDate endDate = timeSpan == TimeSpanSelection.Month ?
                startDate.plusMonths(1).minusDays(1) :
                startDate.plusYears(1).minusDays(1);

        ArrayList<Transaction> transactions = db.retrieveTransactions(
                type,
                LocalDate.parse(startDate.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalDate.parse(endDate.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );

        LocalDate currentDate = null;

        for (Transaction t : transactions) {
            if (currentDate == null || !currentDate.equals(t.date)) {
                currentDate = t.date;
                t.isHeader = true;

                continue;
            }

            t.isHeader = false;
        }


        return transactions;
    }
}
