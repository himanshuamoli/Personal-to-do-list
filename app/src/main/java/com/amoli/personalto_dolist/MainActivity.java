package com.amoli.personalto_dolist;

import android.app.Activity;

import android.app.ActionBar;
import com.github.clans.fab.FloatingActionButton;
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
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.support.v7.app.ActionBarDrawerToggle;

import com.amoli.personalto_dolist.fragments.Today;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab,fabdone;
    Toolbar toolbar;
    DrawerLayout mDrawer;
    ActionBarDrawerToggle mDrawerToggle;
    NavigationView nvDrawer;
    SlidingDrawer slidrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fabdone=(FloatingActionButton)findViewById(R.id.fabdone);
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


            }
        });

        fabdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidrawer.animateClose();
            }
        });

        //Default fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, new Today()).commit();
        nvDrawer.getMenu().getItem(0).setChecked(true);
        setTitle(nvDrawer.getMenu().getItem(0).getTitle());

    }

    public void selectItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.today:
                fragment =new Today();
                break;
            case R.id.meeting:

                break;
            case R.id.work:

                break;
            default:

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


}
