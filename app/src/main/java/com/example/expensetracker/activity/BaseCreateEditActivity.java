package com.example.expensetracker.activity;

import static com.example.expensetracker.model.Constants.DATE_PICKER_TAG;
import static com.example.expensetracker.model.Constants.EXPENSE_ICON_MODELS;
import static com.example.expensetracker.model.Constants.INCOME_ICON_MODELS;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.fragment.DatePickerFragment;
import com.example.expensetracker.activity.fragment.TransactionTypeSelectionFragment;
import com.example.expensetracker.activity.view_model.CreateEditTransactionVM;
import com.example.expensetracker.activity.view_model.TransactionTypeSelectionVM;
import com.example.expensetracker.activity.view_model.ViewModelsFactory;
import com.example.expensetracker.databinding.ActivityCreateEditTransactionBinding;
import com.example.expensetracker.db.TransactionTrackerDbHelper;
import com.example.expensetracker.enumerator.TransactionType;
import com.example.expensetracker.model.CategoryIcon;
import com.example.expensetracker.service.GlobalSelections;
import com.example.expensetracker.ui.model.CategoryIconView;


public class BaseCreateEditActivity extends AppCompatActivity {
    protected TransactionTypeSelectionVM transactionTypeSelectionVM;
    protected TransactionTrackerDbHelper db;
    protected CreateEditTransactionVM vm;
    protected Button addEditBtn;
    protected ViewGroup categoryIdsWrapper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setting content view
        setContentView(R.layout.activity_create_edit_transaction);

        // Adds the TransactionTypeSelectionFragment to the activity
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.type_selection_fragment_container, new TransactionTypeSelectionFragment());
        transaction.commit();

        transactionTypeSelectionVM = new ViewModelProvider(this, new ViewModelsFactory(getApplication())).
                get(TransactionTypeSelectionVM.class);
        db = new TransactionTrackerDbHelper(this);

        initViewModels();
        setupObservers();
        setupCategoryIconsUI(GlobalSelections.selectedTransactionType);
        setUpLayoutChangeListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GlobalSelections.updateSelectedTransactionType(getApplication(), vm.expenseBackground, vm.incomeBackground);
    }

    /**
     * Binds the current Activity to its ViewModel
     */
    protected void initViewModels() {
        vm = new ViewModelProvider(this, new ViewModelsFactory(getApplication())).get(CreateEditTransactionVM.class);

        ActivityCreateEditTransactionBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_create_edit_transaction);
        binding.setViewModel(vm);
        binding.setLifecycleOwner(this);

        addEditBtn = findViewById(R.id.add_edit_transaction_btn);
        categoryIdsWrapper = findViewById(R.id.category_ids_wrapper);
    }

    protected LinearLayout newLinearLayout() {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        );

        return linearLayout;
    }

    protected void setupObservers() {
        // Transactions Type buttons are clicked
        transactionTypeSelectionVM.transactionTypeBtnClicked.observe(
                this, (Boolean clicked) -> {
                    if (clicked) {
                        setupCategoryIconsUI(GlobalSelections.selectedTransactionType);
                        vm.selectedCategoryId.setValue(0);
                    }
                }
        );

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

        vm.amount.observe(this, (String value) -> addEditBtn.setEnabled(!value.isEmpty()));

        vm.selectedCategoryId.observe(
                this,
                (Integer categoryId) -> {
                    vm.updateCategoryIconsBackground(categoryIdsWrapper);
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
                    new CategoryIconView(this, vm, iconModels[categoryId].categoryId, iconModels[categoryId].iconDrawableId)
            );

            TextView tv = new TextView(this);
            tv.setText(iconModels[categoryId].description);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setTextSize(16);
            linearLayout.addView(tv);

            if (i % 3 == 0) {
                linearLayout = newLinearLayout();
                wrapper.addView(linearLayout);
            }

        }
    }

    private void setUpLayoutChangeListener() {
        final View rootLayout = findViewById(android.R.id.content);

        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                () -> {
                    Rect r = new Rect();
                    rootLayout.getWindowVisibleDisplayFrame(r);
                    int screenHeight = rootLayout.getRootView().getHeight();
                    int keypadHeight = (screenHeight - r.bottom) - 100;
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) addEditBtn.getLayoutParams();

                    if (keypadHeight > screenHeight * 0.15) {
                        params.bottomMargin = keypadHeight;
                    } else {
                        params.bottomMargin = 0;
                    }

                    addEditBtn.setLayoutParams(params);
                    addEditBtn.bringToFront();
                }
        );
    }

}
