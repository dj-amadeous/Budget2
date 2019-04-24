package com.example.budget2.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import com.example.budget2.R;
import com.example.budget2.model.DatePickerFragment;
import com.example.budget2.model.Expense;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExpenseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private Button mButtonCancel, mButtonSave;
    private double mAmount;
    private String mAmountS;
    private EditText mAmountE;
    private int mCategory;
    private String mCategoryS;
    private EditText mCategoryE;
    private String mSubcategoryS, mNoteS;
    private EditText mSubcategoryE, mNoteE;
    private String mDateS;
    private Button mButtonDateE;
    private Expense mExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        mButtonCancel = (Button)findViewById(R.id.cancelButton);
        mButtonSave = (Button)findViewById(R.id.save);
        mButtonDateE = (Button)findViewById(R.id.date);
        mAmountE = (EditText)findViewById(R.id.amount);
        mCategoryE = (EditText)findViewById(R.id.category);
        mSubcategoryE = (EditText)findViewById(R.id.subcategory);
        mNoteE = (EditText)findViewById(R.id.note);

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mButtonDateE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAmountS = mAmountE.getText().toString();
                mCategoryS = mCategoryE.getText().toString();
                mSubcategoryS = mSubcategoryE.getText().toString();
                mNoteS = mNoteE.getText().toString();
                //TODO make date picker widget
                try{
                    mAmount = Double.parseDouble(mAmountS);
                    mCategory = Integer.parseInt(mCategoryS);
                } catch(NumberFormatException nfe){
                    Toast.makeText(ExpenseActivity.this,
                            "That's not a number you dumb dumb",
                            Toast.LENGTH_LONG).show();
                    finish();
                }

                mExpense = new Expense(mAmount, mDateS, mCategory, mNoteS, mSubcategoryS);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Expense", mExpense);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day){
        Integer yearI = year;
        Integer monthI = month;
        Integer dayOfMonthI = day;

        String yearS = yearI.toString();
        String monthS = monthI.toString();
        String dayOfMonthS = dayOfMonthI.toString();

        String finalDate = yearS + "," + monthS + "," + dayOfMonthS;
        this.mDateS = finalDate;
    }
}
