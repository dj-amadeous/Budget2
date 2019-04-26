package com.example.budget2.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.budget2.R;
import com.example.budget2.model.Expense;
import com.example.budget2.model.Income;
import com.example.budget2.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class myAccountActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button mLogoutButton;
    private Button mAddExpensesButton;
    private Button mStatsButton;
    private Button mIncomeButton;
    private Button mEditProfileButton;
    private TextView mNameText;
    private TextView mProfessionText;
    private TextView mUTAIDText;

    //private GraphView graph;

    static final int ADD_EXPENSE_REQUEST = 1;
    static final int ADD_INCOME_REQUEST = 2;
    static final int EDIT_PROFILE_REQUEST = 3;
    public static Integer EXPENSE_ID = 0;
    private Income tempIncome;
    private Expense tempExpense;
    private User mUser;

    public static Integer INCOME_ID = 0;
    //this must be of type ArrayList, you cannot use the List interface because of the putParcelableArrayListExtra method
    public ArrayList<Expense> expenseRecords;
    public ArrayList<Income> incomeRecords;
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
        mIncomeButton = (Button)findViewById(R.id.incomeButton);
        mEditProfileButton = (Button)findViewById(R.id.editProfile);
        mNameText = (TextView)findViewById(R.id.nameTextView);
        mProfessionText = (TextView)findViewById(R.id.professionTextView);
        mUTAIDText = (TextView)findViewById(R.id.utaIDTextView);
        expenseRecords = new ArrayList<Expense>();
        incomeRecords = new ArrayList<Income>();
        database = mChildRef.getDatabase();

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                finish();
            }
        });

        getSupportActionBar().hide();
        displayUser();

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
                //statsIntent.putParcelableArrayListExtra("expenseList", expenseRecords);

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("expenseList", expenseRecords);
                bundle.putParcelableArrayList("incomeList", incomeRecords);
                statsIntent.putExtras(bundle);

                startActivity(statsIntent);
            }
        });

        mIncomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent incomeIntent = new Intent(myAccountActivity.this, IncomeActivity.class);
                startActivityForResult(incomeIntent, ADD_INCOME_REQUEST);
            }
        });

        mEditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(myAccountActivity.this, editProfileActivity.class);
                startActivityForResult(profileIntent, EDIT_PROFILE_REQUEST);
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
        if(requestCode == ADD_INCOME_REQUEST){
            if(resultCode == RESULT_OK){

                tempIncome = data.getParcelableExtra("Income");
                incomeRecords.add(tempIncome);
                mRootRef.child("Incomes").child(tempIncome.getId().toString()).setValue(tempIncome);

            }
        }
        if (requestCode == EDIT_PROFILE_REQUEST) {
            if (resultCode == RESULT_OK) {
                mUser = data.getParcelableExtra("user");
            }
        }
        displayUser();
    }
    private void displayUser() {
        if(mUser != null){
            mNameText.setText(mUser.getName());
            mProfessionText.setText(mUser.getProfession());
            mUTAIDText.setText(mUser.getUtaID().toString());

        }
    }
}
