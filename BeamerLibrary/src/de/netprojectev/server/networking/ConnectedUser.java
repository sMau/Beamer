package de.netprojectev.server.networking;

import io.netty.channel.Channel;


public class ConnectedUser {
	
	private final Channel channel;
	private final String alias;
	private boolean alive;
	
	public ConnectedUser(Channel channel, String alias) {
		this.channel = channel;
		this.alias = alias;
		this.alive = true;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public Channel getChannel() {
		return channel;
	}

	public String getAlias() {
		return alias;
	}
	

}
