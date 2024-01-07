package com.example.expensetracker.service;

import static com.example.expensetracker.model.Constants.EXPENSE_ICON_MODELS;
import static com.example.expensetracker.model.Constants.INCOME_ICON_MODELS;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.CreateTransactionActivity;
import com.example.expensetracker.activity.EditTransactionActivity;
import com.example.expensetracker.model.Transaction;
import com.example.expensetracker.enums.TransactionType;
import com.example.expensetracker.model.CategoryIcon;

public class TransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TRANSACTION = 0;
    private static final int TRANSACTION_WITH_HEADER = 1;
    private final List<Transaction> transactions;
    private String lastDate = "";


    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView transactionNameTextView;
        TextView transactionAmountTextView;
        ImageView iconImageView;
        int categoryId;

        TransactionViewHolder(View itemView) {
            super(itemView);
            transactionNameTextView = itemView.findViewById(R.id.transactionNameTextView);
            transactionAmountTextView = itemView.findViewById(R.id.transactionAmountTextView);
            iconImageView = itemView.findViewById(R.id.iconImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context itemCtx = itemView.getContext();
                    Intent intent = new Intent(itemCtx, EditTransactionActivity.class);
                    intent.putExtra("categoryId", categoryId);

                    itemCtx.startActivity(intent);
                }
            });
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
        CategoryIcon[] iconModels = GlobalSelections.selectedTransactionType == TransactionType.Expense ?
                EXPENSE_ICON_MODELS : INCOME_ICON_MODELS;


        if (holder instanceof TransactionViewHolder) {

            TransactionViewHolder transaction = (TransactionViewHolder) holder;
            transaction.transactionNameTextView.setText(currentTransaction.comment);
            transaction.transactionAmountTextView.setText(String.format("€%.2f", currentTransaction.amount));
            transaction.iconImageView.setImageResource(
                    iconModels[currentTransaction.category_id].iconDrawableId
            );
            transaction.categoryId = currentTransaction.category_id;
        } else {
            TransactionWithHeaderViewHolder transaction = (TransactionWithHeaderViewHolder) holder;
            transaction.transactionNameTextView.setText(currentTransaction.comment);
            transaction.transactionAmountTextView.setText(String.format("€%.2f", currentTransaction.amount));
            transaction.transactionHeaderTextView.setText(currentTransaction.date.toString());
            transaction.iconImageView.setImageResource(
                    iconModels[currentTransaction.category_id].iconDrawableId
            );
        }
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }
}
