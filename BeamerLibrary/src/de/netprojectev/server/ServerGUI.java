package de.netprojectev.server;

import java.io.IOException;

import de.netprojectev.server.datastructures.ServerMediaFile;
import de.netprojectev.server.networking.MessageProxyServer.VideoFinishListener;

public interface ServerGUI {

	public void enterFullscreen(int screenNumber);

	public void exitFullscreen();

	public void setVideoFinishedListener(VideoFinishListener videoFinishListener);

	public void setVisible(boolean isVisible);

	public void showMediaFileInMainComponent(ServerMediaFile toShow) throws IOException;

	public void startLiveTicker();

	public void stopLiveTicker();

	public void updateLiveTickerString();

}
