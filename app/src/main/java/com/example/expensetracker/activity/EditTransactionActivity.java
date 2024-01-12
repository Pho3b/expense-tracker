package com.example.expensetracker.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expensetracker.R;

public class EditTransactionActivity extends CreateTransactionActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transaction);

        Toast.makeText(
                this,
                String.format("Category ID: %s", getIntent().getIntExtra("categoryId", -1)),
                Toast.LENGTH_SHORT
        ).show();
    }

}
