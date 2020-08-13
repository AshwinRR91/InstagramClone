package com.mystartup.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {
    private TextView helloWorld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helloWorld = findViewById(R.id.hello_word);
        helloWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseObject boxer = new ParseObject("Boxer");
                boxer.put("PunchSpeed", 200);
                boxer.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e!=null){
                            Toast.makeText(MainActivity.this,"Saved in Server",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }


}