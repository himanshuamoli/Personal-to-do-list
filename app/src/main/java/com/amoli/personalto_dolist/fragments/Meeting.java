package com.amoli.personalto_dolist.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amoli.personalto_dolist.Information;
import com.amoli.personalto_dolist.MainActivity;
import com.amoli.personalto_dolist.MyDatabase;
import com.amoli.personalto_dolist.R;
import com.amoli.personalto_dolist.RecycleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Meeting extends Fragment {


    Cursor c;
    MyDatabase database;
    List<Information> lst;
    RecycleAdapter adapter;
    RecyclerView rv;
    public Meeting() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_today, container, false);
        rv=(RecyclerView)v.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        lst=new ArrayList<Information>();
        database=new MyDatabase(getActivity());
        c=database.query("Meeting");
        if(c.moveToFirst()){
            do{
                Information info=new Information();
                info.title=c.getString(c.getColumnIndex("title"));
                info.desc=c.getString(c.getColumnIndex("desc"));
                info.place=c.getString(c.getColumnIndex("place"));
                info.date=c.getString(c.getColumnIndex("date"));
                info.time=c.getString(c.getColumnIndex("time"));
                lst.add(info);
            }while(c.moveToNext());
        }
        adapter=new RecycleAdapter(getActivity(),lst);
        rv.setAdapter(adapter);
        return v;
    }

}
