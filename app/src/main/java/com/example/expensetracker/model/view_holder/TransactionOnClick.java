package com.example.expensetracker.model.view_holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.expensetracker.activity.EditTransactionActivity;

public class TransactionOnClick implements View.OnClickListener {
    private final int categoryId;

    protected TransactionOnClick(int categoryId) {
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
