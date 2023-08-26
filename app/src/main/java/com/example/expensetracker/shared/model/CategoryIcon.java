package com.example.expensetracker.shared.model;

import android.app.ActionBar;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.view_model.CreateTransactionViewModel;

import java.util.Random;

public class CategoryIcon extends androidx.appcompat.widget.AppCompatImageView {
    private int iconId;

    public CategoryIcon(@NonNull Context context, int tagId) {
        super(context);

        int dpPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        int dpIconSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dpIconSize, dpIconSize);

        setLayoutParams(lp);
        setPadding(dpPadding, dpPadding, dpPadding, dpPadding);
        setImageResource(R.drawable.ic_menu_slideshow);
        iconId = tagId;
        setOnClickListener(
                v -> Toast.makeText(
                        getContext(),
                        Integer.toString(iconId),
                        Toast.LENGTH_SHORT
                ).show()
        );
    }

    public CategoryIcon(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CategoryIcon(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
