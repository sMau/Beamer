package de.netprojectev.networking.server;

public abstract class Command implements Runnable {
	private int priority = 0;

	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
}
