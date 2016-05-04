package com.amoli.personalto_dolist;

import android.app.Activity;

import android.app.ActionBar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amoli.personalto_dolist.fragments.DatePicker;
import com.amoli.personalto_dolist.fragments.Meeting;
import com.amoli.personalto_dolist.fragments.TimePicker;
import com.amoli.personalto_dolist.fragments.Work;
import com.github.clans.fab.FloatingActionButton;

import android.location.LocationManager;
import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.Toast;

import com.amoli.personalto_dolist.fragments.Today;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.sql.Time;

public class MainActivity extends AppCompatActivity {
    int flag=0;
    MyDatabase database;
    FloatingActionButton fab,fabdone;
    Toolbar toolbar;
    DrawerLayout mDrawer;
    ActionBarDrawerToggle mDrawerToggle;
    NavigationView nvDrawer;
    SlidingDrawer slidrawer;
   public Button location,date,time,category;
    static final int PLACE_PICKER_REQUEST = 1;
    EditText title,desc;
    String pl=null;
    final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    String cat[]={"Meeting","Work"},cate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database=new MyDatabase(this);
        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fabdone=(FloatingActionButton)findViewById(R.id.fabdone);
        date=(Button)findViewById(R.id.date);
        time=(Button)findViewById(R.id.time);
        category=(Button)findViewById(R.id.category);
        title=(EditText)findViewById(R.id.title);
        desc=(EditText)findViewById(R.id.description);
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer=(NavigationView)findViewById(R.id.nvView);
        slidrawer=(SlidingDrawer)findViewById(R.id.slidingDrawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.syncState();

        nvDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectItem(menuItem);
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidrawer.animateOpen() ;
                pl=null;
                title.setText("");
                desc.setText("");
                date.setText("Date");
                time.setText("Time");;
                location.setText("Pick a Location");
                DatePicker.year=null;
                DatePicker.day=null;
                DatePicker.month=null;
                TimePicker.hour=null;
                TimePicker.minute=null;
                DatePicker.date=null;
                TimePicker.time=null;
                flag=1;

            }
        });

        fabdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString().equals(""))
                    Toast.makeText(MainActivity.this, "Enter Title", Toast.LENGTH_SHORT).show();
                else if(desc.getText().toString().equals(""))
                    Toast.makeText(MainActivity.this, "Enter Description", Toast.LENGTH_SHORT).show();
                else if(DatePicker.day==null)
                    Toast.makeText(MainActivity.this,"Select Date",Toast.LENGTH_SHORT).show();
                else if(TimePicker.minute==null)
                    Toast.makeText(MainActivity.this, "Select Time", Toast.LENGTH_SHORT).show();

                else {
                    slidrawer.animateClose();
                    flag=0;
                    database.insertData(title.getText().toString(),desc.getText().toString(),cate,pl,DatePicker.date,TimePicker.time);
                }
            }
        });

        //Default fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, new Work()).commit();
        nvDrawer.getMenu().getItem(0).setChecked(true);
        setTitle(nvDrawer.getMenu().getItem(0).getTitle());

        location=(Button)findViewById(R.id.location);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PlacePicker.IntentBuilder intentBuilder =
                            new PlacePicker.IntentBuilder();
                    intentBuilder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
                    Intent intent = intentBuilder.build(MainActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException
                        | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePicker(MainActivity.this);
                newFragment.show(getSupportFragmentManager(),"datepicker");
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment=new TimePicker(MainActivity.this);
                newFragment.show(getSupportFragmentManager(),"timepicker");
            }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(MainActivity.this)
                        .title("Category")
                        .items(cat)
                        .itemsCallbackSingleChoice(1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                /**
                                 * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                                 * returning false here won't allow the newly selected radio button to actually be selected.
                                 **/

                                cate=cat[which];
                                category.setText(cate);
                                return true;
                            }
                        })
                        .positiveText("Done")
                        .show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK) {

            final Place place = PlacePicker.getPlace(this, data);
            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            String attributions = (String) place.getAttributions();
            if (attributions == null) {
                attributions = "";
            }
            StringBuilder s=new StringBuilder();
            s.append(name);
            s.append("\n");
            s.append(address);
            location.setText(s.toString());
            pl=s.toString();
            //mAttributions.setText(Html.fromHtml(attributions));

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void selectItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.work:
                fragment =new Work();
                break;
            case R.id.meeting:
                fragment=new Meeting();
                break;


        }

       /* try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle state) {
        super.onPostCreate(state);
    }

    @Override
    public void onBackPressed() {
          if(flag==1) {
              slidrawer.animateClose();
              flag=0;
          }
          else
              super.onBackPressed();

    }


}
