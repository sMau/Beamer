package de.netprojectev.beamerandroid.networking;

import java.lang.reflect.InvocationTargetException;

import de.netprojectev.client.Client;
import de.netprojectev.client.ClientGUI;
import de.netprojectev.client.model.PreferencesModelClient;
import de.netprojectev.client.model.PreferencesModelClientAndroid;
import de.netprojectev.client.networking.ClientMessageProxy;
import de.netprojectev.networking.LoginData;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class NetworkService extends Service {

	private final IBinder binder = new NetworkServiceBinder();

	private Client client;
	private ClientMessageProxy proxy;
	private boolean loggedIn;
	
	
	public class NetworkServiceBinder extends Binder {
		public NetworkService getService() {
			return NetworkService.this;
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("", "Service started");
		return START_STICKY;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		loggedIn = false;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
	
	public void login(final LoginData login, final String ip, final int port, final ClientGUI gui) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					client = new Client(ip, port, login, gui, PreferencesModelClientAndroid.class);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				client.connect();
				proxy = client.getProxy();
				loggedIn = true;
			}
		}).start();
		
		
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public ClientMessageProxy getProxy() {
		return proxy;
	}

}
