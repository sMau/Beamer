package de.netprojectev.server;

import java.io.IOException;

import de.netprojectev.server.datastructures.MediaFileServer;
import de.netprojectev.server.networking.MessageProxyServer.VideoFinishListener;

public interface GUIServer {

	public void enterFullscreen(int screenNumber);

	public void exitFullscreen();

	public void setVideoFinishedListener(VideoFinishListener videoFinishListener);

	public void setVisible(boolean isVisible);

	public void showMediaFileInMainComponent(MediaFileServer toShow) throws IOException;

	public void startLiveTicker();

	public void stopLiveTicker();

	public void updateLiveTickerString();

}
