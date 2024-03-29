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
import com.example.expensetracker.activity.view_model.CreateTransactionVM;

public class CategoryIconImageView extends androidx.appcompat.widget.AppCompatImageView {
    public int categoryId;
    private static int[] dpMeasures = null;


    public CategoryIconImageView(
            @NonNull Context context,
            CreateTransactionVM viewModel,
            int categoryId,
            int iconDrawableId
    ) {
        super(context);
        this.categoryId = categoryId;
        initDensityPixelMeasures();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpMeasures[1], dpMeasures[1]);
        layoutParams.setMargins(dpMeasures[2], dpMeasures[2], dpMeasures[2], 2);
        setLayoutParams(layoutParams);

        Drawable bg = ContextCompat.getDrawable(context, R.drawable.ci_rounded_background);
        assert bg != null;
        bg.setColorFilter(ContextCompat.getColor(context, R.color.floating_blue), PorterDuff.Mode.MULTIPLY);

        setPadding(dpMeasures[0], dpMeasures[0], dpMeasures[0], dpMeasures[0]);
        setBackgroundDrawable(bg);
        setImageResource(iconDrawableId);
        setOnClickListener(viewModel::onCategoryIconClick);
    }

    public CategoryIconImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CategoryIconImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Singleton method that initializes the density pixel measures used by the CategoryIconImageView instances.
     */
    private void initDensityPixelMeasures() {
        if (dpMeasures == null) {
            dpMeasures = new int[]{
                    // Padding
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()),
                    // Icon Size
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics()),
                    // Margin
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, getResources().getDisplayMetrics())
            };
        }
    }
}
