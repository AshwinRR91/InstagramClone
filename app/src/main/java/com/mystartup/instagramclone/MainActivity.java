package com.mystartup.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

private EditText userName;
private EditText loginEmail;
private EditText password;
private Button loginButton;
private Button signUpButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("INSTACLONE");
        userName = findViewById(R.id.user_name);
        loginEmail = findViewById(R.id.email_id);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.switch_to_login_button);
        signUpButton = findViewById(R.id.signup_buton);
        loginButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
        if(ParseUser.getCurrentUser()!= null){
            switchToSocialMediaActivity();
        }


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.signup_buton:

                if((userName.getText().toString().equals("") || loginEmail.getText().toString().equals("==")) || (password.getText().toString().equals("=="))) {
                    Toast.makeText(MainActivity.this, "Please enter the correct credentials", Toast.LENGTH_LONG).show();

                }
                else{
                    final ParseUser parseUser = new ParseUser();
                    parseUser.setUsername(userName.getText().toString());
                    parseUser.setEmail(loginEmail.getText().toString());
                    parseUser.setPassword(password.getText().toString());
                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "You have been signed up", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                break;

            case R.id.switch_to_login_button:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;


        }
    }
    public void rootLayoutTapped(View view){

        InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        try {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e ){
            Log.i("Error",e.getMessage());
        }


    }
    private void switchToSocialMediaActivity(){
        Intent intent = new Intent(MainActivity.this,SocialMediaActivity.class);
        startActivity(intent);
    }

}