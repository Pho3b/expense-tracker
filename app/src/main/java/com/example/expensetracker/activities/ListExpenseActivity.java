package com.example.expensetracker.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.R;
import com.example.expensetracker.db.ExpensesDbHelper;
import com.example.expensetracker.ui.expenses.Expense;
import com.example.expensetracker.ui.expenses.ExpenseAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ListExpenseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExpenseAdapter expenseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ExpenseAdapter(this.retrieveExpenses()));

        ExpensesDbHelper dbHelper = new ExpensesDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Attaching Listeners
        attachOnClickListeners(db, dbHelper);

        // Setting View Texts
        TextView monthYearTitleView = findViewById(R.id.month_year_txt);
        LocalDate currentDate = LocalDate.now();
        monthYearTitleView.setText(
                getString(R.string.month_year_title, currentDate.getMonth(), currentDate.getYear())
        );

        TextView monthAmountView = findViewById(R.id.month_amount_txt);
        monthAmountView.setText("€198");

        TextView expenseCategoryTxt = findViewById(R.id.expense_category_txt);
        expenseCategoryTxt.setBackground(
                ContextCompat.getDrawable(this, R.drawable.rounded_background)
        );
    }

    private void attachOnClickListeners(SQLiteDatabase db, ExpensesDbHelper dbHelper) {

        FloatingActionButton addExpenseBtn = findViewById(R.id.addExpenseBtn);

        addExpenseBtn.setOnClickListener(v -> {
            Log.d(this.getLocalClassName(), "Starting the second activity");
            Intent intent = new Intent(
                    ListExpenseActivity.this,
                    CreateTransactionActivity.class
            );
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
