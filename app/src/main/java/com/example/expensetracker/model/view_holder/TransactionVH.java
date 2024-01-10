package com.example.expensetracker.model.view_holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.expensetracker.R;

public class TransactionVH extends TransactionViewHolder {
    public int categoryId = -1;
    public TextView transactionNameTextView;
    public TextView transactionAmountTextView;
    public ImageView iconImageView;

    public TransactionVH(@NonNull View itemView) {
        super(itemView);

        transactionNameTextView = itemView.findViewById(R.id.transactionNameTextView);
        transactionAmountTextView = itemView.findViewById(R.id.transactionAmountTextView);
        iconImageView = itemView.findViewById(R.id.iconImageView);
    }
}
