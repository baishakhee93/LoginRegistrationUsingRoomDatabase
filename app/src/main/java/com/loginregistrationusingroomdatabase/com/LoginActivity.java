package com.loginregistrationusingroomdatabase.com;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    public static TextInputLayout textInputLayoutEmail,textInputLayoutPassword;
    @SuppressLint("StaticFieldLeak")
    public static TextInputEditText textInputEditTextEmail,textInputEditTextPassword;

    @SuppressLint("StaticFieldLeak")
    public static InputValidation inputValidation;
    @SuppressLint("StaticFieldLeak")
    public static AppDatabase appDatabase;
    @SuppressLint("StaticFieldLeak")
    public static User user;
    @SuppressLint("StaticFieldLeak")
    public static Activity LA;
    @SuppressLint("StaticFieldLeak")
    public static ScrollView scrollable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LA=this;
        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();
        scrollable=findViewById(R.id.scrollable);
        textInputLayoutEmail=findViewById(R.id.textInputEmail);
        textInputLayoutPassword=findViewById(R.id.textInputPassword);
        textInputEditTextEmail=findViewById(R.id.editTextEmail);
        textInputEditTextPassword=findViewById(R.id.editTextPassword);
        appDatabase = AppDatabase.getAppDatabase(LA);
        inputValidation = new InputValidation(LA);
        user = new User();
    }

    public void cirLoginButton(View view) {
        verifyFromSQLite();
        startActivity(new Intent(this,MainActivity.class));

    }



    public void facebookBtn(View view) {

    }

    public void gmailBtn(View view) {
    }


    public void onRegisterClick(View view) {
        startActivity(new Intent(this,RegistrationActivity.class));

    }

    public void onForgotPwd(View view) {
        startActivity(new Intent(this,ForgotPasswordActivity.class));

    }

    public void registerClick(View view) {
        startActivity(new Intent(this,RegistrationActivity.class));

    }
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return;
        }

        User user = appDatabase.userDao().findByName(textInputEditTextEmail.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim());
        User user1 = appDatabase.userDao().findByPhone(textInputEditTextEmail.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim());

        if(user != null) {
            if (user.getEmail().equalsIgnoreCase(textInputEditTextEmail.getText().toString().trim()) &&
                    user.getPassword().equalsIgnoreCase(textInputEditTextPassword.getText().toString().trim())) {
                Intent accountsIntent = new Intent(LA, MainActivity.class);
                emptyInputEditText();
                startActivity(accountsIntent);

            } else {
                // Snack Bar to show success message that record is wrong
                Snackbar.make(scrollable, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
            }
        }else {
           Snackbar.make(scrollable, "User not available", Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}