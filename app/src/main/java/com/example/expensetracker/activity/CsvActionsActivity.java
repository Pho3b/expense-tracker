package com.example.expensetracker.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.fragment.ActivityHeaderFragment;
import com.example.expensetracker.databinding.CsvActionsActivityBinding;
import com.example.expensetracker.db.TransactionTrackerDbHelper;
import com.google.android.material.navigation.NavigationView;

public class CsvActionsActivity extends AppCompatActivity {
    private TransactionTrackerDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new TransactionTrackerDbHelper(this);

        initializeUI();
    }

    private void initializeUI() {
        // Initializes the Activity ViewModels
        //        ViewModelProvider vmProvider = new ViewModelProvider(this, new ViewModelsFactory(getApplication()));
        //        vm = vmProvider.get(ListTransactionVM.class);
        //        transactionTypeSelectionVM = vmProvider.get(TransactionTypeSelectionVM.class);

        // Binding the ViewModel and the Activity to the layout
        CsvActionsActivityBinding b = DataBindingUtil.setContentView(this, R.layout.csv_actions_activity);
        //b.setViewModel(vm);
        b.setLifecycleOwner(this);

        // Initialize the Activity UI elements
        DrawerLayout drawer = findViewById(R.id.drawer);

        // Adds the TransactionTypeSelectionFragment to the activity
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.activity_header_fragment_container, new ActivityHeaderFragment(drawer)).
                commit();

        initNavigationDrawer(this, drawer);
    }

    private void initNavigationDrawer(Context ctx, DrawerLayout drawer) {
        NavigationView navView = findViewById(R.id.navigationView);

        navView.setNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.nav_transactions) {
                startActivity(new Intent(ctx, ListTransactionActivity.class));
            }

            drawer.close();
            return true;
        });
    }
}
