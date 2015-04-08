package de.netprojectev.client;

public interface GUIClient {

	void errorDuringLogin(String msg);

	void errorRequestingFullsyncDialog();

	void loginSuccess();
}
