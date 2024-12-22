package com.example.expensetracker.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.view_model.ActivityHeaderVM;
import com.example.expensetracker.activity.view_model.ViewModelsFactory;
import com.example.expensetracker.databinding.ActivityHeaderBinding;


public class ActivityHeaderFragment extends Fragment {
    public ActivityHeaderVM vm;
    protected DrawerLayout drawer;
    private ActivityHeaderBinding binding;


    public ActivityHeaderFragment(DrawerLayout drawer) {
        this.drawer = drawer;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initializing the Activity ViewModel
        assert getActivity() != null;
        ViewModelProvider vmProvider = new ViewModelProvider(this, new ViewModelsFactory(getActivity().getApplication()));
        vm = vmProvider.get(ActivityHeaderVM.class);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        // Binding the ViewModel to the Fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_header, container, false);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.setActivityHeaderVM(vm);
        observeOpenDrawerButtonClick();
    }

    private void observeOpenDrawerButtonClick() {
        vm.openDrawerBtnClicked.observe(
                getViewLifecycleOwner(),
                (Boolean clicked) -> {
                    if (clicked) {
                        drawer.openDrawer(GravityCompat.START);
                        vm.openDrawerBtnClicked.setValue(false);
                    }
                }
        );
    }
}
