package com.grace.budgtey.views.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
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
import com.grace.budgtey.helpers.RecyclerTouchListener;
import com.grace.budgtey.helpers.Utils;
import com.grace.budgtey.adapter.TransactionRecyclerAdapter;
import com.grace.budgtey.database.entity.TransactionEntity;
import com.grace.budgtey.databinding.HomeFragmentBinding;
import com.grace.budgtey.views.editTransaction.EditTransactionFragment;
import com.grace.budgtey.views.newTransaction.NewTransactionFragment;
import com.grace.budgtey.views.setting.SettingsFragment;
import com.grace.budgtey.views.spendingDetails.SpendingDetailsFragment;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    View view;

    private HomeViewModel mViewModel;
    TransactionRecyclerAdapter recyclerAdapter;
    private HomeFragmentBinding binding;

    ArrayList<TransactionEntity> transactions;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        recyclerAdapter = new TransactionRecyclerAdapter(getContext());

        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        mViewModel.setBudget(getBudget());

        mViewModel.getAllTransactionsMutableLiveData()
                .observe(this, allTransactionsObserver);
        mViewModel.getTotalMutableLiveData()
                .observe(this, totalAmountObserver);

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
        binding.recentTransactionsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recentTransactionsRecycler.setAdapter(recyclerAdapter);

        //Set up toolbar
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbarHome);
        ((AppCompatActivity) requireActivity())
                .getSupportActionBar().setDisplayShowTitleEnabled(false);

        //open up new transaction
        binding.newTransactionFab.setOnClickListener(v -> newPage(new NewTransactionFragment()));

        //Card view money
        binding.moneyCardView
                .setOnClickListener(v -> newPage(new SpendingDetailsFragment(transactions)));

    }

    //Sends user transactions to recycler when they finish being read from the database
    Observer<ArrayList<TransactionEntity>>allTransactionsObserver =
            new Observer<ArrayList<TransactionEntity>>() {
        @Override
        public void onChanged(ArrayList<TransactionEntity> transactionEntities) {
            transactions = transactionEntities;

            recyclerItemClicked(transactionEntities);
            recyclerAdapter.addTransactions(transactionEntities);
            recyclerAdapter.notifyDataSetChanged();

            addExpenses(transactionEntities);
        }
    };

    private void addExpenses(ArrayList<TransactionEntity> transactionEntities) {
        float totalExpenses = 0f;

        for (int i = 0; i < transactionEntities.size(); i++) {
            totalExpenses += transactionEntities.get(i).getAmount();
        }

        mViewModel.setExpenses(totalExpenses);
    }

    //Recycler click listener
    private void recyclerItemClicked(ArrayList<TransactionEntity> transactionEntities) {

        binding.recentTransactionsRecycler
                .addOnItemTouchListener(new RecyclerTouchListener(getContext(),
                        binding.recentTransactionsRecycler,
                        new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                newPage(new EditTransactionFragment(transactionEntities.get(position)));
            }

            @Override
            public void onLongClick(View view, int position) {

                new Utils().showAlertDialog(getContext(), "Delete transaction",
                        "Are you sure you want to delete transaction?",
                        //User clicked yes
                        (dialog, which) -> deleteTransaction(transactionEntities.get(position)),
                        //User clicked no
                        (dialog, which) -> {
                            dialog.cancel();
                        });


            }
        }));
    }

    //Updates the ui when the total expenses are read from the database
    Observer<Float> totalAmountObserver = new Observer<Float>() {
        @Override
        public void onChanged(Float aFloat) {

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

    private void deleteTransaction(TransactionEntity transactionEntity) {
        mViewModel.deleteTransaction(transactionEntity);
        recyclerAdapter.notifyDataSetChanged();
    }
}