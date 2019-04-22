package com.example.budget2.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.budget2.R;
import com.example.budget2.model.Expense;

import java.util.Date;

public class ExpenseActivity extends AppCompatActivity {
    private Button mButtonCancel, mButtonSave;
    private double mAmount;
    private String mAmountS;
    private EditText mAmountE;
    private int mCategory;
    private String mCategoryS;
    private EditText mCategoryE;
    private String mSubcategory, mNote;
    private EditText mSubcategoryE, mNoteE;
    private Date mDate;
    private String mDateS;
    private EditText mDateE;
    private Expense mExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        mButtonCancel = (Button)findViewById(R.id.cancelButton);
        mButtonSave = (Button)findViewById(R.id.save);
        mAmountE = (EditText)findViewById(R.id.amount);
        mCategoryE = (EditText)findViewById(R.id.category);
        mSubcategoryE = (EditText)findViewById(R.id.subcategory);
        mNoteE = (EditText)findViewById(R.id.note);
        mDateE = (EditText)findViewById(R.id.date);

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAmountS = mAmountE.getText().toString();
                try{
                    mAmount = Double.parseDouble(mAmountS);
                } catch(NumberFormatException nfe){
                    Toast.makeText(ExpenseActivity.this,
                            "That's not a number you dumb dumb",
                            Toast.LENGTH_LONG).show();
                    finish();
                }


                Intent resultIntent = new Intent();
                finish();
            }
        });


    }
}
