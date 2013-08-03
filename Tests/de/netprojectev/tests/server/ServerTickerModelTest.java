package de.netprojectev.tests.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.server.ConstantsServer;
import de.netprojectev.server.datastructures.ServerTickerElement;
import de.netprojectev.server.model.PreferencesModelServer;
import de.netprojectev.server.model.TickerModelServer;
import de.netprojectev.server.networking.MessageProxyServer;

public class ServerTickerModelTest {

	private MessageProxyServer proxy;
	private TickerModelServer tickerModel;
	
	private ServerTickerElement elt123;
	private ServerTickerElement elt456;
	private ServerTickerElement elt789;

	@Before
	public void setUp() {
		proxy = new MessageProxyServer();
		tickerModel = proxy.getTickerModel();
		
		elt123 = new ServerTickerElement("Test123");
		elt456 = new ServerTickerElement("Test456");
		elt789 = new ServerTickerElement("Test789");
	}

	@Test
	public void testAddTickerElement() throws MediaDoesNotExsistException {
		
		UUID test123 = tickerModel.addTickerElement(elt123);
		UUID test456 = tickerModel.addTickerElement(elt456);
		UUID test789 = tickerModel.addTickerElement(elt789);
		
		assertEquals(elt123, tickerModel.getElementByID(test123));
		assertEquals(elt456, tickerModel.getElementByID(test456));
		assertEquals(elt789, tickerModel.getElementByID(test789));
				
		assertEquals("Test123", tickerModel.getElementByID(test123).getText());
		assertEquals("Test456", tickerModel.getElementByID(test456).getText());
		assertEquals("Test789", tickerModel.getElementByID(test789).getText());
		
		assertEquals(elt456, tickerModel.getElementByID(test456));
		assertEquals("Test789", tickerModel.getElementByID(test789).getText());
	}
	
	@Test(expected=MediaDoesNotExsistException.class)
	public void testAddTickerElementException() throws MediaDoesNotExsistException {
		assertEquals(elt123, tickerModel.getElementByID(UUID.randomUUID()));
	}
	
	
	@Test
	public void testAddTickerElementCollision() throws MediaDoesNotExsistException {

		UUID test123 = tickerModel.addTickerElement(elt123);
		
		tickerModel.addTickerElement(elt123);
		
		assertEquals(elt123, tickerModel.getElementByID(test123));
		assertEquals("Test123", tickerModel.getElementByID(test123).getText());
		
		tickerModel.addTickerElement(elt123);
		assertEquals(elt123, tickerModel.getElementByID(test123));
		assertEquals("Test123", tickerModel.getElementByID(test123).getText());
		
		tickerModel.addTickerElement(elt123);
		assertEquals(elt123, tickerModel.getElementByID(test123));
		assertEquals("Test123", tickerModel.getElementByID(test123).getText());
		
		tickerModel.addTickerElement(elt123);
		assertEquals(elt123, tickerModel.getElementByID(test123));
		assertEquals("Test123", tickerModel.getElementByID(test123).getText());
		
	}
	
	@Test
	public void testRemoveTickerElement() throws MediaDoesNotExsistException {
		UUID test123 = tickerModel.addTickerElement(elt123);
		UUID test456 = tickerModel.addTickerElement(elt456);
		UUID test789 = tickerModel.addTickerElement(elt789);
		
		assertEquals(elt123, tickerModel.getElementByID(test123));
		assertEquals(elt456, tickerModel.getElementByID(test456));
		assertEquals(elt789, tickerModel.getElementByID(test789));
		
		tickerModel.removeTickerElement(test123);
		
		assertEquals(elt456, tickerModel.getElementByID(test456));
		assertEquals(elt789, tickerModel.getElementByID(test789));
		
		tickerModel.addTickerElement(elt123);
		assertEquals(elt123, tickerModel.getElementByID(test123));
		
		tickerModel.removeTickerElement(test123);
		tickerModel.removeTickerElement(test789);
		
		assertEquals(elt456, tickerModel.getElementByID(test456));
		
	}
	
	@Test
	public void testGenerateCompleteTickerString() {
		tickerModel.addTickerElement(elt123);
		tickerModel.addTickerElement(elt456);
		tickerModel.addTickerElement(elt789);
		
		String actual = tickerModel.generateCompleteTickerText();

		assertTrue(actual.contains(elt123.getText() + PreferencesModelServer.getPropertyByKey(ConstantsServer.PROP_SERVER_TICKER_SEPERATOR)));
		assertTrue(actual.contains(elt456.getText() + PreferencesModelServer.getPropertyByKey(ConstantsServer.PROP_SERVER_TICKER_SEPERATOR)));
		assertTrue(actual.contains(elt789.getText() + PreferencesModelServer.getPropertyByKey(ConstantsServer.PROP_SERVER_TICKER_SEPERATOR)));

	}
}
