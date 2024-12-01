package com.example.expensetracker.model.view_holder;

import static com.example.expensetracker.model.Constants.CLICKED_TRANS_ID_EXTRA;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.expensetracker.activity.EditTransactionActivity;

public class TransactionOnClick implements View.OnClickListener {
    public int id;

    protected TransactionOnClick(int id) {
        this.id = id;
    }

    @Override
    public void onClick(View v) {
        Context itemCtx = v.getContext();
        Intent intent = new Intent(itemCtx, EditTransactionActivity.class);
        intent.putExtra(CLICKED_TRANS_ID_EXTRA, this.id);

        itemCtx.startActivity(intent);
    }
}
