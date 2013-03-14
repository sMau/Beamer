package de.netprojectev.old;

import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.misc.LiveTicker;
import de.netprojectev.misc.TickerTextElement;
import de.netprojectev.server.datastructures.media.Theme;

public class MiscOld {
	/**
	 * 
	 * @param selectedIndices index array identifiying files in the {@link MediaHandlerOld}
	 * @return array of specified media files
	 */
	public static ServerMediaFile[] indexListToMediaFiles(int[] selectedIndices) {
		
		ServerMediaFile[] mediaFiles = new ServerMediaFile[selectedIndices.length];
		
		for(int i = 0; i < selectedIndices.length; i++) {
			
			mediaFiles[i] = MediaHandlerOld.getInstance().getMediaFiles().get(selectedIndices[i]); 
		}
		
		return mediaFiles;
	}
	
	/**
	 * 
	 * @param selectedIndices index array identifiying priorities in the preferences handler
	 * @return array of specified priorities
	 */
	public static Priority[] indexListToPriorities(int[] selectedIndices) {
		
		Priority[] priorities = new Priority[selectedIndices.length];
		
		for(int i = 0; i < selectedIndices.length; i++) {
			
			priorities[i] = PreferencesModelOld.getInstance().getListOfPriorities().get(selectedIndices[i]);
			
		}
		
		return priorities;
	}
	
	/**
	 * 
	 * @param selectedIndices index array identifiying themes in the preferences handler
	 * @return array of specified themes
	 */
	public static Theme[] indexListToThemes(int[] selectedIndices) {
		
		Theme[] themes = new Theme[selectedIndices.length];
		
		for(int i = 0; i < selectedIndices.length; i++) {
			
			themes[i] = PreferencesModelOld.getInstance().getListOfThemes().get(selectedIndices[i]);
			
		}
		
		return themes;
	}
	
	/**
	 * 
	 * @param selectedIndices index array identifiying ticker text elements in the Liveticker 
	 * @return array of specified ticker text elements
	 */
	public static TickerTextElement[] indexListToTickerElts(LiveTicker liveTicker, int[] selectedIndices) {
		
		TickerTextElement[] elements = new TickerTextElement[selectedIndices.length];
		
		for(int i = 0; i < selectedIndices.length; i++) {
			
			elements[i] = liveTicker.getTextElements().get(selectedIndices[i]);
			
		}
		
		return elements;
	}
}
