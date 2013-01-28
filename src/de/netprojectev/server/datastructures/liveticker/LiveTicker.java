package de.netprojectev.server.datastructures.liveticker;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.netprojectev.client.gui.manager.ManagerFrame;
import de.netprojectev.client.gui.manager.TickerManagerTableModel;
import de.netprojectev.misc.Constants;
import de.netprojectev.misc.Misc;
import de.netprojectev.server.model.PreferencesModel;

/**
 * 
 * This class manages the elements of the live ticker.
 * Consisting of a list of LiveTickerElements which contains the text.
 * @author samu
 */
public class LiveTicker {
	
	private static final Logger log = Misc.getLoggerAll(LiveTicker.class.getName());
		
	private ManagerFrame managerFrame;
	private LinkedList<TickerTextElement> textElements;
	private String completeTickerText;
	
	public LiveTicker() {
		
		this.textElements = new LinkedList<TickerTextElement>();
		this.completeTickerText = "";			
	}

	/**
	 * invokes a update on the data model to refresh the view
	 * furthermore it invokes the genereteCompleteTickerString method
	 */
	private void refreshDataModel() {
		if(managerFrame != null) {
			((TickerManagerTableModel) managerFrame.getjTableLiveticker().getModel()).updateModel();
		}
		
		/*
		 * 
		 * TODO graphical view update corrupted after refactoring
		 * 
		 * 
		 */
		//generateCompleteTickerString();
		
	}
	
	public LinkedList<TickerTextElement> getTextElements() {
		return textElements;
	}
	public void setTextElements(LinkedList<TickerTextElement> textElements) {
		this.textElements = textElements;
	}

	public String getCompleteTickerText() {
		return completeTickerText;
	}
	public void setCompleteTickerText(String completeTickerText) {
		this.completeTickerText = completeTickerText;
	}

	public ManagerFrame getManagerFrame() {
		return managerFrame;
	}

	public void setManagerFrame(ManagerFrame managerFrame) {
		this.managerFrame = managerFrame;
	}
	
}
