package de.netprojectev.client;

public interface ClientGUI {
	
	public void errorDuringLogin(String msg);
	
	public void loginSuccess();
	
	public void errorRequestingFullsyncDialog();
}
