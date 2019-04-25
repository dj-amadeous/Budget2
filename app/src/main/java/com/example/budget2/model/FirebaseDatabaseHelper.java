package com.example.budget2.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseDatabaseHelper {
    public FirebaseDatabase getmDatabase() {
        return mDatabase;
    }

    public DatabaseReference getmReferenceExpenses() {
        return mReferenceExpenses;
    }

    public ArrayList<Expense> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(ArrayList<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceExpenses;
    private ArrayList<Expense> expenseList = new ArrayList<Expense>();

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceExpenses = mDatabase.getReference("expenses");
    }

    public void ReadExpenses(){
        mReferenceExpenses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                expenseList.clear();

                //DataSnapshot expensesSnapshot = dataSnapshot.child("Expense");
                //Iterable<DataSnapshot> expenseChildren = expensesSnapshot.getChildren();
                for(DataSnapshot expense : dataSnapshot.getChildren()) {
                    Expense e = expense.getValue(Expense.class);
                    expenseList.add(e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
