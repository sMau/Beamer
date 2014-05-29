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

	public String getAlias() {
		return this.alias;
	}

	public Channel getChannel() {
		return this.channel;
	}

	public boolean isAlive() {
		return this.alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

}
