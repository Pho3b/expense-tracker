package com.example.expensetracker.service;

import static com.example.expensetracker.model.Constants.EXPENSE_ICON_MODELS;
import static com.example.expensetracker.model.Constants.INCOME_ICON_MODELS;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.expensetracker.R;
import com.example.expensetracker.model.Transaction;
import com.example.expensetracker.enumerator.TransactionType;
import com.example.expensetracker.model.CategoryIcon;
import com.example.expensetracker.model.view_holder.HeaderTransactionVH;
import com.example.expensetracker.model.view_holder.TransactionVH;

public class TransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TRANSACTION = 0;
    private static final int HEADER_TRANSACTION = 1;
    private final List<Transaction> transactions;
    private String lastDate = "";

    public TransactionAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public int getItemViewType(int position) {
        String currentTransactionDate = transactions.get(position).date.toString();

        if (!lastDate.equals(currentTransactionDate)) {
            lastDate = currentTransactionDate;

            return HEADER_TRANSACTION;
        }

        return TRANSACTION;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER_TRANSACTION) {
            return new HeaderTransactionVH(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.transaction_item_with_header,
                            parent,
                            false
                    )
            );
        }

        return new TransactionVH(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.transaction_item,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TransactionVH transactionVH = (TransactionVH) holder;
        Transaction currentTransaction = transactions.get(position);
        CategoryIcon[] iconModels = GlobalSelections.selectedTransactionType == TransactionType.Expense ?
                EXPENSE_ICON_MODELS : INCOME_ICON_MODELS;

        transactionVH.categoryId = currentTransaction.category_id;
        transactionVH.transactionNameTextView.setText(currentTransaction.comment);
        transactionVH.transactionAmountTextView.setText(String.format("€%.2f", currentTransaction.amount));
        transactionVH.iconImageView.setImageResource(
                iconModels[currentTransaction.category_id].iconDrawableId
        );

        if (holder instanceof HeaderTransactionVH) {
            ((HeaderTransactionVH) transactionVH).transactionHeaderTextView.setText(currentTransaction.date.toString());
        }
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }
}
