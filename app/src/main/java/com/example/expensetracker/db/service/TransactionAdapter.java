package com.example.expensetracker.db.service;

import static com.example.expensetracker.shared.Constants.EXPENSE_CATEGORY_ICON_MODELS;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.expensetracker.R;
import com.example.expensetracker.db.model.Transaction;

public class TransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Transaction> transactions;
    private static final int TRANSACTION = 0;
    private static final int TRANSACTION_WITH_HEADER = 1;

    private String lastDate = "";

    public TransactionAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public int getItemViewType(int position) {
        String currentTransactionDate = transactions.get(position).date.toString();

        if (!lastDate.equals(currentTransactionDate)) {
            lastDate = currentTransactionDate;
            return TRANSACTION_WITH_HEADER;
        }

        return TRANSACTION;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TRANSACTION_WITH_HEADER) {
            return new TransactionWithHeaderViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.transaction_item_with_header,
                            parent,
                            false
                    )
            );
        }

        return new TransactionViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.transaction_item,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Transaction currentTransaction = transactions.get(position);

        if (holder instanceof TransactionViewHolder) {
            TransactionViewHolder transaction = (TransactionViewHolder) holder;
            transaction.transactionNameTextView.setText(currentTransaction.comment);
            transaction.transactionAmountTextView.setText(String.format("€%.2f", currentTransaction.amount));
            transaction.iconImageView.setImageResource(
                    EXPENSE_CATEGORY_ICON_MODELS[currentTransaction.category_id].iconDrawableId
            );
        } else {
            TransactionWithHeaderViewHolder transaction = (TransactionWithHeaderViewHolder) holder;
            transaction.transactionNameTextView.setText(currentTransaction.comment);
            transaction.transactionAmountTextView.setText(String.format("€%.2f", currentTransaction.amount));
            transaction.transactionHeaderTextView.setText(currentTransaction.date.toString());
            transaction.iconImageView.setImageResource(
                    EXPENSE_CATEGORY_ICON_MODELS[currentTransaction.category_id].iconDrawableId
            );
        }
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView transactionNameTextView;
        TextView transactionAmountTextView;
        ImageView iconImageView;

        TransactionViewHolder(View itemView) {
            super(itemView);
            transactionNameTextView = itemView.findViewById(R.id.transactionNameTextView);
            transactionAmountTextView = itemView.findViewById(R.id.transactionAmountTextView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
        }
    }

    static class TransactionWithHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView transactionNameTextView;
        TextView transactionAmountTextView;
        TextView transactionHeaderTextView;
        ImageView iconImageView;

        TransactionWithHeaderViewHolder(View itemView) {
            super(itemView);
            transactionNameTextView = itemView.findViewById(R.id.transactionNameTextView);
            transactionAmountTextView = itemView.findViewById(R.id.transactionAmountTextView);
            transactionHeaderTextView = itemView.findViewById(R.id.transaction_header);
            iconImageView = itemView.findViewById(R.id.iconImageView);
        }

    }
}
