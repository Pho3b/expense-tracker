package com.example.expensetracker.activity.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.view_model.CreateEditTransactionVM;

public class DatePickerFragment extends DialogFragment {
    public CreateEditTransactionVM datePickerListener;
    private static DatePickerDialog.OnDateSetListener onSelectListener;

    private static final String ARG_YEAR = "year";
    private static final String ARG_MONTH = "month";
    private static final String ARG_DAY = "day";


    public DatePickerFragment() {
        onSelectListener = new OnSelectListener();
    }

    public DatePickerFragment(int year, int month, int day) {
        this();

        Bundle args = new Bundle();
        args.putInt(ARG_YEAR, year);
        args.putInt(ARG_MONTH, month);
        args.putInt(ARG_DAY, day);
        this.setArguments(args);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get the current date as fallback
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // If arguments are passed, use them instead of the current date
        if (getArguments() != null) {
            year = getArguments().getInt(ARG_YEAR, year);
            month = getArguments().getInt(ARG_MONTH, month);
            day = getArguments().getInt(ARG_DAY, day);
        }

        return new DatePickerDialog(
                requireContext(),
                R.style.DarkDatePickerDialogTheme,
                onSelectListener,
                year,
                month,
                day
        );
    }

    private class OnSelectListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            if (datePickerListener != null) {
                datePickerListener.onDateSelected(year, month, dayOfMonth);
            }
        }
    }
}
