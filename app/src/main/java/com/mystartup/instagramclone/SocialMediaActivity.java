package com.mystartup.instagramclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class SocialMediaActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;;
    private TabLayout mTabLayout;
    TabAdapter mTabAdapter;
    private ParseObject Photo;
    private RelativeLayout mRelativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        setTitle("CrAcKeRs");
        mToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(mToolbar);
        mViewPager = findViewById(R.id.view_pager);
        mTabAdapter = new TabAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(mTabAdapter);
        mTabLayout = findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.postImageItem :
                if(Build.VERSION.SDK_INT>23&& checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2000);
                }
                else{  captureImage();
                }
                break;
            case R.id.logout:
                ParseUser.logOut();
                Intent intent = new Intent(SocialMediaActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3000){

            if(resultCode == Activity.RESULT_OK){
                Uri selectedImageUri = data.getData();
                String[] FilePath = {MediaStore.Images.Media.DATA};
                Cursor cursor = this.getContentResolver().query(selectedImageUri,FilePath,null,null,null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(FilePath[0]);
                String picturePath = cursor.getString(columnIndex);
                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                cursor.close();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                byte[] bot = byteArrayOutputStream.toByteArray();
                ParseFile parseFile = new ParseFile("Image.png",bot);
                Photo = new ParseObject("Photo");
                Photo.put("Picture",parseFile);
                Photo.put("Username", ParseUser.getCurrentUser().getUsername());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Photo.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e != null) {
                                    Toast.makeText(SocialMediaActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

                                }
                            }
                        });
                    }
                }).start();

                }
        }


    }

    private void captureImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,3000);

    }

}