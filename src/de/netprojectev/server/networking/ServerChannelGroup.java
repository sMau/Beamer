package de.netprojectev.server.networking;

import java.util.concurrent.ConcurrentHashMap;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.group.DefaultChannelGroup;

public class ServerChannelGroup extends DefaultChannelGroup{

	private ConcurrentHashMap<String, Channel> aliasToChannelMapping;
	
	
	public ServerChannelGroup(String name) {
		super(name);
		aliasToChannelMapping = new ConcurrentHashMap<>();
	}
	
	public boolean addWithAlias(Channel channel) {
		boolean success = super.add(channel);
		
		if(success) {
			
		}
		
		return success;
	}
	
	public boolean removeWithAlias(Object o) {
		boolean success = super.remove(o);
		
		if(success) {
			
		}
		
		return success;
	}
}
