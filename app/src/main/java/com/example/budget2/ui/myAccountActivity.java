package com.example.budget2.ui;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.budget2.R;
import com.example.budget2.model.Expense;
import com.example.budget2.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class myAccountActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button mLogoutButton;
    private Button mAddExpensesButton;
    private Button mStatsButton;
    private GraphView graph;
    private Expense tempExpense;
    static final int ADD_EXPENSE_REQUEST = 1;
    public static Integer EXPENSE_ID = 0;
    //this must be of type ArrayList, you cannot use the List interface because of the putParcelableArrayListExtra method
    public ArrayList<Expense> expenseRecords;
    DatabaseReference mRootRef =  FirebaseDatabase.getInstance().getReference();
    DatabaseReference mChildRef = mRootRef.child("Expenses");
    FirebaseDatabase database;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        mLogoutButton = (Button)findViewById(R.id.logOutButton);
        mAddExpensesButton = (Button)findViewById(R.id.expenseButton);
        mStatsButton = (Button)findViewById(R.id.statsButton);
        graph = (GraphView)findViewById(R.id.graph);
        expenseRecords = new ArrayList<Expense>();
        database = mChildRef.getDatabase();

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                finish();
            }
        });

        mAddExpensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent expenseIntent = new Intent(myAccountActivity.this, ExpenseActivity.class);
                startActivityForResult(expenseIntent, ADD_EXPENSE_REQUEST);
                //Log.d("Expense test", expenseRecords.get(0).getNote());
            }
        });

        mStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent statsIntent = new Intent(myAccountActivity.this, viewStatsActivity.class);
                statsIntent.putParcelableArrayListExtra("expenseList", expenseRecords);
                startActivity(statsIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ValueEventListener expenseListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                expenseRecords.clear();

                DataSnapshot expensesSnapshot = dataSnapshot.child("Expense");
                Iterable<DataSnapshot> expenseChildren = expensesSnapshot.getChildren();
                for(DataSnapshot expense : expenseChildren) {
                    Expense e = expense.getValue(Expense.class);
                    expenseRecords.add(e);

                    //Log.d("Expense debug", expenseRecords.get(0).getNote());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    private void signOut() {
        mAuth.signOut();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_EXPENSE_REQUEST){
            if(resultCode == RESULT_OK){

                tempExpense = data.getParcelableExtra("Expense");
                expenseRecords.add(tempExpense);
                mRootRef.child("Expenses").child(tempExpense.getId().toString()).setValue(tempExpense);

            }
        }
    }
}
