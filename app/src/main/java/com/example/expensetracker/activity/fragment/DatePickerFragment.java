package com.example.expensetracker.activity.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.expensetracker.activity.view_model.CreateTransactionViewModel;

public class DatePickerFragment extends DialogFragment {
    public CreateTransactionViewModel datePickerListener = null;
    private final DatePickerDialog.OnDateSetListener onSelectListener  = new OnSelectListener();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();

        return new DatePickerDialog(
                requireContext(),
                onSelectListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }

    private class OnSelectListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month += 1; // Updating it because of the DatePicker compatibility numeration

            if (datePickerListener != null) {
                datePickerListener.onDateSelected(year, month, dayOfMonth);
            }

            Toast.makeText(
                    view.getContext(),
                    String.format("%s : %s : %s", dayOfMonth, month, year),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}