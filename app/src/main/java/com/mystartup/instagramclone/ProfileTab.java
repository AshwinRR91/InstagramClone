package com.mystartup.instagramclone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ProfileTab extends Fragment {

    private EditText profileName,bioText, professionText, hobbiesText, sportText;
    private Button updateInfo;


    public ProfileTab() {
        // Required empty public constructor
    }
    public static ProfileTab newInstance(String param1, String param2) {
        ProfileTab fragment = new ProfileTab();
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
        View view  = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        profileName = view.findViewById(R.id.profileText);
        bioText = view.findViewById(R.id.bioText);
        professionText = view.findViewById(R.id.professionText);
        sportText = view.findViewById(R.id.sportText);
        hobbiesText= view.findViewById(R.id.hobbiesText);
        updateInfo = view.findViewById(R.id.updateInfo);
        final ParseUser parseUser = ParseUser.getCurrentUser();
        if(parseUser.get("Profile_Name")!=null){
            profileName.setText(parseUser.get("Profile_Name").toString());
        }
        if(parseUser.get("Bio")!=null){
            bioText.setText(parseUser.get("Bio").toString());
        }
        if(parseUser.get("Sport")!=null){
            sportText.setText(parseUser.get("Sport").toString());
        }
        if(parseUser.get("Hobbies")!=null){
            hobbiesText.setText(parseUser.get("Hobbies").toString());
        }
        if(parseUser.get("Profession")!=null){
            professionText.setText(parseUser.get("Profession").toString());
        }

        updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                parseUser.put("Sport", sportText.getText().toString());
                parseUser.put("Profile_Name",profileName.getText().toString());
                parseUser.put("Bio",bioText.getText().toString());
                parseUser.put("Sport", sportText.getText().toString());
                parseUser.put("Hobbies", hobbiesText.getText().toString());
                parseUser.put("Profession", professionText.getText().toString());
                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e != null){
                            Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });
        return view;
    }
}