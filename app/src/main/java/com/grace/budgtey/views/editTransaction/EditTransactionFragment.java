package com.grace.budgtey.views.editTransaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.grace.budgtey.databinding.EditTransactionFragmentBinding;

import java.util.Objects;

public class EditTransactionFragment extends Fragment {

    View view;

    EditTransactionViewModel mViewModel;
    TransactionEntity transactionEntity;
    EditTransactionFragmentBinding binding;

    public EditTransactionFragment(TransactionEntity transactionEntity) {
        this.transactionEntity = transactionEntity;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mViewModel = new ViewModelProvider(this).get(EditTransactionViewModel.class);
        mViewModel.setTransactionEntity(transactionEntity);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil
                .inflate(inflater, R.layout.edit_transaction_fragment, container, false);
        binding.setViewModel(mViewModel);
        view = binding.getRoot();

        initViews();


        return view;
    }

    private void initViews() {

        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(binding.editTransactionToolbar);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setTitle("Edit transaction");

        binding.saveEditTransactionFab.setOnClickListener(v -> saveEditTransaction());
    }

    private void saveEditTransaction(){

        mViewModel.editTransaction(Objects.requireNonNull(getTransactionDetails()));
        requireActivity().onBackPressed();
    }

    private TransactionEntity getTransactionDetails() {
        if (new Utils().hasText(binding.amountEdit) && new Utils().hasText(binding.noteEdit)) {

            TransactionEntity transactionEntity = new TransactionEntity();
            transactionEntity.setCategory(binding.categoryEdit.getSelectedItem().toString());
            return transactionEntity;
        }
        return null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.transaction_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                requireActivity().onBackPressed();
                break;

            case R.id.delete:

                new Utils().showAlertDialog(getContext(), "Delete transaction",
                        "Are you sure you want to delete transaction?",
                        //User clicked yes
                        (dialog, which) -> deleteTransaction(),
                        //User clicked no
                        (dialog, which) -> {
                            dialog.cancel();
                        });
                break;

            default: return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteTransaction() {
        mViewModel.deleteTransaction(Objects.requireNonNull(getTransactionDetails()));
        requireActivity().onBackPressed();
    }

}