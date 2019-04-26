package com.example.budget2.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.budget2.R;
import com.example.budget2.model.User;

public class editProfileActivity extends AppCompatActivity {
    private Button mCancelButton;
    private Button mSaveButton;
    private EditText mNameText;
    private EditText mProfessionText;
    private EditText mUtaIdText;
    private String mName;
    private String mProfession;
    private Integer mUtaID;
    private String mUtaIDS;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mCancelButton = findViewById(R.id.cancelButtonEditP);
        mSaveButton = findViewById(R.id.saveButtonEditP);
        mNameText = findViewById(R.id.nameEditText);
        mProfessionText = findViewById(R.id.professionEditText);
        mUtaIdText = findViewById(R.id.utaIDEditText);

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName = mNameText.getText().toString();
                mProfession = mProfessionText.getText().toString();
                mUtaIDS = mUtaIdText.getText().toString();

                try{
                    mUtaID = Integer.parseInt(mUtaIDS);
                } catch(NumberFormatException nfe){
                    Toast.makeText(editProfileActivity.this,
                            "That's not a number you dumb dumb",
                            Toast.LENGTH_LONG).show();
                    finish();
                }

                mUser = new User(mProfession, mName, mUtaID);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("user", mUser);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();

            }
        });
    }
}
