package de.netprojectev.tests.server;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;

import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.exceptions.PriorityDoesNotExistException;
import de.netprojectev.exceptions.ThemeDoesNotExistException;
import de.netprojectev.server.datastructures.Theme;
import de.netprojectev.server.model.PreferencesModelServer;
import de.netprojectev.server.networking.MessageProxyServer;

public class PreferencesModelServerTest {
	
	private PreferencesModelServer prefs;
	private Theme t1 = new Theme("t1", new ImageIcon());
	private Theme t2 = new Theme("t2", new ImageIcon());
	private Priority p1 = new Priority("p1", 5);
	private Priority p2 = new Priority("p2", 10);
	
	private String k1 = "k1";
	private String k2 = "k2";
	private String v1 = "v1";
	private String v2 = "v2";
	
	@Before
	public void setUp() {
		prefs = new MessageProxyServer().getPrefsModel();
	}
	
	@Test
	public void testAddGetTheme1() throws ThemeDoesNotExistException {
		prefs.addTheme(t1);
		assertEquals(t1, prefs.getThemeById(t1.getId()));
		prefs.addTheme(t2);
		assertEquals(t2, prefs.getThemeById(t2.getId()));
		prefs.addTheme(t1);
		prefs.addTheme(t2);
		prefs.addTheme(t2);
		assertEquals(t1, prefs.getThemeById(t1.getId()));
		assertEquals(t2, prefs.getThemeById(t2.getId()));
	}
	
	@Test(expected=ThemeDoesNotExistException.class)
	public void testGetByIdTheme1() throws ThemeDoesNotExistException {
		prefs.getThemeById(UUID.randomUUID());
	}
	
	@Test
	public void testAddGetPriority1() throws PriorityDoesNotExistException {
		prefs.addPriority(p1);
		assertEquals(p1, prefs.getPriorityById(p1.getId()));
		prefs.addPriority(p2);
		assertEquals(p2, prefs.getPriorityById(p2.getId()));
		prefs.addPriority(p1);
		prefs.addPriority(p2);
		prefs.addPriority(p2);
		assertEquals(p1, prefs.getPriorityById(p1.getId()));
		assertEquals(p2, prefs.getPriorityById(p2.getId()));
	}
	
	@Test(expected=PriorityDoesNotExistException.class)
	public void testGetPriority1() throws PriorityDoesNotExistException {
		prefs.getPriorityById(UUID.randomUUID());
	}
	
	
	@Test(expected=ThemeDoesNotExistException.class)
	public void testRemoveTheme1() throws ThemeDoesNotExistException {
		prefs.addTheme(t1);
		assertEquals(t1, prefs.getThemeById(t1.getId()));
		prefs.addTheme(t2);
		assertEquals(t2, prefs.getThemeById(t2.getId()));
		prefs.removeTheme(t1.getId());
		prefs.removeTheme(t2.getId());
		prefs.getThemeById(t2.getId());
	}
	
	@Test(expected=PriorityDoesNotExistException.class)
	public void testRemovePriority1() throws PriorityDoesNotExistException {
		prefs.addPriority(p1);
		assertEquals(p1, prefs.getPriorityById(p1.getId()));
		prefs.addPriority(p2);
		assertEquals(p2, prefs.getPriorityById(p2.getId()));
		prefs.removePriority(p2.getId());
		prefs.removePriority(p1.getId());
		prefs.getPriorityById(p1.getId());
	}
	
	@Test
	public void testSetGetProperty1() {
		PreferencesModelServer.setProperty(k1, v1);
		PreferencesModelServer.setProperty(k2, v2);
		assertEquals(v1, PreferencesModelServer.getPropertyByKey(k1));
		assertEquals(v2, PreferencesModelServer.getPropertyByKey(k2));
		PreferencesModelServer.setProperty(k1, v2);
		assertEquals(v2, PreferencesModelServer.getPropertyByKey(k1));
	}

}
