package com.example.expensetracker.model.view_holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.R;

/**
 * Class representing the ViewHolder for the plain Transaction item showed in the main view list.
 */
public class TransactionVH extends RecyclerView.ViewHolder {
    protected int categoryId = -1;
    public TextView transactionNameTextView;
    public TextView transactionAmountTextView;
    public ImageView iconImageView;

    /**
     * Default constructor
     *
     * @param itemView the View item that the holder will bind to.
     */
    public TransactionVH(@NonNull View itemView) {
        super(itemView);

        transactionNameTextView = itemView.findViewById(R.id.transactionNameTextView);
        transactionAmountTextView = itemView.findViewById(R.id.transactionAmountTextView);
        iconImageView = itemView.findViewById(R.id.iconImageView);


    }

    public void setOnClickListener(int categoryId) {
        itemView.setOnClickListener(new TransactionOnClick(categoryId));
    }
}
