package de.netprojectev.beamerandroid.ticker;

import de.netprojectev.beamerandroid.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TickerFragment extends Fragment {

	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.ticker_fragment, container, false);
	}
}
