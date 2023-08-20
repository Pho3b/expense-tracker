package com.example.expensetracker.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.R;
import com.example.expensetracker.db.ExpensesDbHelper;
import com.example.expensetracker.enums.TransactionType;
import com.example.expensetracker.models.Constants;
import com.example.expensetracker.ui.expenses.Expense;
import com.example.expensetracker.ui.expenses.ExpenseAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ListExpenseActivity extends AppCompatActivity {

    private ExpenseAdapter expenseAdapter;

    // View Element References
    private RecyclerView recyclerView;
    private TextView expenseTypeTxt;
    private TextView incomeTypeTxt;

    private TextView monthAmountView;
    private TextView monthYearTitleView;

    // View Related Data
    private TransactionType selectedTransactionType = TransactionType.Expense;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions_list);

        LocalDate currentDate = LocalDate.now();
        expenseTypeTxt = findViewById(R.id.expense_type_btn);
        incomeTypeTxt = findViewById(R.id.income_type_btn);
        recyclerView = findViewById(R.id.recyclerView);
        monthAmountView = findViewById(R.id.month_amount_txt);
        monthYearTitleView = findViewById(R.id.month_year_txt);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ExpenseAdapter(this.retrieveExpenses()));
        monthAmountView.setText("€198");
        expenseTypeTxt.setBackground(
                ContextCompat.getDrawable(this, R.drawable.rounded_background)
        );
        monthYearTitleView.setText(
                getString(R.string.month_year_title, currentDate.getMonth(), currentDate.getYear())
        );

//        ExpensesDbHelper dbHelper = new ExpensesDbHelper(this);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();

        attachOnClickListeners();
    }

    private void attachOnClickListeners() {

        FloatingActionButton addExpenseBtn = findViewById(R.id.add_expense_btn);

        addExpenseBtn.setOnClickListener(v -> {
            Log.d(this.getLocalClassName(), "Starting the second activity");
            Intent intent = new Intent(
                    ListExpenseActivity.this,
                    CreateTransactionActivity.class
            );
            intent.putExtra(Constants.TRANSACTION_TYPE_EXTRA, selectedTransactionType);
            startActivity(intent);

//            boolean res = dbHelper.insertNewExpense(db, new Expense("Cinema Test", 11));
//            String resMsg = res ? "Insertion Success" : "Insertion Error";
//
//            Toast.makeText(
//                    ListExpenseActivity.this,
//                    resMsg,
//                    Toast.LENGTH_SHORT
//            ).show();
        });

        expenseTypeTxt.setOnClickListener(
                btn -> {
                    selectedTransactionType = TransactionType.Expense;
                    TextView expenseTypeBtn = (TextView) btn;
                    expenseTypeBtn.setBackground(
                            ContextCompat.getDrawable(this, R.drawable.rounded_background)
                    );
                    incomeTypeTxt.setBackground(null);
                }
        );

        incomeTypeTxt.setOnClickListener(
                btn -> {
                    selectedTransactionType = TransactionType.Income;
                    TextView incomeTypeBtn = (TextView) btn;
                    incomeTypeBtn.setBackground(
                            ContextCompat.getDrawable(this, R.drawable.rounded_background)
                    );
                    expenseTypeTxt.setBackground(null);
                }
        );
    }

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
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Gas", 54.0));
        expenses.add(new Expense("Book", 12.0));

        return expenses;
    }
}
