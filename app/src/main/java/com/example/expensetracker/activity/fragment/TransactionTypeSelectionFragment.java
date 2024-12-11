package com.example.expensetracker.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.view_model.ListTransactionVM;
import com.example.expensetracker.activity.view_model.TransactionTypeSelectionVM;
import com.example.expensetracker.activity.view_model.ViewModelsFactory;
import com.example.expensetracker.databinding.TransactionTypeSelectionBinding;
import com.example.expensetracker.db.TransactionTrackerDbHelper;
import com.example.expensetracker.enumerator.TimeSpanSelection;
import com.example.expensetracker.enumerator.TransactionType;
import com.example.expensetracker.service.Global;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class TransactionTypeSelectionFragment extends Fragment {
    public TransactionTypeSelectionVM vm;
    public ListTransactionVM listTransactionVM;

    private TransactionTypeSelectionBinding binding;
    private TransactionTrackerDbHelper db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModelProvider vmp = new ViewModelProvider(this, new ViewModelsFactory(getActivity().getApplication()));
        vm = vmp.get(TransactionTypeSelectionVM.class);
        listTransactionVM = vmp.get(ListTransactionVM.class);

        assert getActivity() != null;
        db = new TransactionTrackerDbHelper(getActivity().getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        assert getActivity() != null;
        binding = DataBindingUtil.inflate(inflater, R.layout.transaction_type_selection, container, false);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.setVm(vm);
        setupObservers();
    }

    private void setupObservers() {
        vm.transactionTypeBtnClicked.observe(
                getViewLifecycleOwner(),
                (Boolean clicked) -> {
                    if (clicked) {
                        updateAmountsTexts();
                    }
                }
        );

        Global.selectedDate.observe(
                getViewLifecycleOwner(),
                (LocalDate selectedDate) -> {
                    updateAmountsTexts();
                }
        );
    }

    private Integer retrieveTransactionsAmountSum(TransactionType selectedType, TimeSpanSelection selectedTimeSpan, LocalDate selectedDate) {
        LocalDate startDate = selectedTimeSpan == TimeSpanSelection.Month ?
                LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(), 1) :
                LocalDate.of(selectedDate.getYear(), 1, 1);
        LocalDate endDate = selectedTimeSpan == TimeSpanSelection.Month ?
                startDate.plusMonths(1).minusDays(1) :
                startDate.plusYears(1).minusDays(1);

        return db.retrieveTransactionsAmountSum(
                selectedType,
                LocalDate.parse(startDate.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalDate.parse(endDate.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
    }

    private void updateAmountsTexts() {
        int expensesSum = retrieveTransactionsAmountSum(TransactionType.Expense, Global.selectedTimeSpan, Global.selectedDate.getValue());
        int incomesSum = retrieveTransactionsAmountSum(TransactionType.Income, Global.selectedTimeSpan, Global.selectedDate.getValue());


        if (Global.selectedTransactionType == TransactionType.Expense)
            listTransactionVM.transactionTotalAmountTxt.setValue(
                    String.format(Locale.getDefault(), "Expenses %d €", expensesSum)
            );
        else
            listTransactionVM.transactionTotalAmountTxt.setValue(
                    String.format(Locale.getDefault(), "Incomes %d €", incomesSum)
            );

        listTransactionVM.netAmountTxt.setValue(String.format(Locale.getDefault(), "Net Amount %d €", (incomesSum - expensesSum)));
    }

}
