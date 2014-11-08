package de.netprojectev.beam4s;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;

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
        String password = etPassword.getText().toString();
        String serverIP = etServerIP.getText().toString();
        String username = etUsername.getText().toString();
        String port = etPort.getText().toString();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.KEY_IP, serverIP);
        intent.putExtra(MainActivity.KEY_PASSWORD, password);
        intent.putExtra(MainActivity.KEY_USERNAME, username);
        intent.putExtra(MainActivity.KEY_PORT, Integer.valueOf(port));
        startActivity(intent);

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {

    }
}
