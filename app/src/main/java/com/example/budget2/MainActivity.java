package com.example.budget2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
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


    }

    @Override
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
                       }
                   }
               });
    }

    private void updateUI(FirebaseUser currentUser) {
    }
}
