package com.grace.budgtey.views.newTransaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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

import com.google.android.material.appbar.MaterialToolbar;
import com.grace.budgtey.MainActivity;
import com.grace.budgtey.R;

import java.util.Objects;

import static android.content.ContentValues.TAG;

public class NewTransactionFragment extends Fragment {

    View view;
    MaterialToolbar toolbar;

    private NewTransactionViewModel mViewModel;

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

        initViews();

        return view;
    }

    private void initViews() {
        toolbar = view.findViewById(R.id.new_transaction_toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("New transaction");

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