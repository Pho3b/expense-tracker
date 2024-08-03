package com.example.expensetracker.ui.model;

import static com.example.expensetracker.model.Constants.CATEGORY_ICON_LABEL;

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
import com.example.expensetracker.activity.view_model.CreateEditTransactionVM;

public class CategoryIconView extends androidx.appcompat.widget.AppCompatImageView {
    public int categoryId;
    private static int[] dpMeasures = null;


    public CategoryIconView(
            @NonNull Context context,
            CreateEditTransactionVM viewModel,
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

        setId(this.categoryId);
        setTag(CATEGORY_ICON_LABEL);
        setPadding(dpMeasures[0], dpMeasures[0], dpMeasures[0], dpMeasures[0]);
        setBackground(bg);
        setImageResource(iconDrawableId);
        setOnClickListener(viewModel::onCategoryIconClick);
    }

    public CategoryIconView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CategoryIconView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics()),
                    // Margin
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, getResources().getDisplayMetrics())
            };
        }
    }
}
