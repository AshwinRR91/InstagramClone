package com.mystartup.instagramclone;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class SharePictureTab extends Fragment implements View.OnClickListener{

    private ImageView imageToShare;
    private EditText description;
    private Button shareButton;
    Bitmap receivedImageBitmap;
    private ProgressBar mProgressBar;
    Drawable draw;

    public SharePictureTab() {
    }
    public static SharePictureTab newInstance(String param1, String param2) {
        SharePictureTab fragment = new SharePictureTab();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_picture_tab, container, false);
        imageToShare = view.findViewById(R.id.image_to_share);
        description = view.findViewById(R.id.description);
        shareButton  = view.findViewById(R.id.share);
        shareButton.setOnClickListener(this);
        imageToShare.setOnClickListener(this);
        draw = ResourcesCompat.getDrawable(getResources(),R.drawable.circular_progress_bar,null);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mProgressBar.setProgressDrawable(draw);

        return view;

    }



    private void getChosenImage(){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1000);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){

            if(resultCode == Activity.RESULT_OK){
                Uri selectedImageUri = data.getData();
                String[] FilePath = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImageUri,FilePath,null,null,null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(FilePath[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                receivedImageBitmap = BitmapFactory.decodeFile(picturePath);
                imageToShare.setImageBitmap(receivedImageBitmap);
            }
        }



    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.image_to_share:
                if(Build.VERSION.SDK_INT>23&& ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1000);
                }
                else{
                    getChosenImage();
                }
                break;
            case R.id.share:
                if (receivedImageBitmap != null) {

                    if(description.getText()!= null){
                        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
                        receivedImageBitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                        byte[] bytesArray = byteArrayOutputStream.toByteArray();
                        ParseFile parseFile = new ParseFile("Image.png",bytesArray);
                        ParseObject Photo = new ParseObject("Photo");
                        Photo.put("Picture", parseFile);
                        Photo.put("Description", description.getText().toString());
                        Photo.put("Username", ParseUser.getCurrentUser().getUsername());
                        mProgressBar.setVisibility(View.VISIBLE);
                        Photo.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e!=null){
                                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                }
                                else{
                                    mProgressBar.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(getContext(),"Enter Description",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getContext(),"Please select Image",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}