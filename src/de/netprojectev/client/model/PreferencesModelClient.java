package de.netprojectev.client.model;

import de.netprojectev.client.networking.ClientMessageProxy;

public class PreferencesModelClient {

	private final ClientMessageProxy proxy;
	
	private boolean automode;
	private boolean fullscreen;
	private boolean liveTickerRunning;
	
	
	public PreferencesModelClient(ClientMessageProxy proxy) {
		this.proxy = proxy;
		
	}

	public void toggleAutomode() {
		automode = !automode;
	}
	
	public void toggleFullscreen() {
		fullscreen = !fullscreen;
	}
	
	public void toggleLiveTickerRunning() {
		liveTickerRunning = !liveTickerRunning;
	}
	
	public boolean isAutomode() {
		return automode;
	}

	public boolean isFullscreen() {
		return fullscreen;
	}

	public boolean isLiveTickerRunning() {
		return liveTickerRunning;
	}
	
}
