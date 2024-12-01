package com.example.expensetracker.activity;

import static com.example.expensetracker.model.Constants.EXPENSE_ICON_MODELS;
import static com.example.expensetracker.model.Constants.INCOME_ICON_MODELS;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.fragment.TransactionTypeSelectionFragment;
import com.example.expensetracker.activity.view_model.CreateEditTransactionVM;
import com.example.expensetracker.activity.view_model.TransactionTypeSelectionVM;
import com.example.expensetracker.activity.view_model.ViewModelsFactory;
import com.example.expensetracker.databinding.ActivityCreateEditTransactionBinding;
import com.example.expensetracker.db.TransactionTrackerDbHelper;
import com.example.expensetracker.enumerator.TransactionType;
import com.example.expensetracker.model.CategoryIcon;
import com.example.expensetracker.service.Global;
import com.example.expensetracker.ui.model.CategoryIconView;


public class BaseCreateEditActivity extends AppCompatActivity {
    protected TransactionTypeSelectionVM ttVm;
    protected TransactionTrackerDbHelper db;
    protected CreateEditTransactionVM vm;
    protected Button addEditBtn;
    protected ViewGroup categoryIdsWrapper;
    protected View deleteBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new TransactionTrackerDbHelper(this);

        initializeUI();
        setUpLayoutChangeListener();
        observeTransactionTypeButton();
        observeAmountInputField();
        observeCategoryIdIcons();
        setupCategoryIconsUI(Global.selectedTransactionType);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Global.updateSelectedTransactionType(getApplication(), vm.expenseBackground, vm.incomeBackground);
    }

    private void initializeUI() {
        // Initializes the Activity ViewModels
        vm = new ViewModelProvider(this, new ViewModelsFactory(getApplication())).get(CreateEditTransactionVM.class);
        ttVm = new ViewModelProvider(this, new ViewModelsFactory(getApplication())).get(TransactionTypeSelectionVM.class);

        // Binding the ViewModel and the Activity to the layout and
        ActivityCreateEditTransactionBinding b = DataBindingUtil.setContentView(this, R.layout.activity_create_edit_transaction);
        b.setViewModel(vm);
        b.setLifecycleOwner(this);

        // Initialize the Activity UI elements
        addEditBtn = findViewById(R.id.add_edit_transaction_btn);
        categoryIdsWrapper = findViewById(R.id.category_ids_wrapper);
        deleteBtn = findViewById(R.id.delete_transaction);

        // Adds the TransactionTypeSelectionFragment to the activity
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.type_selection_fragment_container, new TransactionTypeSelectionFragment()).
                commit();
    }

    private void setupCategoryIconsUI(TransactionType selectedType) {
        CategoryIcon[] iconModels = selectedType == TransactionType.Expense ? EXPENSE_ICON_MODELS : INCOME_ICON_MODELS;
        LinearLayout wrapper = findViewById(R.id.category_ids_wrapper);
        LinearLayout linearLayout = newLinearLayout();

        wrapper.removeAllViews();
        wrapper.addView(linearLayout);

        for (int i = 1; i <= iconModels.length; i++) {
            int categoryId = i - 1;

            linearLayout.addView(
                    new CategoryIconView(this, vm, iconModels[categoryId].categoryId, iconModels[categoryId].iconDrawableId)
            );

            TextView textView = new TextView(this);
            textView.setText(iconModels[categoryId].description);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setTextSize(16);
            linearLayout.addView(textView);

            if (i % 3 == 0) {
                linearLayout = newLinearLayout();
                wrapper.addView(linearLayout);
            }
        }
    }

    private LinearLayout newLinearLayout() {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        );

        return linearLayout;
    }

    private void observeTransactionTypeButton() {
        ttVm.transactionTypeBtnClicked.observe(
                this, (Boolean clicked) -> {
                    if (clicked) {
                        setupCategoryIconsUI(Global.selectedTransactionType);
                        vm.selectedCategoryId.setValue(0);
                    }
                }
        );
    }

    private void observeAmountInputField() {
        vm.amount.observe(this, (String value) -> addEditBtn.setEnabled(!value.isEmpty()));
    }

    private void observeCategoryIdIcons() {
        vm.selectedCategoryId.observe(
                this,
                (Integer categoryId) -> {
                    vm.updateCategoryIconsBackground(categoryIdsWrapper);
                }
        );
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
