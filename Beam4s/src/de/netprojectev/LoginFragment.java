package de.netprojectev;

import de.netprojectev.service.NetworkService;
import android.content.Intent;
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
		Intent i = new Intent(getActivity(), NetworkService.class);
		// potentially add data to the intent
		i.putExtra("KEY1", "Value to be used by the service");
		getActivity().startService(i); 
		
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_login, container, false);
		return rootView;
	}
}
	

