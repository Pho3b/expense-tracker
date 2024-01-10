package com.example.expensetracker.model.view_holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.expensetracker.R;

/**
 * Class that represents the ViewHolder for the first Transaction of every day.
 * These transactions have an header showing the date when they have been added.
 */
public class HeaderTransactionVH extends TransactionVH {
    public TextView transactionHeaderTextView;

    /**
     * Default constructor
     *
     * @param itemView the View item that the holder will bind to.
     */
    public HeaderTransactionVH(@NonNull View itemView) {
        super(itemView);

        transactionHeaderTextView = itemView.findViewById(R.id.transaction_header);
    }
}
