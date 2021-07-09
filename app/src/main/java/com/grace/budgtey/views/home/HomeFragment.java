package com.grace.budgtey.views.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.grace.budgtey.R;
import com.grace.budgtey.adapter.TransactionRecyclerAdapter;
import com.grace.budgtey.database.entity.TransactionEntity;
import com.grace.budgtey.databinding.HomeFragmentBinding;
import com.grace.budgtey.views.newTransaction.NewTransactionFragment;
import com.grace.budgtey.views.setting.SettingsFragment;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {

    View view;
    FloatingActionButton floatingActionButton;
    Toolbar toolbar;
    RecyclerView recyclerView;

    private HomeViewModel mViewModel;
    TransactionRecyclerAdapter recyclerAdapter;
    private HomeFragmentBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        recyclerAdapter = new TransactionRecyclerAdapter(getContext());

        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        mViewModel.getAllTransactionsMutableLiveData()
                .observe(this, allTransactionsObserver);
        mViewModel.getTotalMutableLiveData()
                .observe(this, totalAmountObserver);

        mViewModel.setBudget(getBudget());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //Bind fragment
        binding = DataBindingUtil
                .inflate(inflater, R.layout.home_fragment, container, false);

        binding.setViewModel(mViewModel);
        view = binding.getRoot();

        initViews();

        return view;
    }

    private void initViews() {

        //set up recyclerView
        recyclerView = view.findViewById(R.id.recent_transactions_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerAdapter);

        //Set up toolbar
        toolbar = view.findViewById(R.id.toolbar_home);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity())
                .getSupportActionBar().setDisplayShowTitleEnabled(false);

        //open up new transaction
        floatingActionButton = view.findViewById(R.id.new_transaction_fab);
        floatingActionButton.setOnClickListener(v -> newPage(new NewTransactionFragment()));

    }

    //Sends user transactions to recycler when they finish being read from the database
    Observer<ArrayList<TransactionEntity>>allTransactionsObserver =
            new Observer<ArrayList<TransactionEntity>>() {
        @Override
        public void onChanged(ArrayList<TransactionEntity> transactionEntities) {
            recyclerAdapter.addTransactions(transactionEntities);
            recyclerAdapter.notifyDataSetChanged();
        }
    };

    //Updates the ui when the total expenses are read from the database
    Observer<Float> totalAmountObserver = new Observer<Float>() {
        @Override
        public void onChanged(Float aFloat) {
            mViewModel.setExpenses(aFloat);
        }
    };

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.settings_home) {
            newPage(new SettingsFragment());
        }
        return super.onOptionsItemSelected(item);
    }

    //Get budget from preferences
    private String getBudget() {

        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(getContext());

        return preferences.getString("budget", "20000");
    }

    //Opens up a new fragment
    private void newPage(Fragment fragment) {

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack("");
        fragmentTransaction.commit();
    }

}