package com.loginregistrationusingroomdatabase.com;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegistrationActivity extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    public static TextInputLayout textInputLayoutName,textInputLayoutEmail,textInputLayoutPhone,textInputLayoutPassword,textInputLayoutConfirmPassword;
     @SuppressLint("StaticFieldLeak")
    public static TextInputEditText textInputEditTextName,textInputEditTextEmail,textInputEditTextPassword,textInputEditTextPhone,textInputEditTextConfirmPassword;
     @SuppressLint("StaticFieldLeak")
    public static ScrollView scrollable;

    private InputValidation inputValidation;
    //private DatabaseHelper databaseHelper;
    @SuppressLint("StaticFieldLeak")
    public static User user;
    @SuppressLint("StaticFieldLeak")
    public static AppDatabase appDatabase;
    @SuppressLint("StaticFieldLeak")
    public static Activity RA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        RA=this;
        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();
       changeStatusBarColor();
        appDatabase = AppDatabase.getAppDatabase(RA);
        inputValidation = new InputValidation(RA);
        // databaseHelper = new DatabaseHelper(activity);
        user = new User();
        scrollable = (ScrollView) findViewById(R.id.scrollable);
        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputName);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputEmail);
        textInputLayoutPhone = (TextInputLayout) findViewById(R.id.textInputMobile);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputPassword);
        textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.textInputConfimPassword);

        textInputEditTextName = (TextInputEditText) findViewById(R.id.editTextName);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.editTextEmail);
        textInputEditTextPhone = (TextInputEditText) findViewById(R.id.editTextMobile);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.editTextPassword);
        textInputEditTextConfirmPassword = (TextInputEditText) findViewById(R.id.editTextConfirmPassword);
    }

    public void cirRegisterButton(View view) {
        postDataToSQLite();
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this,LoginActivity.class));
       // overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }
    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        } if (!inputValidation.isInputEditTextPhone(textInputEditTextPhone, textInputLayoutPhone, getString(R.string.error_message_phone))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return;
        }



        User user = appDatabase.userDao().findByEmail(textInputEditTextEmail.getText().toString().trim());
        if (user != null && !user.getEmail().equalsIgnoreCase(textInputEditTextEmail.getText().toString().trim())) {

            user.setName(textInputEditTextName.getText().toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());
            appDatabase.userDao().insertAll(user);
            // Snack Bar to show success message that record saved successfully
            Snackbar.make(scrollable, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();


        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(scrollable, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }


    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPhone.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }
}