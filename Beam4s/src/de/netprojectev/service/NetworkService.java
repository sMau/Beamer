package de.netprojectev.service;

import de.netprojectev.client.Client;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class NetworkService extends Service {

	private Client client;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public Client getClient() {
		return client;
	}

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}
	
}
