package com.example.expensetracker.model.view_holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.expensetracker.R;

public class HeaderTransactionVH extends TransactionVH {
    public TextView transactionHeaderTextView;

    public HeaderTransactionVH(@NonNull View itemView) {
        super(itemView);

        transactionHeaderTextView = itemView.findViewById(R.id.transaction_header);
    }
}
