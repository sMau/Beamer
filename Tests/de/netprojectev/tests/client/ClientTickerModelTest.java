package de.netprojectev.tests.client;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import de.netprojectev.client.Client;
import de.netprojectev.client.datastructures.ClientTickerElement;
import de.netprojectev.client.model.TickerModelClient;
import de.netprojectev.client.networking.ClientMessageProxy;
import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.networking.LoginData;
import de.netprojectev.server.datastructures.ServerTickerElement;

public class ClientTickerModelTest {

	private TickerModelClient tickerModel;
	
	private ClientTickerElement elt1 = new ClientTickerElement(new ServerTickerElement("test1"));
	private ClientTickerElement elt2 = new ClientTickerElement(new ServerTickerElement("test2"));
	private ClientTickerElement elt3 = new ClientTickerElement(new ServerTickerElement("test3"));
	
	
	@Before
	public void setUp() {
		
		Client client = new Client("", 0, new LoginData("", ""));
		
		tickerModel = client.getProxy().getTickerModel();
		
	}
	
	@Test
	public void testAddTickerElement() throws MediaDoesNotExsistException {
		tickerModel.addTickerElement(elt1);
		assertEquals(elt1, tickerModel.getElementByID(elt1.getId()));
		tickerModel.addTickerElement(elt2);
		assertEquals(elt1, tickerModel.getElementByID(elt1.getId()));
		assertEquals(elt2, tickerModel.getElementByID(elt2.getId()));
		tickerModel.addTickerElement(elt3);
		assertEquals(elt1, tickerModel.getElementByID(elt1.getId()));
		assertEquals(elt2, tickerModel.getElementByID(elt2.getId()));
		assertEquals(elt3, tickerModel.getElementByID(elt3.getId()));
	}
	
	@Test(expected=MediaDoesNotExsistException.class)
	public void testGetEltByID() throws MediaDoesNotExsistException {
		tickerModel.getElementByID(UUID.randomUUID());
	}
	
	@Test(expected=MediaDoesNotExsistException.class)
	public void testRemoveTickerElement2() throws MediaDoesNotExsistException {
		tickerModel.removeTickerElement(UUID.randomUUID());
	}
	
	@Test
	public void testRemoveTickerElement() throws MediaDoesNotExsistException {
		tickerModel.addTickerElement(elt1);
		assertEquals(elt1, tickerModel.getElementByID(elt1.getId()));
		tickerModel.addTickerElement(elt2);
		assertEquals(elt1, tickerModel.getElementByID(elt1.getId()));
		assertEquals(elt2, tickerModel.getElementByID(elt2.getId()));
		tickerModel.addTickerElement(elt3);
		assertEquals(elt1, tickerModel.getElementByID(elt1.getId()));
		assertEquals(elt2, tickerModel.getElementByID(elt2.getId()));
		assertEquals(elt3, tickerModel.getElementByID(elt3.getId()));
		
		tickerModel.removeTickerElement(elt1.getId());
		assertEquals(elt2, tickerModel.getElementByID(elt2.getId()));
		assertEquals(elt3, tickerModel.getElementByID(elt3.getId()));
		tickerModel.removeTickerElement(elt2.getId());
		assertEquals(elt3, tickerModel.getElementByID(elt3.getId()));
		tickerModel.removeTickerElement(elt3.getId());
		
		tickerModel.addTickerElement(elt3);
		assertEquals(elt3, tickerModel.getElementByID(elt3.getId()));
	}
}
