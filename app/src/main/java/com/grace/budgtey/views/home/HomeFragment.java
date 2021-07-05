package com.grace.budgtey.views.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.grace.budgtey.MainActivity;
import com.grace.budgtey.R;
import com.grace.budgtey.adapter.TransactionRecyclerAdapter;
import com.grace.budgtey.database.entity.TransactionEntity;
import com.grace.budgtey.views.newTransaction.NewTransactionFragment;
import com.grace.budgtey.views.setting.SettingsFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    View view;
    FloatingActionButton floatingActionButton;
    Toolbar toolbar;
    RecyclerView recyclerView;
    TextView budget, expense, balance;

    private HomeViewModel mViewModel;
    TransactionRecyclerAdapter recyclerAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerAdapter = new TransactionRecyclerAdapter(getContext());
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mViewModel.getAllTransactionsMutableLiveData()
                .observe(this, allTransactionsObserver);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);


        initViews();

        return view;
    }

    private void initViews() {

        toolbar = view.findViewById(R.id.toolbar_home);
        floatingActionButton = view.findViewById(R.id.new_transaction_fab);
        budget = view.findViewById(R.id.budget_home);
        expense = view.findViewById(R.id.expenses_home);
        balance = view.findViewById(R.id.balance_home);
        recyclerView = view.findViewById(R.id.recent_transactions_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerAdapter);

        floatingActionButton.setOnClickListener(v -> newPage(new NewTransactionFragment()));

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(null);
    }

    Observer<ArrayList<TransactionEntity>>allTransactionsObserver =
            new Observer<ArrayList<TransactionEntity>>() {
        @Override
        public void onChanged(ArrayList<TransactionEntity> transactionEntities) {
            recyclerAdapter.addTransactions(transactionEntities);
            recyclerAdapter.notifyDataSetChanged();
            Log.d(getClass().getSimpleName(), "onChanged() returned: " + transactionEntities);
        }
    };

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.settings_home:
                Toast.makeText(getContext(), "yoh", Toast.LENGTH_SHORT).show();
                newPage(new SettingsFragment());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void newPage(Fragment fragment) {

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack("");
        fragmentTransaction.commit();
    }

}