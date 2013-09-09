package de.netprojectev.beamerandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.webkit.WebView.FindListener;
import android.widget.EditText;
import android.widget.Toast;
import de.netprojectev.misc.Misc;
import de.netprojectev.networking.LoginData;

public class LoginDialog extends DialogFragment {

	public interface LoginDialogListener {
		public void onLoginClicked(String ip, int port, LoginData login);
		public void onCancelClicked();
	}

	private LoginDialogListener listener;

	public Dialog onCreateDialog(Bundle savedInstanceState) {

		LayoutInflater inflater = getActivity().getLayoutInflater();

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Login").setView(inflater.inflate(R.layout.login_dialog_fragment, null))
				.setPositiveButton("Login", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						
						EditText aliasEt = (EditText) getActivity().findViewById(R.id.etAlias);
						EditText ipEt = (EditText) getActivity().findViewById(R.id.etHost);
						EditText portEt = (EditText) getActivity().findViewById(R.id.etPort);
						EditText passwordEt = (EditText) getActivity().findViewById(R.id.etPassword);
						
						String alias = aliasEt.getText().toString().trim();
						String ip = ipEt.getText().toString().trim();
						String password = passwordEt.getText().toString();
						String portAsString = portEt.getText().toString().trim();
						int port;

						if (alias.length() == 0) {
							Toast.makeText(getActivity(), "The choosen alias is not valid.", Toast.LENGTH_SHORT).show();
							return;
						}

						if (!Misc.isIpAddress(ip)) {
							Toast.makeText(getActivity(), "The ip address is not valid.", Toast.LENGTH_SHORT).show();
							return;
						}

						try {
							port = Integer.parseInt(portAsString);
						} catch (NumberFormatException e) {
							Toast.makeText(getActivity(), "The port is not valid.", Toast.LENGTH_SHORT).show();
							return;
						}

						listener.onLoginClicked(ip, port, new LoginData(alias, password));
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						listener.onCancelClicked();
					}
				});
		return builder.create();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (LoginDialogListener) activity;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
