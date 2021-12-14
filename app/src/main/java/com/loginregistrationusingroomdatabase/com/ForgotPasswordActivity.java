package com.loginregistrationusingroomdatabase.com;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

public class ForgotPasswordActivity extends AppCompatActivity {
       TextInputLayout textInputOTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();
        textInputOTP=findViewById(R.id.textInputOTP);
        textInputOTP.setVisibility(View.GONE);
    }

    public void onRegisterClick(View view) {
        startActivity(new Intent(this,RegistrationActivity.class));

    }

    public void cirSubmitButton(View view) {
        textInputOTP.setVisibility(View.VISIBLE);
    }
}