package com.mystartup.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    private EditText loginUserName;
    private EditText loginPassword;
    private Button loginButton;
    private EditText signUpUserName;
    private EditText signUpPassword;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        loginUserName = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signUpUserName =findViewById(R.id.sign_up_user_name);
        signUpPassword = findViewById(R.id.sign_up_password);
        signUpButton = findViewById(R.id.sign_up_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser signUpUser= new ParseUser();
                signUpUser.setUsername(signUpUserName.getText().toString());
                signUpUser.setPassword(signUpPassword.getText().toString());
                signUpUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e != null){
                            Toast.makeText(SignUpActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

        }

    });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ParseUser.logInInBackground(loginUserName.getText().toString(), loginPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user == null){
                            Toast.makeText(SignUpActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(SignUpActivity.this,"Login done Successfully",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
}
}