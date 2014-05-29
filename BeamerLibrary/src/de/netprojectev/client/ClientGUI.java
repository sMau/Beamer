package de.netprojectev.client;

public interface ClientGUI {

	public void errorDuringLogin(String msg);

	public void errorRequestingFullsyncDialog();

	public void loginSuccess();
}
