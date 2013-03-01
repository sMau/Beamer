package de.netprojectev.server.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

import de.netprojectev.old.server.datastructures.media.ServerMediaFile;
import de.netprojectev.server.networking.MessageProxyServer;

public class MediaModelServer {
	
	private final MessageProxyServer proxy;
	private HashMap<UUID, ServerMediaFile> allMediaFiles;
	private LinkedList<ServerMediaFile> mediaQueue;
	private ServerMediaFile currentFile;
	
	public MediaModelServer(MessageProxyServer proxy) {
		this.proxy = proxy;
	}
	
	public void addMediaFile(ServerMediaFile file) {
		//TODO
	}
	public void removeMediaFile(UUID id) {
		//TODO
	}
	public void mediaFileChanged(UUID id, ServerMediaFile updatedFile) {
		//TODO
	}
	
	public void queue(UUID id) {
		//TODO
	}	
	
}
