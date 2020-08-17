package com.mystartup.instagramclone;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UsersTab extends Fragment implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private ArrayList <String>mArrayList;
    private ArrayAdapter mArrayAdapter;


    public UsersTab() {
        // Required empty public constructor
    }
    public static UsersTab newInstance(String param1, String param2) {
        UsersTab fragment = new UsersTab();
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
        View view = inflater.inflate(R.layout.fragment_users_tab, container, false);
        mListView = view.findViewById(R.id.listView);
        mArrayList = new ArrayList();
        mArrayAdapter = new ArrayAdapter(Objects.requireNonNull(getContext()),android.R.layout.simple_list_item_1,mArrayList);

        ParseQuery<ParseUser> mParseQuery = ParseUser.getQuery();
        mListView.setOnItemClickListener(this);
        mParseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());

        mParseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e == null) {
                    if (objects.size() > 0) {

                        for(ParseUser mParseUser : objects){
                            mArrayList.add(mParseUser.getUsername());
                        }
                        mListView.setAdapter(mArrayAdapter);

                    }
                }

            }
        });


        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getContext(), UsersPost.class);
        intent.putExtra("username",mArrayList.get(i));
        startActivity(intent);
    }
}