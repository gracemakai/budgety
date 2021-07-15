package com.grace.budgtey.views.spendingDetails;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.grace.budgtey.R;
import com.grace.budgtey.adapter.CategoryTotalRecyclerAdapter;
import com.grace.budgtey.database.entity.TransactionEntity;
import com.grace.budgtey.models.CategoryTotalModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


public class SpendingDetailsFragment extends Fragment {

    ArrayList<TransactionEntity> transactionEntityArrayList;
    ArrayList<CategoryTotalModel> categoryTotalModelArrayList;

    View view;
    RecyclerView categoriesRecyclerView;

    public SpendingDetailsFragment(ArrayList<TransactionEntity> transactionEntityArrayList) {
        this.transactionEntityArrayList = transactionEntityArrayList;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpendingDetailsViewModel mViewModel = new ViewModelProvider(this).get(SpendingDetailsViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.spending_details_fragment, container, false);

        initRecyclerView();
        preparePieChart();

        return view;
    }

    private void initRecyclerView() {
        categoriesRecyclerView = view.findViewById(R.id.categories_total_recyclerView);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        categoriesRecyclerView.setAdapter(new CategoryTotalRecyclerAdapter(getContext(),
                fillCategoriesData()));
    }

    //Go through each transaction in the array list and put ones with matching category
    //names in their own arrays
    private ArrayList<CategoryTotalModel> fillCategoriesData() {

        categoryTotalModelArrayList = new ArrayList<>();

        //Iterate through category names
        for (int a = 0; a < getCategories().size(); a++){

            ArrayList<TransactionEntity> categoryItems = new ArrayList<>();

            //Iterate through all the transactions
            for (int i = 0; i < transactionEntityArrayList.size(); i++) {

                //if the transaction matches the current category put it in array categoryItems
                if (getCategories().get(a).equals(transactionEntityArrayList.get(i).getCategory())) {
                    categoryItems.add(transactionEntityArrayList.get(i));
                }
            }

            CategoryTotalModel categoryTotalModel = new CategoryTotalModel();
            categoryTotalModel.setName(getCategories().get(a));
            categoryTotalModel.setCategoryItems(categoryItems);
            categoryTotalModel.setTotal(getCategoryTotalAmount(categoryTotalModel.getCategoryItems()));
            categoryTotalModel.setPercentage((int) getPercentage(categoryTotalModel.getTotal()));

            //Add the category and its transactions to the parent array
            categoryTotalModelArrayList.add(categoryTotalModel);
        }

        return categoryTotalModelArrayList;
    }

    //Create an array of category names from the array with all the transactions
    private ArrayList<String> getCategories(){
        ArrayList<String> categoryNames = new ArrayList<>();

        for (int i = 0; i < transactionEntityArrayList.size(); i++){

            //If the categoryNamesArray does not contain the transactions category name, add it
            if (!categoryNames.contains(transactionEntityArrayList.get(i).getCategory())) {
                categoryNames.add(transactionEntityArrayList.get(i).getCategory());
            }
        }

        return categoryNames;
    }

    //Get total amount spent in a category by adding all the transactions in the category
    private int getCategoryTotalAmount(ArrayList<TransactionEntity> categoryItems){

        float categoryTotal = 0;

        for (int i = 0; i < categoryItems.size(); i++) {
            categoryTotal += categoryItems.get(i).getAmount();
        }

        return (int) categoryTotal;
    }

    //Get total amount used by the user by adding all the transactions
    private Float getTotalExpenses() {
        Float totalAmountSpent = (float) 0;

        for (int i = 0; i < transactionEntityArrayList.size(); i++){
            totalAmountSpent += transactionEntityArrayList.get(i).getAmount();
        }

        return totalAmountSpent;
    }

    //Get category percentage from the total amount spent
    private float getPercentage(int categoryAmount) {
       return (Float.parseFloat(String.valueOf(categoryAmount)) / getTotalExpenses()) * 100;
    }

    private void preparePieChart() {
        PieChart pieChart = view.findViewById(R.id.expenses_pie_chart);

        ArrayList<PieEntry> expenses = new ArrayList<>();

        //Get category expenses and names for pie chart
        for (int i = 0; i < categoryTotalModelArrayList.size(); i++) {
            expenses.add(new PieEntry(categoryTotalModelArrayList.get(i).getTotal(),
                    categoryTotalModelArrayList.get(i).getName()));
        }

        PieDataSet dataSet = new PieDataSet(expenses, "");
        dataSet.setColors(getColors());
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.WHITE);

        pieChart.setData(pieData);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setDrawEntryLabels(false);
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setCenterText("Spending by category");
        pieChart.setCenterTextSize(16);
        pieChart.getDescription().setEnabled(false);
        pieChart.setUsePercentValues(true);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setEnabled(true);
        legend.setTextSize(16);

        pieChart.invalidate();
        pieChart.animateY(1400, Easing.EaseInOutQuad);

    }

    // add a lot of colors
    private ArrayList<Integer> getColors() {

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        return colors;
    }


}