package com.example.expensetracker.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.expensetracker.R;
import com.example.expensetracker.activity.view_model.TransactionTypeSelectionVM;
import com.example.expensetracker.activity.view_model.TransactionTypeSelectionVMFactory;
import com.example.expensetracker.databinding.TransactionTypeSelectionBinding;


public class TransactionTypeSelectionFragment extends Fragment {
    private TransactionTypeSelectionBinding binding;
    private TransactionTypeSelectionVM vm;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = DataBindingUtil.inflate(inflater, R.layout.transaction_type_selection, container, false);
        binding.setLifecycleOwner(getActivity());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assert getActivity() != null;
        vm = new ViewModelProvider(
                this, new TransactionTypeSelectionVMFactory(getActivity().getApplication())
        ).get(TransactionTypeSelectionVM.class);

        binding.setVm(vm);
    }
}
