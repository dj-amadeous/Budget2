package com.example.budget2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class myAccountActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button mLogoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        mLogoutButton = findViewById(R.id.logOutButton);
        //Intent intent = getIntent();
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                finish();
            }
        });
    }

    private void signOut() {
        mAuth.signOut();
    }


}
