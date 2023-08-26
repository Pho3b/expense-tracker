package com.example.expensetracker.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class DatePickerFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();

        return new DatePickerDialog(
                requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> Toast.makeText(
                        view.getContext(),
                        String.format("%s : %s : %s", selectedDay, selectedMonth, selectedYear),
                        Toast.LENGTH_SHORT
                ).show(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }
}
