package de.netprojectev.networking.server;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class CommandQueue {

	private PriorityBlockingQueue<Command> queue;
	

}

class CommandComparator implements Comparator<Command> {

	@Override
	public int compare(Command o1, Command o2) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}