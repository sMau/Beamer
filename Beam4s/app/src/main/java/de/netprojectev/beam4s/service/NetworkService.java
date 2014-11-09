package de.netprojectev.beam4s.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import de.netprojectev.beam4s.MainActivity;
import de.netprojectev.client.Client;
import de.netprojectev.client.ClientGUI;
import de.netprojectev.client.model.PreferencesModelClientAndroid;
import de.netprojectev.networking.LoginData;

/**
 * Created by samu on 07.11.14.
 */
public class NetworkService extends Service implements ClientGUI {

    private final IBinder binder = new NetworkServiceBinder();
    private Client client;

    public class NetworkServiceBinder extends Binder {
        public NetworkService getService() {
            return NetworkService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        return binder;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        ArrayList<String> passed = intent.getStringArrayListExtra("values");

        try {
            client = new Client(passed.get(0), Integer.valueOf(passed.get(1)), new LoginData(passed.get(2),
                    passed.get(3)), this, PreferencesModelClientAndroid.class);
            client.connect();
        } catch (InstantiationException e) {
            Log.e("Login", "Error during login", e);
        } catch (IllegalAccessException e) {
            Log.e("Login", "Error during login", e);
        } catch (IllegalArgumentException e) {
            Log.e("Login", "Error during login", e);
        } catch (InvocationTargetException e) {
            Log.e("Login", "Error during login", e);
        } catch (SecurityException e) {
            Log.e("Login", "Error during login", e);
        } catch (InterruptedException e) {
            Log.e("Login", "Error during login", e);
        }
        return Service.START_NOT_STICKY;
    }

    @Override
    public void errorDuringLogin(String msg) {
        Log.e("Login", "Login failed: " + msg);
    }

    @Override
    public void errorRequestingFullsyncDialog() {
        // TODO Auto-generated method stub

    }

    @Override
    public void loginSuccess() {
        Log.d("Login", "Login success");
    }
}
