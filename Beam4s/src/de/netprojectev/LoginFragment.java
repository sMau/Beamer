package de.netprojectev;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LoginFragment extends Fragment {

	public static LoginFragment newInstance() {
		LoginFragment fragment = new LoginFragment();

		return fragment;
	}

	private LoginFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		
		
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_login, container, false);
		return rootView;
	}
}
	

