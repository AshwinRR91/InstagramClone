package com.mystartup.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText namePerson;
    private EditText nameRocket;
    private EditText nameSinger;
    private Button saveButton;
    private Button fetchButton;
    private String str;
    private Button switchToSignUP;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        namePerson = findViewById(R.id.editTextTextPersonName);
        nameRocket = findViewById(R.id.rocket);
        nameSinger = findViewById(R.id.singer);
        fetchButton = findViewById(R.id.fetch_details);
        saveButton = findViewById(R.id.save_details);
        fetchButton = findViewById(R.id.fetch_details);
        switchToSignUP = findViewById(R.id.switch_to_signup);
        switchToSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(signUp);

            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ParseObject Likes = new ParseObject("Likes");
                Likes.put("PersonName",namePerson.getText().toString());
                Likes.put("RocketName",nameRocket.getText().toString());

                Likes.put("SingerName",nameSinger.getText().toString());
                Likes.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e != null){
                        Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }}
                }); }
        });

        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str = "";
                ParseQuery<ParseObject> getDetails = ParseQuery.getQuery("Likes");
                getDetails.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            for (ParseObject pq : objects) {
                                str += pq.get("PersonName") + "" + pq.get("SingerName") + "" + pq.get("RocketName");
                            }
                        } else {
                            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }});
            }
        });
    }
}