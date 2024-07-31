package com.example.expensetracker.activity.view_model;

import static com.example.expensetracker.model.Constants.CATEGORY_ICON_LABEL;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.expensetracker.R;
import com.example.expensetracker.ui.model.CategoryIconView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CreateEditTransactionVM extends ViewModel {
    public LocalDate date = LocalDate.now();
    public MutableLiveData<Integer> selectedCategoryId = new MutableLiveData<>(0);
    public MutableLiveData<Drawable> incomeBackground = new MutableLiveData<>();
    public MutableLiveData<Drawable> expenseBackground = new MutableLiveData<>();
    public MutableLiveData<Boolean> openDatePickerFragmentClicked = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> addEditBtnClicked = new MutableLiveData<>(false);
    public MutableLiveData<String> uiDate = new MutableLiveData<>();
    public MutableLiveData<String> amount = new MutableLiveData<>();
    public MutableLiveData<String> comment = new MutableLiveData<>();
    public MutableLiveData<String> editBtnText = new MutableLiveData<>();
    public Drawable ciWhiteBg;
    public Drawable ciBlueBg;


    public CreateEditTransactionVM(Context ctx) {
        this.ciWhiteBg = ContextCompat.getDrawable(ctx, R.drawable.ci_rounded_background);
        ciWhiteBg.setColorFilter(ContextCompat.getColor(ctx, R.color.white), PorterDuff.Mode.MULTIPLY);

        this.ciBlueBg = ContextCompat.getDrawable(ctx, R.drawable.ci_rounded_background);
        ciBlueBg.setColorFilter(ContextCompat.getColor(ctx, R.color.floating_blue), PorterDuff.Mode.MULTIPLY);
    }

    public void setupUI() {
        uiDate.setValue(String.format(
                Locale.ITALIAN, "%d-%d-%d", date.getDayOfMonth(), date.getMonthValue(), date.getYear())
        );
    }

    public void onOpenDatePickerClick(View view) {
        openDatePickerFragmentClicked.setValue(true);
    }

    public void onAddEditTransactionClick(View view) {
        addEditBtnClicked.setValue(true);
    }

    public void onCategoryIconClick(View categoryIcon) {
        CategoryIconView iconImageView = (CategoryIconView) categoryIcon;
        selectedCategoryId.setValue(iconImageView.categoryId);
    }

    public void onDateSelected(int year, int month, int day) {
        uiDate.setValue(String.format(Locale.ITALIAN, "%d-%d-%d", day, month, year));

        this.date = LocalDate.parse(
                String.format(Locale.ITALIAN, "%d-%d-%d", day, month, year),
                DateTimeFormatter.ofPattern("d-M-yyyy")
        );
    }

    public void updateCategoryIconsBackground(ViewGroup categoriesScrollView) {
        for (int i = 0; i < categoriesScrollView.getChildCount(); i++) {
            ViewGroup wrapperChild = (ViewGroup) categoriesScrollView.getChildAt(i);

            for (int j = 0; j < wrapperChild.getChildCount(); j++) {
                View categoryIconView = wrapperChild.getChildAt(j);

                if (CATEGORY_ICON_LABEL.equals(categoryIconView.getTag())) {
                    Drawable bg = ciBlueBg;

                    if (categoryIconView.getId() == (selectedCategoryId.getValue() == null ? 0 : selectedCategoryId.getValue()))
                        bg = ciWhiteBg;

                    categoryIconView.setBackground(bg);
                }
            }
        }
    }
}
