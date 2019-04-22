package com.example.budget2.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.budget2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText mEmail, mPassword;
    private Button mLoginB, mNewAcctB;
    private FirebaseAuth mAuth;

    private static final String TAG = "EmailPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        mEmail = findViewById(R.id.eMail);
        mPassword = findViewById(R.id.password);
        mLoginB = findViewById(R.id.loginButton);
        mNewAcctB = findViewById(R.id.signUpButton);
        mAuth = FirebaseAuth.getInstance();

        mNewAcctB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String emailLogin = mEmail.getText().toString();
                String pwLogin = mPassword.getText().toString();
                createAccount(emailLogin, pwLogin);
            }
        });

        mLoginB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String emailLogin = mEmail.getText().toString();
                String pwLogin = mPassword.getText().toString();
                signIn(emailLogin, pwLogin);
            }
        });


    }

    //@Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void createAccount(String email, String password){
       mAuth.createUserWithEmailAndPassword(email, password)
               .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           //success, update UI with signed-in user info
                           Log.d(TAG, "createUserWithEmail:success");
                           FirebaseUser user = mAuth.getCurrentUser();
                           updateUI(user);
                       } else {
                           // sign in fails, dispaly fail message
                           Log.w(TAG, "createUserWithEmail:failure", task.getException());
                           Toast.makeText(MainActivity.this, "Authentication failed.",
                                   Toast.LENGTH_SHORT).show();
                           updateUI(null);
                           //TODO create a warning  that password must be at least 6 characters
                       }
                   }
               });
    }

    private void signIn(String email, String password){
        Log.d(TAG, "signIn:" + email);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null){
            Intent intent = new Intent(this, myAccountActivity.class);
            startActivity(intent);
        } else {
            mEmail.setText("");
            mPassword.setText("");
        }
    }
}
