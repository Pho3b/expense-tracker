package com.example.expensetracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.expensetracker.model.Constants.DEL_TRANSACTION_ID;

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
    private TransactionTrackerDbHelper dbHelper;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MY-DEBUG", "ListTransactionActivity onCreate transaction Type: " + Global.selectedTransactionType.toString());

        // setContentView(R.layout.activity_list_transaction);
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
        Global.updateSelectedTransactionType(getApplication(), vm.expenseBackground, vm.incomeBackground);

        try {
            Integer del_transaction_id = Objects.requireNonNull(getIntent().getExtras()).getInt(DEL_TRANSACTION_ID);

            Toast.makeText(
                    this,
                    String.format(Locale.ITALY, "Correctly Deleted Transaction {%d}", del_transaction_id),
                    Toast.LENGTH_SHORT
            ).show();
            getIntent().removeExtra(DEL_TRANSACTION_ID);
        } catch (NullPointerException ignored) {
            Log.d("MY-DEBUG", "Null value received after deletion");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("MY-DEBUG", "ListTransactionActivity onStop  transaction Type: " + Global.selectedTransactionType.toString());
        vm.startCreateTransactionClicked.setValue(false);
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
                    Log.d("MY-DEBUG", String.format("%s, clicked value: %b", "inside startCreateTransactionClicked", clicked));

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

        ArrayList<Transaction> transactions = dbHelper.retrieveTransactions(
                selectedType,
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
