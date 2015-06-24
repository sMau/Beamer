package de.netprojectev.beam4s;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

import de.netprojectev.beam4s.service.NetworkService;
import de.netprojectev.datastructures.TickerElement;
import roboguice.activity.RoboActionBarActivity;


public class MainActivity extends RoboActionBarActivity implements ActionBar.TabListener, MediaFragment.OnFragmentInteractionListener, TickerFragment.OnFragmentInteractionListener, QueueFragment.OnFragmentInteractionListener {

    //TODO last worked here in android app
    /*
    preview activity for images
    add media file
    add themeslide
    controls like next or previous
    show the current file
     */

    private boolean serviceConnected = false;
    private boolean firstStart = true;

    private NetworkService networkService;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder binder) {
            NetworkService.NetworkServiceBinder b = (NetworkService.NetworkServiceBinder) binder;
            networkService = b.getService();
            serviceConnected = true;

            if(firstStart) {
                setUpTabs();
                firstStart = false;
            }

        }

        public void onServiceDisconnected(ComponentName className) {
            networkService = null;
        }
    };

    private void setUpTabs() {
        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager
                .setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar.setSelectedNavigationItem(position);
                    }
                });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(actionBar.newTab()
                    .setText(mSectionsPagerAdapter.getPageTitle(i))
                    .setTabListener(MainActivity.this));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, NetworkService.class);
        getApplicationContext().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(serviceConnected) {
            getApplicationContext().unbindService(mConnection);
            serviceConnected = false;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.signOff:
                networkService.getClient().disconnect();
                finish();
                return true;
            case R.id.addMediaFile:
                // TODO add media file
                return true;
            case R.id.addTickerElt:

                LayoutInflater inflater = getLayoutInflater();
                final View v = inflater.inflate(R.layout.add_ticker_element_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(v);
                builder.setTitle(R.string.addTickerElt)
                        .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                EditText tickerText = (EditText) v.findViewById(R.id.etTickerElt);
                                networkService.getClient().getProxy().sendAddTickerElement(tickerText.getText().toString());
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                // Create the AlertDialog object and return it
                 builder.create();

                return true;
            case R.id.addThemeslide:
                // TODO add theme slide
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab,
                              FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab,
                                FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab,
                                FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MediaFragment.newInstance(networkService.getClient().getProxy().getMediaModel());
                case 1:
                    return TickerFragment.newInstance(networkService.getClient().getProxy().getTickerModel(), networkService.getClient().getProxy());
                case 2:
                    return QueueFragment.newInstance(networkService.getClient().getProxy().getMediaModel());
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    public NetworkService getNetworkService() {
        return networkService;
    }

}
