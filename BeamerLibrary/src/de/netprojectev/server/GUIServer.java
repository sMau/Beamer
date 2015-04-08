package de.netprojectev.server;

import java.io.IOException;

import de.netprojectev.server.datastructures.MediaFileServer;
import de.netprojectev.server.networking.MessageProxyServer.VideoFinishListener;

public interface GUIServer {

	void enterFullscreen(int screenNumber);

	void exitFullscreen();

	void setVideoFinishedListener(VideoFinishListener videoFinishListener);

	void setVisible(boolean isVisible);

	void showMediaFileInMainComponent(MediaFileServer toShow) throws IOException;

	void startLiveTicker();

	void stopLiveTicker();

	void updateLiveTickerString();

}
