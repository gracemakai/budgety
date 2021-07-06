package com.grace.budgtey.views.newTransaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.grace.budgtey.MainActivity;
import com.grace.budgtey.R;
import com.grace.budgtey.Utils;
import com.grace.budgtey.database.entity.TransactionEntity;

import java.util.Calendar;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class NewTransactionFragment extends Fragment {

    View view;
    MaterialToolbar toolbar;
    EditText amountSpent, note;
    TextView date;
    Spinner category;
    FloatingActionButton saveFab;

    private NewTransactionViewModel mViewModel;
    Utils utils;

    public static NewTransactionFragment newInstance() {
        return new NewTransactionFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(NewTransactionViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.new_transaction_fragment, container, false);

        utils = new Utils();

        initViews();

        return view;
    }

    private void initViews() {
        toolbar = view.findViewById(R.id.new_transaction_toolbar);
        amountSpent = view.findViewById(R.id.amount_spent);
        date = view.findViewById(R.id.date);
        note = view.findViewById(R.id.note);
        category = view.findViewById(R.id.category);
        saveFab = view.findViewById(R.id.save_transaction_fab);

        date.setText(utils.getCurrentTimeOrDate());

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("New transaction");

        saveFab.setOnClickListener(v -> saveTransaction());
        date.setOnClickListener(v -> {
            utils.showDatePickerDialog(getContext(), (view, year, month, dayOfMonth) ->
                    date.setText(utils
                            .getSpecificDate("EEE, dd-MM-yy", year, month, dayOfMonth)
                    )
            );
        });
    }

    private void saveTransaction() {

        if (utils.hasText(amountSpent) && utils.hasText(note)) {

            mViewModel.addTransaction(new TransactionEntity(category.getSelectedItem().toString(),
                    date.getText().toString().trim(), note.getText().toString().trim(),
                    Float.parseFloat(amountSpent.getText().toString().trim())));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Log.i(TAG, "onOptionsItemSelected: home selected");
            getActivity().onBackPressed();
        } else {
            Log.i(TAG, "onOptionsItemSelected: " + item.getItemId());
        }

        return super.onOptionsItemSelected(item);
    }


}