package de.netprojectev.server;

import java.io.IOException;

import de.netprojectev.server.datastructures.ServerMediaFile;
import de.netprojectev.server.networking.MessageProxyServer;
import de.netprojectev.server.networking.MessageProxyServer.VideoFinishListener;

public interface ServerGUI {
	
	public void setVisible(boolean isVisible);
	public void updateLiveTickerString();
	public void setVideoFinishedListener(VideoFinishListener videoFinishListener);
	public void showMediaFileInMainComponent(ServerMediaFile toShow) throws IOException;
	public void enterFullscreen(int screenNumber);
	public void exitFullscreen();
	public void startLiveTicker();
	public void stopLiveTicker();
	
}
