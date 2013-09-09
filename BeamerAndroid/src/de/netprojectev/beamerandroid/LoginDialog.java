package de.netprojectev.beamerandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

public class LoginDialog extends DialogFragment {

	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Login").setView(inflater.inflate(R.layout.login_dialog_fragment, null))
               .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   //http://developer.android.com/guide/topics/ui/dialogs.html
                       // FIRE ZE MISSILES!
                   }
               })
               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                	   
                   }
               });
        return builder.create();
    }
	
}
