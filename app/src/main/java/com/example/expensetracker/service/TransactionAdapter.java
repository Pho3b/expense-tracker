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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.EditTransactionActivity;
import com.example.expensetracker.model.Transaction;
import com.example.expensetracker.enumerator.TransactionType;
import com.example.expensetracker.model.CategoryIcon;
import com.example.expensetracker.model.view_holder.HeaderTransactionVH;
import com.example.expensetracker.model.view_holder.TransactionVH;
import com.example.expensetracker.model.view_holder.TransactionViewHolder;

public class TransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TRANSACTION = 0;
    private static final int TRANSACTION_WITH_HEADER = 1;
    private final List<Transaction> transactions;
    private String lastDate = "";

    public TransactionAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    private static class ItemOnClick implements View.OnClickListener {
        int categoryId;

        protected ItemOnClick(int categoryId) {
            this.categoryId = categoryId;
        }

        @Override
        public void onClick(View v) {
            Context itemCtx = v.getContext();
            Intent intent = new Intent(itemCtx, EditTransactionActivity.class);
            intent.putExtra("categoryId", this.categoryId);

            itemCtx.startActivity(intent);
        }
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
        TransactionViewHolder transactionVH;
        Transaction currentTransaction = transactions.get(position);
        CategoryIcon[] iconModels = GlobalSelections.selectedTransactionType == TransactionType.Expense ?
                EXPENSE_ICON_MODELS : INCOME_ICON_MODELS;

        if (holder instanceof HeaderTransactionVH) {
            transactionVH = (HeaderTransactionVH) holder;
            transactionVH.transactionHeaderTextView.setText(currentTransaction.date.toString());
        } else {
            transactionVH = (TransactionVH) holder;
        }

        transactionVH.categoryId = currentTransaction.category_id;
        transactionVH.transactionNameTextView.setText(currentTransaction.comment);
        transactionVH.transactionAmountTextView.setText(String.format("€%.2f", currentTransaction.amount));
        transactionVH.iconImageView.setImageResource(
                iconModels[currentTransaction.category_id].iconDrawableId
        );
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }
}
