package de.netprojectev.unittests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.netprojectev.liveticker.LiveTicker;
import de.netprojectev.liveticker.TickerTextElement;

public class LiveTickerTest {

	LiveTicker ticker;
	TickerTextElement[] elements1;
	TickerTextElement elt1;
	TickerTextElement elt2;
	TickerTextElement elt3;
	TickerTextElement elt4;
	TickerTextElement elt5;
	
	
	@Before
	public void setUp() throws Exception {
		
		ticker = LiveTicker.getInstance();
		
		elements1 = new TickerTextElement[2];
				
		elt1 = new TickerTextElement("1");
		elt2 = new TickerTextElement("2");
		elt3 = new TickerTextElement("3");
		elt4 = new TickerTextElement("4");
		elt5 = new TickerTextElement("5");

		elements1[0] = elt1;
		elements1[1] = elt3;
		
		elt2.setToShow(false);
		elt4.setToShow(false);

	}

	@Test
	public void testAdd() {
		
		ticker.add(elt1);
		ticker.add(elt2);
		ticker.add(elt3);
		
		assertEquals(3, ticker.getTextElements().size());
		assertEquals(elt1, ticker.getTextElements().get(0));
		
	}
	
	@Test
	public void testRemove() {
		
		ticker.remove(elements1);	
		assertEquals(1, ticker.getTextElements().size());
		assertEquals(elt2, ticker.getTextElements().get(0));
		
	}

}
