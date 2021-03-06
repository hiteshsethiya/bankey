package com.fartans.bankey.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fartans.bankey.R;
import com.fartans.bankey.db.DbHandler;
import com.fartans.bankey.helper.StaticHelper;
import com.fartans.bankey.model.UserModel;


public class SignupActivity extends AppCompatActivity {
    AppCompatButton btnSignUp;
    EditText editTextUserName;
    EditText editTextPassword;
    EditText editTextConfirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        StaticHelper.IsFromSignUp = true;

        this.setTitle("Sign Up");

        editTextPassword = (EditText)findViewById(R.id.input_password_edittext);
        editTextConfirmPassword = (EditText)findViewById(R.id.input_confirmpassword_edittext);
        btnSignUp = (AppCompatButton)findViewById(R.id.btn_signup);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = "user";
                String userPassword = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();
                UserModel userModel = new UserModel();

                if(!userPassword.matches("") &&  !userName.matches("") && !confirmPassword.matches(""))
                {
                    if(!userPassword.equals(confirmPassword))
                    {
                        Toast.makeText(getApplicationContext(), "Password doesn't Match ", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        userModel.UserName = userName;
                        userModel.Password = Integer.parseInt(userPassword);

                        DbHandler.insertUser(getApplicationContext(), userModel);

                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }
}
