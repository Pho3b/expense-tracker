package com.example.expensetracker.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.R;
import com.example.expensetracker.ui.expenses.Expense;
import com.example.expensetracker.ui.expenses.ExpenseAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExpenseListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExpenseAdapter expenseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);

        recyclerView = findViewById(R.id.recyclerView);

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
        // Add more expenses as needed

        expenseAdapter = new ExpenseAdapter(expenses);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(expenseAdapter);
    }
}
