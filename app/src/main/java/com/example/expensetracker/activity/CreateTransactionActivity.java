package com.example.expensetracker.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.view_model.CreateTransactionViewModel;
import com.example.expensetracker.databinding.ActivityCreateTransactionBinding;
import com.example.expensetracker.activity.fragment.DatePickerFragment;
import com.example.expensetracker.shared.model.CategoryIcon;
import com.example.expensetracker.shared.service.GlobalService;

public class CreateTransactionActivity extends AppCompatActivity {
    protected CreateTransactionViewModel viewModel;
    private static final String DATE_PICKER_TAG = "datePicker";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transaction);

        bindViewModel();
        setupObservers();
        viewModel.amount.setValue("22");

        LinearLayout wrapper = findViewById(R.id.category_ids_wrapper);
        LinearLayout linearLayout = getNewLinearLayout();
        int test = 22;

        for (int i = 0; i < test; i++) {
            if (i > 0 && i % 3 == 0) {
                wrapper.addView(linearLayout);
                linearLayout = getNewLinearLayout();
            }

            linearLayout.addView(new CategoryIcon(this, i));
        }
    }

    private LinearLayout getNewLinearLayout() {
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

    @Override
    protected void onResume() {
        super.onResume();

        GlobalService.updateSelectedTransactionType(
                getApplication(),
                viewModel.expenseBackground,
                viewModel.incomeBackground
        );
    }

    /**
     * Binds the current Activity to its ViewModel
     */
    private void bindViewModel() {
        viewModel = new CreateTransactionViewModel(getApplication());
        ActivityCreateTransactionBinding binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_create_transaction
        );
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

    private void setupObservers() {
        // Button responsible to open the 'Select Custom Date' Date-picker Fragment
        viewModel.openDatePickerFragmentClicked.observe(
                this,
                (Boolean clicked) -> {
                    if (clicked) {
                        DialogFragment datePickerFragment = new DatePickerFragment();
                        datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);
                    }
                }
        );
    }
}
