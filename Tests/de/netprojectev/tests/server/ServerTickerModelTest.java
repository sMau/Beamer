package de.netprojectev.tests.server;

import org.jboss.netty.logging.Log4JLoggerFactory;
import org.junit.Before;
import org.junit.Test;

import de.netprojectev.server.datastructures.liveticker.TickerElement;
import de.netprojectev.server.model.TickerModelServer;
import de.netprojectev.server.networking.MessageProxyServer;

public class ServerTickerModelTest {
	
	private MessageProxyServer proxy;
	private TickerModelServer tickerModel;
	
	@Before
	public void setUp() {
		proxy = new MessageProxyServer();
		tickerModel = proxy.getTickerModel();
	}
	
	@Test
	public void testAddTickerElement() {
		
		tickerModel.addTickerElement(new TickerElement("Test123"));
		
		
		
	}
	
	
	
	

}
