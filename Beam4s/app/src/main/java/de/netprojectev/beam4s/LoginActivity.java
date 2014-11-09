package de.netprojectev.beam4s;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;

import java.util.ArrayList;

import de.netprojectev.beam4s.service.NetworkService;
import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;


public class LoginActivity extends RoboActionBarActivity implements Validator.ValidationListener{

    private Validator mValidator;


    @InjectView(R.id.etPassword)
    private EditText etPassword;
    @Required(order = 2, messageResId = R.string.message_port_required)
    @InjectView(R.id.etPort)
    private EditText etPort;
    @Required(order = 3, messageResId = R.string.message_name_required)
    @InjectView(R.id.etUsername)
    private EditText etUsername;
    @Required(order = 1, messageResId = R.string.message_server_required)
    @InjectView(R.id.etServerIP)
    private EditText etServerIP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    public void onLoginClicked(View v) {
        mValidator.validate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onValidationSucceeded() {
        etServerIP.setError(null);
        etPort.setError(null);
        etUsername.setError(null);
        etPassword.setError(null);

        String serverIP = etServerIP.getText().toString();
        String port = etPort.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        ArrayList<String> toPass = new ArrayList<String>();
        toPass.add(serverIP);
        toPass.add(port);
        toPass.add(username);
        toPass.add(password);

        Intent intent = new Intent(this, NetworkService.class);
        intent.putStringArrayListExtra("values", toPass);

        startService(intent);

        // TODO loading bar for login, then show the activity

        // startActivity(intent);

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        if(failedView instanceof EditText){
            failedView.requestFocus();
            ((EditText)failedView).setError(failedRule.getFailureMessage());
        }

    }

}
