package de.netprojectev.LiveTicker;

import java.util.LinkedList;

import de.netprojectev.GUI.Display.DisplayMainFrame;
import de.netprojectev.GUI.Manager.ManagerFrame;
import de.netprojectev.GUI.Manager.TickerManagerTableModel;
import de.netprojectev.MediaHandler.DisplayDispatcher;
import de.netprojectev.Misc.Constants;

/**
 * 
 * This class manages the elements of the live ticker.
 * Consisting of a list of LiveTickerElements which contains the text.
 * @author samu
 */
public class LiveTicker {

	private static LiveTicker instance = new LiveTicker();
	
	private ManagerFrame managerFrame;
	
	private DisplayMainFrame display;

	private LinkedList<TickerTextElement> textElements;
	private int speed;
	private String completeTickerText;
	
	
	private LiveTicker() {
		
		this.textElements = new LinkedList<TickerTextElement>();
		this.speed = Constants.DEFAULT_TICKER_SPEED;
		this.completeTickerText = "";
		this.display = DisplayDispatcher.getInstance().getDisplayFrame();
			
	}
	
	public static LiveTicker getInstance() {
		
		if(instance == null) {
			instance = new LiveTicker();
		}
		return instance;
	}
	
	public static void reset() {
		instance = new LiveTicker();
	}
	
	/**
	 * Adds a new TickerTextElement to the list.
	 * @param element the element to add.
	 */
	public void add(TickerTextElement element) {
		if(!textElements.contains(element)) {
			textElements.add(element);
			if(textElements.size() == 1) {
				display.startLiveTicker(Constants.DEFAULT_TICKER_SPEED);
			}
		}
		refreshDataModel();
	
	}

	/**
	 * invokes a update on the data model to refresh the view
	 * furthermore it invokes the genereteCompleteTickerString method
	 */
	private void refreshDataModel() {
		if(managerFrame != null) {
			((TickerManagerTableModel) managerFrame.getjTableLiveticker().getModel()).updateModel();
		}
		generateCompleteTickerString();
		
	}
	
	/**
	 * Remove a set of elements from list.
	 * @param elements the element to remove
	 */
	public void remove(TickerTextElement[] elements) {
		
		for(int i = 0; i < elements.length; i++) {	
			textElements.remove(elements[i]);	
		}
		if(textElements.isEmpty()) {
			display.stopLiveTicker();
		}
		refreshDataModel();
		
	}
	
	/**
	 * Editing a existing ticker element.
	 * @param element the element to edit
	 * @param newText new text of the element
	 */
	public void editElement(TickerTextElement element, String newText) {
		element.setText(newText);
		refreshDataModel();
	}
	
	/**
	 * 
	 * Generates the complete ticker string connecting all elements to be viewed on display unit.
	 * It takes care of the seperators and also takes the Boolean toShow into account.
	 */
	public void generateCompleteTickerString() {
		
		completeTickerText = "";
		
		for(int i = 0; i < textElements.size(); i++) {
			
			if(textElements.get(i).getToShow()) {
				
				completeTickerText += textElements.get(i).getText() + Constants.SEPERATOR;
			}
			
		}
		display.getTickerComponent().setTickerString(completeTickerText);

	}

	
	public LinkedList<TickerTextElement> getTextElements() {
		return textElements;
	}
	public void setTextElements(LinkedList<TickerTextElement> textElements) {
		this.textElements = textElements;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
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
