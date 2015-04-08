package de.netprojectev.client;

public interface GUIClient {

	public void errorDuringLogin(String msg);

	public void errorRequestingFullsyncDialog();

	public void loginSuccess();
}
