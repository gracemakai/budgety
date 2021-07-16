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
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.grace.budgtey.R;
import com.grace.budgtey.helpers.Utils;
import com.grace.budgtey.database.entity.TransactionEntity;
import com.grace.budgtey.databinding.NewTransactionFragmentBinding;

import java.util.Objects;

public class NewTransactionFragment extends Fragment {

    View view;

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
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.newTransactionToolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity())
                .getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity())
                .getSupportActionBar()).setTitle("New transaction");

        binding.date.setOnClickListener(v -> showDateDialog());
        binding.saveTransactionFab.setOnClickListener(v -> saveTransaction());

    }


    public void showDateDialog(){
        new Utils().showDatePickerDialog(getContext(),
                (view, year, month, dayOfMonth) -> {
                    String dateString = new Utils().getSpecificDate(year, month, dayOfMonth);
                    binding.date.setText(dateString);
                });
    }

    private void saveTransaction() {

        if (utils.hasText(binding.amountSpent) && utils.hasText(binding.note)) {

            TransactionEntity transactionEntity = new TransactionEntity();
            transactionEntity.setCategory(binding.category.getSelectedItem().toString());
            mViewModel.addTransaction(transactionEntity);
            requireActivity().onBackPressed();
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