package com.example.expensetracker.service;

import static com.example.expensetracker.model.Constants.EXPENSE_ICON_MODELS;
import static com.example.expensetracker.model.Constants.INCOME_ICON_MODELS;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.expensetracker.R;
import com.example.expensetracker.model.Constants;
import com.example.expensetracker.model.Transaction;
import com.example.expensetracker.enumerator.TransactionType;
import com.example.expensetracker.model.CategoryIcon;
import com.example.expensetracker.model.view_holder.HeaderTransactionVH;
import com.example.expensetracker.model.view_holder.TransactionVH;


public class TransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TRANSACTION = 0;
    private static final int HEADER_TRANSACTION = 1;
    private final List<Transaction> transactions;
    private String lastDate;

    /**
     * Default constructor
     *
     * @param transactions are the list of transactions that will be adapted for the Recycles View.
     */
    public TransactionAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
        this.lastDate = "";
    }

    /**
     * Called for every item of the Adapter returns the current item initialize View Holder.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return The current item ViewHolder
     */
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

    /**
     * Called for every adapter item, it is triggered after the onCreateViewHolder() in order to
     * initialize the current ViewHolder properties.
     *
     * @param viewHolder The ViewHolder which should be updated to represent the contents of the
     *                   item at the given position in the data set.
     * @param position   The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        TransactionVH transactionVH = (TransactionVH) viewHolder;
        Transaction currentTransaction = transactions.get(position);
        CategoryIcon[] iconModels = GlobalSelections.selectedTransactionType == TransactionType.Expense ?
                EXPENSE_ICON_MODELS : INCOME_ICON_MODELS;
        int iconId = currentTransaction.category_id < iconModels.length ? currentTransaction.category_id : 0;

        transactionVH.setOnClickListener(currentTransaction.id);
        transactionVH.transactionNameTextView.setText(currentTransaction.comment);
        transactionVH.transactionAmountTextView.setText(String.format("€%.2f", currentTransaction.amount));
        transactionVH.iconImageView.setImageResource(iconModels[iconId].iconDrawableId);

        if (viewHolder instanceof HeaderTransactionVH) {
            ((HeaderTransactionVH) transactionVH).transactionHeaderTextView.setText(currentTransaction.date.toString());
        }
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        this.lastDate = "";
    }

    /**
     * The method that gets called internally and returns the current item type as a parameter
     * to the instance onCreateViewHolder() method.
     *
     * @param position position to query
     * @return an integer representing the current item view type.
     */
    @Override
    public int getItemViewType(int position) {
        String currentTransactionDate = transactions.get(position).date.toString();

        if (!lastDate.equals(currentTransactionDate)) {
            lastDate = currentTransactionDate;

            return HEADER_TRANSACTION;
        }

        return TRANSACTION;
    }

    /**
     * @see RecyclerView.Adapter#getItemCount()
     */
    @Override
    public int getItemCount() {
        return transactions.size();
    }
}
