package de.netprojectev.client.networking;

import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.server.datastructures.media.ServerMediaFile;

public class ClientMessageWrapper {

	private final Client client;

	public ClientMessageWrapper(Client client) {
		this.client = client;
	}
	
	private void sendCommandMessage(Message msg) {
		client.sendMessageToServer(msg);
	}

	public void addMediaFile(ServerMediaFile file) {
		sendCommandMessage(new Message(OpCode.ADD_MEDIA_FILE, file));
	}

	public void removeMediaFile(ServerMediaFile file) {
		sendCommandMessage(new Message(OpCode.REMOVE_MEDIA_FILE, file));
	}

	public void showMediaFile(ServerMediaFile file) {
		sendCommandMessage(new Message(OpCode.SHOW_MEDIA_FILE, file));
	}

	public void showPreviousMediaFile(ServerMediaFile file) {
		sendCommandMessage(new Message(OpCode.SHOW_PREVIOUS_MEDIA_FILE, file));
	}

	public void showNextMediaFile(ServerMediaFile file) {
		sendCommandMessage(new Message(OpCode.SHOW_NEXT_MEDIA_FILE, file));
	}
	
	
}
