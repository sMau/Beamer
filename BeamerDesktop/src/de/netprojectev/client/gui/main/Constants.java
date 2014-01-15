package de.netprojectev.client.gui.main;

import java.awt.GraphicsEnvironment;

import de.netprojectev.utils.HelperMethods;

public class Constants {

	/*
	 * MISC
	 */
	public static final String[] FONT_SIZES = HelperMethods.generateFontSizes();
	public static final String[] FONT_FAMILIES = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	public static final String[] FONT_SIZES_COUNTDOWN = HelperMethods.generateFontSizesCountdown();
	
}
