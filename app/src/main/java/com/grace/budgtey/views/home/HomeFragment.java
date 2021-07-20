package com.grace.budgtey.views.home;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.grace.budgtey.ListItemClickListener;
import com.grace.budgtey.R;
import com.grace.budgtey.adapter.TransactionRecyclerAdapter;
import com.grace.budgtey.database.entity.TransactionEntity;
import com.grace.budgtey.databinding.HomeFragmentBinding;
import com.grace.budgtey.helpers.Utils;
import com.grace.budgtey.models.DayTransactionsModel;
import com.grace.budgtey.views.editTransaction.EditTransactionFragment;
import com.grace.budgtey.views.newTransaction.NewTransactionFragment;
import com.grace.budgtey.views.setting.SettingsFragment;
import com.grace.budgtey.views.spendingDetails.SpendingDetailsFragment;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment implements ListItemClickListener {

    View view;

    private HomeViewModel mViewModel;
    TransactionRecyclerAdapter recyclerAdapter;
    private HomeFragmentBinding binding;

    ArrayList<TransactionEntity> transactions;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        recyclerAdapter = new TransactionRecyclerAdapter(getContext(), this);

        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        mViewModel.setBudget(getBudget());

        mViewModel.getAllTransactionsMutableLiveData()
                .observe(this, allTransactionsObserver);

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

        //Set up current month
        binding.monthAndYearBtn.setText(new Utils().getMonthAndYear());
        binding.monthAndYearBtn.setOnClickListener(v -> pickMonth());

        //set up recyclerView
        binding.recentTransactionsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recentTransactionsRecycler.setAdapter(recyclerAdapter);

        //Set up toolbar
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbarHome);
        Objects.requireNonNull(((AppCompatActivity) requireActivity())
                .getSupportActionBar()).setDisplayShowTitleEnabled(false);

        //open up new transaction
        binding.newTransactionFab.setOnClickListener(v -> newPage(new NewTransactionFragment()));

        //Card view money
        binding.moneyCardView
                .setOnClickListener(v -> newPage(new SpendingDetailsFragment(transactions)));

    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.getTotalMutableLiveData();
        mViewModel.getAllTransactionsMutableLiveData();
    }

    private void pickMonth() {
        new Utils().showDatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {

            binding.monthAndYearBtn.setText(new Utils().getSpecificMonthAndYear(year, month));
        });
    }

    //Sends user transactions to recycler when they finish being read from the database
    Observer<ArrayList<TransactionEntity>>allTransactionsObserver =
            new Observer<ArrayList<TransactionEntity>>() {
        @Override
        public void onChanged(ArrayList<TransactionEntity> transactionEntities) {
            transactions = transactionEntities;

            recyclerAdapter.addTransactions(getTransactionsInDay(transactions));

            addExpenses(transactions);
        }
    };

    //Iterate through transactions and place them in dates arrays
    private ArrayList<DayTransactionsModel> getTransactionsInDay(ArrayList<TransactionEntity> transactionEntities) {

        ArrayList<DayTransactionsModel> dayTransactionsModelArrayList = new ArrayList<>();
        ArrayList<String> dates = getDates(transactionEntities);

        //Iterate through the dates
        for (int a = 0; a < dates.size(); a++) {
            ArrayList<TransactionEntity> entities = new ArrayList<>();

            //Iterate through the transactions
            for (int i = 0; i < transactionEntities.size(); i++) {

                //If transaction date matches with the date, put it in the array
                if (dates.get(a).equals(transactionEntities.get(i).getDate())) {
                    entities.add(transactionEntities.get(i));

                    System.out.println(dates.get(a) + " and " + transactionEntities.get(i).getDate()
                    + " item " + transactionEntities.get(i).getNote());
                }
            }

            DayTransactionsModel dayTransactionsModel = new DayTransactionsModel();
            dayTransactionsModel.setDate(dates.get(a));
            dayTransactionsModel.setDayTotal(getDayTotal(entities));
            dayTransactionsModel.setDayTransactions(entities);

            System.out.println(dayTransactionsModel.getDate() + " " + dayTransactionsModel.getDayTotal()
            + " " + dayTransactionsModel.getDayTransactions().size());

            //Add the date and its transactions to the parent array
            dayTransactionsModelArrayList.add(dayTransactionsModel);
        }

        System.out.println(dayTransactionsModelArrayList.size());

        return dayTransactionsModelArrayList;
    }

    //Get day total
    private Integer getDayTotal(ArrayList<TransactionEntity> entities) {

        float dayTotal = 0f;

        for (int i = 0; i <entities.size(); i++) {
            dayTotal += entities.get(i).getAmount();
        }

        return (int) dayTotal;
    }

    //Get all the dates that transactions
    private ArrayList<String> getDates(ArrayList<TransactionEntity> transactionEntities) {

        ArrayList<String> dates = new ArrayList<>();

        for (int i = 0; i < transactionEntities.size(); i++) {
            if (!dates.contains(transactionEntities.get(i).getDate())){
                dates.add(transactionEntities.get(i).getDate());
                System.out.println(transactionEntities.get(i).getDate());
            }
        }

        return dates;
    }

    private void addExpenses(ArrayList<TransactionEntity> transactionEntities) {
        float totalExpenses = 0f;

        for (int i = 0; i < transactionEntities.size(); i++) {
            totalExpenses += transactionEntities.get(i).getAmount();
        }

        mViewModel.setExpenses(totalExpenses);
    }

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
        mViewModel.getTotalMutableLiveData();
    }

    @Override
    public void onListItemClick(TransactionEntity transactionEntity) {
        newPage(new EditTransactionFragment(transactionEntity));
    }

    @Override
    public void onListItemLongClick(TransactionEntity transactionEntity) {

            new Utils().showAlertDialog(getContext(), "Delete transaction",
                    "Are you sure you want to delete transaction?",
                    //User clicked yes
                    (dialog, which) -> deleteTransaction(transactionEntity),
                    //User clicked no
                    (dialog, which) -> dialog.cancel()
            );

        }
}
