package com.example.expensetracker.activity;

import static com.example.expensetracker.model.Constants.ET_LOGS_TAG_DEV;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.fragment.ActivityHeaderFragment;
import com.example.expensetracker.databinding.CsvActionsActivityBinding;
import com.example.expensetracker.db.TransactionTrackerDbHelper;
import com.example.expensetracker.service.CSVImportService;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.ComponentActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class CsvActionsActivity extends AppCompatActivity {
//    private static final int PICK_CSV_FILE = 1;
    private TransactionTrackerDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new TransactionTrackerDbHelper(this);

        initializeUI();
    }

    // Step 1: Register the activity result launcher
    private final ActivityResultLauncher<Intent> startActivityForResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // Step 3: Handle the result
                            Intent data = result.getData();
                            Log.i(ET_LOGS_TAG_DEV, "1. inside the first step");

                            if (data != null) {
                                Log.i(ET_LOGS_TAG_DEV, "2. second step");
                                CSVImportService csvImportService = new CSVImportService(this);

                                Uri fileUri = data.getData();

                                try {
                                    InputStream is = getContentResolver().openInputStream(fileUri);
                                    csvImportService.importCSV(is);
                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException(e);
                                }

                                Toast.makeText(this, "Selected File: " + fileUri, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(this, "Action canceled", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

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

        ImageButton openActivityButton = findViewById(R.id.btn_import_csv);
        openActivityButton.setOnClickListener(v -> {
            // Step 2: Launch the second activity
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");  // Allows all files if necessary
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResultLauncher.launch(intent);
        });
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
