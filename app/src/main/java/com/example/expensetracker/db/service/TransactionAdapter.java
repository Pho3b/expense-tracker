package com.example.expensetracker.db.service;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.expensetracker.R;
import com.example.expensetracker.db.model.Transaction;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
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
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TRANSACTION_WITH_HEADER) {
            return new TransactionViewHolder(
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

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,, int position) {
        Transaction expense = transactions.get(position);

        if (holder instanceof TransactionViewHolder) {
            TransactionViewHolder h = (TransactionViewHolder) holder;
            h.transactionNameTextView.setText(expense.comment);
            h.transactionAmountTextView.setText(String.format("€%.2f", expense.amount));
        } else {
            TransactionWithHeaderViewHolder h = (TransactionWithHeaderViewHolder) holder;
            h.transactionNameTextView.setText(expense.comment);
            h.transactionAmountTextView.setText(String.format("€%.2f", expense.amount));
            h.transactionHeaderTextView.setText(expense.date.toString());

            holder.bind
        }
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView transactionNameTextView;
        TextView transactionAmountTextView;

        TransactionViewHolder(View itemView) {
            super(itemView);
            transactionNameTextView = itemView.findViewById(R.id.transactionNameTextView);
            transactionAmountTextView = itemView.findViewById(R.id.transactionAmountTextView);
        }
    }

    static class TransactionWithHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView transactionNameTextView;
        TextView transactionAmountTextView;
        TextView transactionHeaderTextView;

        TransactionWithHeaderViewHolder(View itemView) {
            super(itemView);
            transactionNameTextView = itemView.findViewById(R.id.transactionNameTextView);
            transactionAmountTextView = itemView.findViewById(R.id.transactionAmountTextView);
            transactionHeaderTextView = itemView.findViewById(R.id.transaction_header);
        }

    }
}
