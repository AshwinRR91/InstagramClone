package com.mystartup.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class UsersPost extends AppCompatActivity {

    private LinearLayout mLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_post);
        Intent intent = getIntent();
        final String userName = intent.getStringExtra("username");
        mLinearLayout = findViewById(R.id.linearLayout);
        ParseQuery<ParseObject> parseQuery = new ParseQuery("Photo");
                parseQuery.whereEqualTo("Username",userName);
                parseQuery.orderByDescending("createdAt");
                parseQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(objects.size()>0 && e==null){
                            for(ParseObject parseObject: objects){
                                final TextView imageDes = new TextView(UsersPost.this);
                                imageDes.setText(parseObject.get("Description")+"");
                                ParseFile postPicture = (ParseFile) parseObject.get("Picture");
                                postPicture.getDataInBackground(new GetDataCallback() {
                                    @Override
                                    public void done(byte[] data, ParseException e) {
                                        if(data != null && e == null ){
                                            Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                            layoutParams.setMargins(5,5,5,5);
                                            ImageView imageView = new ImageView(UsersPost.this);
                                            imageView.setLayoutParams(layoutParams);
                                            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                            imageView.setImageBitmap(bitmap);
                                            imageDes.setLayoutParams(layoutParams);
                                            imageDes.setGravity(View.TEXT_ALIGNMENT_CENTER);
                                            imageDes.setBackgroundColor(Color.BLACK);
                                            imageDes.setTextColor(Color.WHITE);
                                            mLinearLayout.addView(imageView);
                                            mLinearLayout.addView(imageDes);

                                        }
                                    }
                                });
                            }
                        }
                    }
                });

    }
}