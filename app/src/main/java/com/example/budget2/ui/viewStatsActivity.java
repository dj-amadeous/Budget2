package com.example.budget2.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.budget2.R;
import com.example.budget2.model.Expense;
import com.example.budget2.model.Income;
import com.example.budget2.model.FirebaseDatabaseHelper;
import com.google.firebase.database.FirebaseDatabase;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class viewStatsActivity extends AppCompatActivity {
    private ArrayList<Expense> expenseList;
    private ArrayList<Income> incomeList;
    private Button cancelButton;
    private Button demoButton;
    private TextView totalIncomeTextView;
    private TextView monthlyWantsTextView;
    private TextView monthlyNeedsTextView;
    private TextView monthlySavesTextView;
    private TextView dailyWantsTextView;
    private TextView dailyNeedsTextView;
    private TextView dailySavesTextView;
    private GraphView graph;

    private double totalIncome = 0;
    private double ewantsSum=0;
    private double eneedsSum=0;
    private double esavesSum=0;
    private double iwantsSum=0;
    private double ineedsSum=0;
    private double isavesSum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stats);
        cancelButton = (Button)findViewById(R.id.cancelButton);
        demoButton = (Button)findViewById(R.id.demoButton);
        totalIncomeTextView = (TextView)findViewById(R.id.totalIncomeTextView);
        monthlyWantsTextView = (TextView)findViewById(R.id.monthlyWantsTextView);
        monthlyNeedsTextView = (TextView)findViewById(R.id.monthlyNeedsTextView);
        monthlySavesTextView = (TextView)findViewById(R.id.monthlySavesTextView);
        dailyWantsTextView = (TextView)findViewById(R.id.dailyWantsTextView);
        dailyNeedsTextView = (TextView)findViewById(R.id.dailyNeedsTextView);
        dailySavesTextView = (TextView)findViewById(R.id.dailySavesTextView);

        getSupportActionBar().hide();
        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        expenseList = bundle.getParcelableArrayList("expenseList");
        incomeList = bundle.getParcelableArrayList("incomeList");
        makeGraph();

        updateStats();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        demoButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                easterEgg();
                makeGraph();
                updateStats();
            }
        });
    }

    private void updateStats() {
        int daysUntilEnd = getDatesUntilEndOfMonth();
        Double tempTotalIncome = totalIncome;
        Double remainingMonthlyWants = totalIncome - ewantsSum;
        Double remainingMonthlyNeeds = totalIncome - eneedsSum;
        Double remainingMonthlySaves = totalIncome - esavesSum;
        Double remainingDailyWants = (totalIncome - ewantsSum)/daysUntilEnd;
        Double remainingDailyNeeds = (totalIncome - eneedsSum)/daysUntilEnd;
        Double remainingDailySaves = (totalIncome - esavesSum)/daysUntilEnd;

        totalIncomeTextView.setText(tempTotalIncome.toString());
        monthlyWantsTextView.setText(remainingMonthlyWants.toString());
        monthlyNeedsTextView.setText(remainingMonthlyNeeds.toString());
        monthlySavesTextView.setText(remainingMonthlySaves.toString());
        dailyWantsTextView.setText(remainingDailyWants.toString());
        dailyNeedsTextView.setText(remainingDailyNeeds.toString());
        dailySavesTextView.setText(remainingDailySaves.toString());
    }

    private int getDatesUntilEndOfMonth() {
        Calendar c = Calendar.getInstance();
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int daysInMonth;
        if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
            daysInMonth = 31;
        } else if (month == 2){
            daysInMonth = 28;
        } else {
            daysInMonth = 30;
        }
        return daysInMonth - dayOfMonth;
    }

    public void makeGraph() {

        if(expenseList.size() > 0){
            for (Expense e : expenseList) {
                if (e.getCategory() == 1) // wants
                {
                    ewantsSum += e.getAmount();
                }
                else if (e.getCategory() == 2) // needs
                {
                    eneedsSum += e.getAmount();
                }
                else if (e.getCategory() == 3) // saves
                {
                    esavesSum += e.getAmount();
                }
            }
        }
        if(incomeList.size() > 0){
            for(Income i : incomeList){
                if(i.getCategory()==1){
                    totalIncome+=i.getAmount();
                }
                if(i.getCategory()==2){
                    int months = 0;

                    String s = i.getDate();
                    int month = s.charAt(5);

                    if(s.charAt(5)==1) {
                        if (s.charAt(6) == 0) {
                            //oct
                        }
                    }
                    totalIncome+=(months*i.getAmount());
                }
            }
        }


        graph = (GraphView) findViewById(R.id.graph);

        BarGraphSeries<DataPoint> series1 = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(1, ewantsSum),
                new DataPoint(3, eneedsSum),
                new DataPoint(5, esavesSum),});

        BarGraphSeries<DataPoint> series2 = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(2, totalIncome * .5),
                new DataPoint(4, totalIncome * .3),
                new DataPoint(6, totalIncome * .2)
        });

        graph.addSeries(series1);
        graph.addSeries(series2);
        series1.setColor(Color.GREEN);
        series1.setSpacing(50);
        series2.setSpacing(50);


    }



    public void easterEgg(){

        expenseList.add(new Expense(135, "2019,04,22", 1, "note", "note", 25));
        expenseList.add(new Expense(5, "2019,04,22", 1, "note", "note", 26));
        expenseList.add(new Expense(40, "2019,04,22", 1, "note", "note", 27));
        expenseList.add(new Expense(70, "2019,04,22", 1, "note", "note", 28));
        expenseList.add(new Expense(20, "2019,04,22", 2, "note", "note", 29));
        expenseList.add(new Expense(40, "2019,04,22", 2, "note", "note", 30));
        expenseList.add(new Expense(100, "2019,04,22", 2, "note", "note", 31));
        expenseList.add(new Expense(3, "2019,04,22", 2, "note", "note", 32));
        expenseList.add(new Expense(35, "2019,04,22", 3, "note", "note", 20));
        expenseList.add(new Expense(135, "2019,04,22", 3, "note", "note", 21));
        expenseList.add(new Expense(45, "2019,04,22", 3, "note", "note", 23));
        expenseList.add(new Expense(25, "2019,04,22", 3, "note", "note", 24));

        totalIncome = 600;

    }
}
