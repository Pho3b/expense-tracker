package com.example.expensetracker.model.view_holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.expensetracker.activity.EditTransactionActivity;

public class TransactionOnClick implements View.OnClickListener {
    public static final String CLICKED_TRANSACTION_ID = "_id";
    public int id;

    protected TransactionOnClick(int id) {
        this.id = id;
    }

    @Override
    public void onClick(View v) {
        Context itemCtx = v.getContext();
        Intent intent = new Intent(itemCtx, EditTransactionActivity.class);
        intent.putExtra(CLICKED_TRANSACTION_ID, this.id);

        itemCtx.startActivity(intent);
    }
}
