package com.example.expensetracker.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.R;
import com.example.expensetracker.activities.click_handlers.ListTransactionClickHandler;
import com.example.expensetracker.shared.enums.TransactionType;
import com.example.expensetracker.ui.expenses.Expense;
import com.example.expensetracker.ui.expenses.ExpenseAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListTransactionActivity extends AppCompatActivity {

    public TransactionType selectedTransactionType;
    public TextView incomeTypeTxt;
    public TextView expenseTypeTxt;
    private ExpenseAdapter expenseAdapter;
    private final Map<Integer, Runnable> onClickActions = new HashMap<>();
    private RecyclerView recyclerView;
    private TextView monthAmountView;
    private TextView monthYearTitleView;
    private FloatingActionButton addTransactionBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions_list);

        findViews();
        setupViewValues();
        associateViewListeners();
    }

    private void findViews() {
        expenseTypeTxt = findViewById(R.id.expense_type_btn);
        incomeTypeTxt = findViewById(R.id.income_type_btn);
        recyclerView = findViewById(R.id.recyclerView);
        monthAmountView = findViewById(R.id.month_amount_txt);
        monthYearTitleView = findViewById(R.id.month_year_txt);
        addTransactionBtn = findViewById(R.id.add_transaction_btn);
    }

    private void setupViewValues() {
        LocalDate currentDate = LocalDate.now();

        selectedTransactionType = TransactionType.Expense;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ExpenseAdapter(this.retrieveExpenses()));
        monthAmountView.setText("€198");
        expenseTypeTxt.setBackground(
                ContextCompat.getDrawable(this, R.drawable.rounded_background)
        );
        monthYearTitleView.setText(
                getString(R.string.month_year_title, currentDate.getMonth(), currentDate.getYear())
        );
    }

    private void associateViewListeners() {
        ListTransactionClickHandler handler = new ListTransactionClickHandler(this);

        onClickActions.put(expenseTypeTxt.getId(), () -> handler.handleExpenseTypeClick(expenseTypeTxt));
        onClickActions.put(incomeTypeTxt.getId(), () -> handler.handleIncomeTypeClick(incomeTypeTxt));
        onClickActions.put(addTransactionBtn.getId(), handler::handleAddTransactionClick);

        expenseTypeTxt.setOnClickListener(onClickListener);
        incomeTypeTxt.setOnClickListener(onClickListener);
        addTransactionBtn.setOnClickListener(onClickListener);
    }

    private final View.OnClickListener onClickListener = v -> {
        Runnable action = onClickActions.get(v.getId());

        if (action != null) {
            action.run();
        }
    };

    private List<Expense> retrieveExpenses() {
        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense("Groceries test lkdfjsalfkjsadlfjaslkdfjaslkdfjalsdfkfdl", 50.0));
        expenses.add(new Expense("Dinner", 30.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Restaurant", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Book", 12.0));

        return expenses;
    }
}
