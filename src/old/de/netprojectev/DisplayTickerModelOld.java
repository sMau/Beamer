package old.de.netprojectev;

import java.util.logging.Level;
import java.util.logging.Logger;

import old.de.netprojectev.server.datastructures.liveticker.LiveTicker;
import old.de.netprojectev.server.datastructures.liveticker.TickerTextElement;

import de.netprojectev.misc.Constants;
import de.netprojectev.misc.Misc;

public class DisplayTickerModelOld {
	
	private static final Logger LOG = Misc.getLoggerAll(DisplayTickerModelOld.class.getName());
	
	private final LiveTicker liveTicker;
	private final DisplayController controller;
	
	public DisplayTickerModelOld(DisplayController displayController) {
		this.liveTicker = new LiveTicker();
		this.controller = displayController;
	}
	

	/**
	 * 
	 * Generates the complete ticker string connecting all elements to be viewed on display unit.
	 * It takes care of the seperators and also takes the Boolean toShow into account.
	 */
	public void generateCompleteTickerString() {
		
		liveTicker.setCompleteTickerText("");
		
		for(int i = 0; i < liveTicker.getTextElements().size(); i++) {
			
			if(liveTicker.getTextElements().get(i).getToShow()) {
				
				liveTicker.setCompleteTickerText(liveTicker.getCompleteTickerText() + liveTicker.getTextElements().get(i).getText() + PreferencesModelOld.getInstance().getProperties().getProperty(Constants.PROP_TICKER_SEPERATOR));
			}
			
		}
		LOG.log(Level.INFO, "new complete ticker string generated");
		
	}
	
	/**
	 * Adds a new TickerTextElement to the list.
	 * @param element the element to add.
	 */
	public void add(TickerTextElement element) {
		if(!liveTicker.getTextElements().contains(element)) {
			LOG.log(Level.INFO, "adding element to live ticker " + element.getText());
			liveTicker.getTextElements().add(element);
			if(liveTicker.getTextElements().size() == 1) {
				controller.startLiveTicker();
			}
		}
		// TODO refreshDataModel();
	
	}
	
	/**
	 * Remove a set of elements from list.
	 * @param elements the element to remove
	 */
	public void remove(TickerTextElement[] elements) {
		
		for(int i = 0; i < elements.length; i++) {	
			LOG.log(Level.INFO, "removing element from live ticker " + elements[i].getText());
			liveTicker.getTextElements().remove(elements[i]);	
		}

		// TODO refreshDataModel();
		
	}
	
	/**
	 * Editing a existing ticker element.
	 * @param element the element to edit
	 * @param newText new text of the element
	 */
	public void editElement(TickerTextElement element, String newText) {
		element.setText(newText);
		// TODO refreshDataModel();
	}
	
}
