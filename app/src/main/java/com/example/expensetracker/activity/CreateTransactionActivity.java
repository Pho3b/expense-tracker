package com.example.expensetracker.activity;

import static com.example.expensetracker.model.Constants.DATE_PICKER_TAG;
import static com.example.expensetracker.model.Constants.EXPENSE_ICON_MODELS;
import static com.example.expensetracker.model.Constants.INCOME_ICON_MODELS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.fragment.TransactionTypeSelectionFragment;
import com.example.expensetracker.activity.view_model.CreateTransactionVM;
import com.example.expensetracker.activity.view_model.TransactionTypeSelectionVM;
import com.example.expensetracker.activity.view_model.ViewModelsFactory;
import com.example.expensetracker.databinding.ActivityCreateEditTransactionBinding;
import com.example.expensetracker.activity.fragment.DatePickerFragment;
import com.example.expensetracker.db.TransactionTrackerDbHelper;
import com.example.expensetracker.model.Transaction;
import com.example.expensetracker.model.CategoryIcon;
import com.example.expensetracker.enumerator.TransactionType;
import com.example.expensetracker.ui.model.CategoryIconImageView;
import com.example.expensetracker.service.GlobalSelections;

import java.util.Objects;

public class CreateTransactionActivity extends AppCompatActivity {
    protected TransactionTypeSelectionVM transactionTypeSelectionVM;
    protected CreateTransactionVM vm;
    protected TransactionTrackerDbHelper dbHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_transaction);

        // Adds the TransactionTypeSelectionFragment to the activity
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.type_selection_fragment_container, new TransactionTypeSelectionFragment());
        transaction.commit();

        initViewModels();
        setupObservers();
        setupDbConnection();
        setupCategoryIconsUI(GlobalSelections.selectedTransactionType);
    }

    @Override
    protected void onResume() {
        super.onResume();

        GlobalSelections.updateSelectedTransactionType(
                getApplication(),
                vm.expenseBackground,
                vm.incomeBackground
        );
    }

    /**
     * Binds the current Activity to its ViewModel
     */
    protected void initViewModels() {
        vm = new ViewModelProvider(this, new ViewModelsFactory(getApplication())).get(CreateTransactionVM.class);
        transactionTypeSelectionVM = new ViewModelProvider(this, new ViewModelsFactory(getApplication())).
                get(TransactionTypeSelectionVM.class);

        ActivityCreateEditTransactionBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_create_edit_transaction);
        binding.setViewModel(vm);
        binding.setLifecycleOwner(this);
    }

    protected void setupObservers() {
        // Opens the date-picker Fragment
        vm.openDatePickerFragmentClicked.observe(
                this,
                (Boolean clicked) -> {
                    if (clicked) {
                        DatePickerFragment datePickerFragment = new DatePickerFragment();
                        datePickerFragment.datePickerListener = vm;
                        datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);
                    }
                }
        );

        // Insert a new Transaction into the Database
        vm.addTransactionClicked.observe(
                this,
                (Boolean clicked) -> {
                    if (clicked) {
                        Transaction transaction = new Transaction(
                                Double.parseDouble(Objects.requireNonNull(vm.amount.getValue())),
                                vm.comment.getValue(),
                                vm.categoryId,
                                vm.date,
                                GlobalSelections.selectedTransactionType
                        );

                        dbHelper.insertNewTransaction(transaction);
                        startActivity(new Intent(this, ListTransactionActivity.class));
                    }
                }
        );

        // Transactions Type buttons are clicked
        transactionTypeSelectionVM.transactionTypeBtnClicked.observe(
                this, (Boolean clicked) -> {
                    if (clicked) {
                        setupCategoryIconsUI(GlobalSelections.selectedTransactionType);
                    }
                }
        );
    }

    protected void setupCategoryIconsUI(TransactionType selectedType) {
        CategoryIcon[] iconModels = selectedType == TransactionType.Expense ?
                EXPENSE_ICON_MODELS : INCOME_ICON_MODELS;

        LinearLayout wrapper = findViewById(R.id.category_ids_wrapper);
        wrapper.removeAllViews();

        LinearLayout linearLayout = newLinearLayout();
        wrapper.addView(linearLayout);

        for (int i = 1; i <= iconModels.length; i++) {
            int categoryId = i - 1;

            linearLayout.addView(
                    new CategoryIconImageView(this,
                            vm,
                            iconModels[categoryId].categoryId,
                            iconModels[categoryId].iconDrawableId
                    )
            );

            TextView tv = new TextView(this);
            tv.setText(iconModels[categoryId].description);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            linearLayout.addView(tv);

            if (i % 3 == 0) {
                linearLayout = newLinearLayout();
                wrapper.addView(linearLayout);
            }

        }
    }

    protected LinearLayout newLinearLayout() {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        );

        return linearLayout;
    }

    protected void setupDbConnection() {
        dbHelper = new TransactionTrackerDbHelper(this);
    }
}
