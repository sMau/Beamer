package de.netprojectev.beam4s.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;

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

        try {
            client = new Client(intent.getStringExtra(MainActivity.KEY_IP), intent.getIntExtra(MainActivity.KEY_PORT, 11111), new LoginData(intent.getStringExtra(MainActivity.KEY_USERNAME),
                    intent.getStringExtra(MainActivity.KEY_PASSWORD)), this, PreferencesModelClientAndroid.class);
            client.connect();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Service.START_NOT_STICKY;
    }

    @Override
    public void errorDuringLogin(String msg) {
        Log.d("Login", "Login failed");
        Log.d("Login", msg);
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
