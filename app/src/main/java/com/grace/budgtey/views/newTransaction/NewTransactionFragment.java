package com.grace.budgtey.views.newTransaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.grace.budgtey.R;
import com.grace.budgtey.helpers.Utils;
import com.grace.budgtey.database.entity.TransactionEntity;
import com.grace.budgtey.databinding.NewTransactionFragmentBinding;

import java.util.Objects;

public class NewTransactionFragment extends Fragment {

    View view;
    MaterialToolbar toolbar;
    EditText amountSpent, note;
    Spinner category;
    FloatingActionButton saveFab;

    private NewTransactionViewModel mViewModel;
    private NewTransactionFragmentBinding binding;
    Utils utils = new Utils();

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mViewModel = new ViewModelProvider(this).get(NewTransactionViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //Bind fragment
        binding = DataBindingUtil
                .inflate(inflater, R.layout.new_transaction_fragment,
                        container, false);

        binding.setLifecycleOwner(this);
        binding.setViewModel(mViewModel);

        view = binding.getRoot();

        initViews();

        return view;
    }

    private void initViews() {

        //Set up toolbar
        toolbar = view.findViewById(R.id.new_transaction_toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity())
                .getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity())
                .getSupportActionBar()).setTitle("New transaction");

        amountSpent = view.findViewById(R.id.amount_spent);
        note = view.findViewById(R.id.note);
        category = view.findViewById(R.id.category);
        saveFab = view.findViewById(R.id.save_transaction_fab);
        saveFab.setOnClickListener(v -> saveTransaction());

    }

    private void saveTransaction() {

        if (utils.hasText(amountSpent) && utils.hasText(note)) {

            TransactionEntity transactionEntity = new TransactionEntity();
            transactionEntity.setCategory(category.getSelectedItem().toString());
            mViewModel.addTransaction(transactionEntity);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            requireActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}