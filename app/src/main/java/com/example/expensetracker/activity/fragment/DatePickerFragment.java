package com.example.expensetracker.activity.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.expensetracker.activity.view_model.CreateTransactionViewModel;

public class DatePickerFragment extends DialogFragment {
    public CreateTransactionViewModel datePickerListener = null;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();

        return new DatePickerDialog(
                requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    if (datePickerListener != null) {
                        datePickerListener.onDateSelected(selectedYear, selectedMonth, selectedDay);
                    }
                    Toast.makeText(
                            view.getContext(),
                            String.format("%s : %s : %s", selectedDay, selectedMonth, selectedYear),
                            Toast.LENGTH_SHORT
                    ).show();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }
}
