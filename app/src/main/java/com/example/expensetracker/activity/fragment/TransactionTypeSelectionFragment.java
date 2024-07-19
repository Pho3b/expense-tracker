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
import com.example.expensetracker.activity.view_model.TransactionTypeSelectionVM;
import com.example.expensetracker.activity.view_model.TransactionTypeSelectionVMFactory;
import com.example.expensetracker.databinding.TransactionTypeSelectionBinding;
import com.example.expensetracker.db.TransactionTrackerDbHelper;
import com.example.expensetracker.enumerator.TimeSpanSelection;
import com.example.expensetracker.enumerator.TransactionType;
import com.example.expensetracker.service.GlobalSelections;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class TransactionTypeSelectionFragment extends Fragment {
    public TransactionTypeSelectionVM vm;

    private TransactionTypeSelectionBinding binding;
    private TransactionTrackerDbHelper db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getActivity() != null;
        db = new TransactionTrackerDbHelper(getActivity().getApplicationContext());
        vm = new ViewModelProvider(
                this, new TransactionTypeSelectionVMFactory(getActivity().getApplication())
        ).get(TransactionTypeSelectionVM.class);
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
                        vm.updateAmountsTexts(
                                retrieveTransactionsAmountSum(
                                        GlobalSelections.selectedTransactionType,
                                        GlobalSelections.selectedTimeSpan,
                                        GlobalSelections.selectedDate.getValue()
                                )
                        );
                    }
                }
        );

        GlobalSelections.selectedDate.observe(
                getViewLifecycleOwner(),
                (LocalDate selectedDate) -> {
                    vm.updateSelectedDateTxt();
                    vm.updateAmountsTexts(
                            retrieveTransactionsAmountSum(
                                    GlobalSelections.selectedTransactionType,
                                    GlobalSelections.selectedTimeSpan,
                                    selectedDate
                            )
                    );
                }
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

        return db.retrieveTransactionsAmountSum(
                selectedType,
                LocalDate.parse(startDate.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalDate.parse(endDate.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
    }
}
