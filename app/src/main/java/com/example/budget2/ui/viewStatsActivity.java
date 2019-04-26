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

import java.util.ArrayList;

public class viewStatsActivity extends AppCompatActivity {
    private ArrayList<Expense> expenseList;
    private ArrayList<Income> incomeList;
    private Button cancelButton;
    private Button demoButton;
    private TextView firstText;
    private GraphView graph;

    double totalIncome = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stats);

        cancelButton = (Button)findViewById(R.id.cancelButton);
        demoButton = (Button)findViewById(R.id.demoButton);


        //jihuiohiouhiouhiuo

        // make userDumb object with userDumb messages

        //(total monthly income - current expenses        /      30 - days passed) categorized by needs/wants/saves
        //search function
        //display overall income, allowances per category, daily allowance per category

        firstText = (TextView) findViewById(R.id.firstText);


        Intent intent = getIntent();

        Bundle bundle = getIntent().getExtras();

        expenseList = bundle.getParcelableArrayList("expenseList");
        incomeList = bundle.getParcelableArrayList("incomeList");

        //easterEgg();

        makeGraph();


        /*FirebaseDatabaseHelper helper = new FirebaseDatabaseHelper();
        helper.ReadExpenses();
        helper.getExpenseList();*/
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
            }
        });




    }


    public void makeGraph() {


        double ewantsSum=0;
        double eneedsSum=0;
        double esavesSum=0;
        double iwantsSum=0;
        double ineedsSum=0;
        double isavesSum=0;

        if(expenseList.size() > 0){
            firstText.setText(expenseList.get(0).getNote());
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
