package de.netprojectev.beamerandroid;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
import de.netprojectev.beamerandroid.LoginDialog.LoginDialogListener;
import de.netprojectev.beamerandroid.media.AllMediaFragment;
import de.netprojectev.beamerandroid.media.MediaQueueFragment;
import de.netprojectev.beamerandroid.networking.NetworkService;
import de.netprojectev.beamerandroid.ticker.TickerFragment;
import de.netprojectev.client.ClientGUI;
import de.netprojectev.networking.LoginData;


public class Main extends ActionBarActivity implements LoginDialogListener, ClientGUI {

	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;

	private NetworkService network;
	private ServiceConnection networkServiceConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder binder) {
			Log.d("", "NetworkService connected to main activity");
			network = ((NetworkService.NetworkServiceBinder) binder).getService();
			
			onServiceBoundSuccess();
		}

		public void onServiceDisconnected(ComponentName className) {
			Log.d("", "NetworkService disconnected from main activity");
		}

	};		

	private void onServiceBoundSuccess() {
		if(!network.isLoggedIn()) {
			new LoginDialog().show(getSupportFragmentManager(), "Login");
		}
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		doBindService();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		unbindService(networkServiceConnection);
	}
	
	private void doBindService() {
		bindService(new Intent(this, NetworkService.class), networkServiceConnection, Context.BIND_DEBUG_UNBIND);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		getSupportActionBar().setSubtitle("Overview");

		startService(new Intent(this, NetworkService.class));

		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return new AllMediaFragment();
			case 1:
				return new MediaQueueFragment();
			case 2:
				return new TickerFragment();
			default:
				return null;
			}
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section_allmedia).toUpperCase(l);
			case 1:
				return getString(R.string.title_section_mediaqueue).toUpperCase(l);
			case 2:
				return getString(R.string.title_section_ticker).toUpperCase(l);
			}
			return null;
		}
	}


	@Override
	public void onLoginClicked(String ip, int port, LoginData login) {
		network.login(login, ip, port, this);
	}

	@Override
	public void onCancelClicked() {
		finish();
	}

	@Override
	public void errorDuringLogin(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	@Override
	public void loginSuccess() {
		Toast.makeText(this, "Login Success", Toast.LENGTH_LONG).show();		
	}

	@Override
	public void errorRequestingFullsyncDialog() {
		Toast.makeText(this, "A network error occured. Trying to sync again.", Toast.LENGTH_LONG).show();	
	}
}
