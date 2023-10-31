package com.example.expensetracker.ui.model;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.view_model.CreateTransactionViewModel;

public class CategoryIconImageView extends androidx.appcompat.widget.AppCompatImageView {
    public int categoryId;

    private final int dpPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
    private final int  dpIconSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics());
    private final int  dpMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());

    public CategoryIconImageView(
            @NonNull Context context,
            CreateTransactionViewModel viewModel,
            int categoryId,
            int iconDrawableId
    ) {
        super(context);

        this.categoryId = categoryId;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpIconSize, dpIconSize);

        layoutParams.setMargins(dpMargin, dpMargin, dpMargin, 2);
        setLayoutParams(layoutParams);
        setPadding(dpPadding, dpPadding, dpPadding, dpPadding);

        Drawable bg = ContextCompat.getDrawable(context, R.drawable.ci_rounded_background);
        assert bg != null;
        bg.setColorFilter(ContextCompat.getColor(context, R.color.floating_blue), PorterDuff.Mode.MULTIPLY);
        setBackgroundDrawable(bg);
        setImageResource(iconDrawableId);
        setOnClickListener(viewModel::onIconClicked);
    }

    public CategoryIconImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CategoryIconImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
