package de.netprojectev.beamerandroid;

import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import de.netprojectev.beamerandroid.media.AllMediaFragment;
import de.netprojectev.beamerandroid.media.MediaQueueFragment;
import de.netprojectev.beamerandroid.ticker.TickerFragment;

public class Main extends ActionBarActivity {

	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		getSupportActionBar().setSubtitle("Overview");

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
}
