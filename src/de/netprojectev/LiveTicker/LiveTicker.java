package de.netprojectev.LiveTicker;

import java.io.Serializable;
import java.util.LinkedList;

import de.netprojectev.Misc.Constants;

/**
 * 
 * Die Klasse LiveTicker dient zur Verwaltung der einzelnen Elemente des LiveTickers.
 * Sie besteht aus beliebig vielen TickerTextElements, deren Text den LiveTicker Text ergeben.
 *
 */
public class LiveTicker implements Serializable {

	private static final long serialVersionUID = 6806990368618523192L;

	private static LiveTicker instance;
	
	private LinkedList<TickerTextElement> textElements;
	private int speed;
	private String completeTickerText;
	
	
	private LiveTicker() {
		
		this.textElements = new LinkedList<TickerTextElement>();
		this.speed = Constants.DEFAULT_TICKER_SPEED;
		this.completeTickerText = "";
			
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
	 * Fügt ein neues TickerElement der Liste hinzu
	 * @param element
	 */
	public void add(TickerTextElement element) {
		if(!textElements.contains(element)) {
			textElements.add(element);
		}
	
	}
	
	/**
	 * Entfernt die übergebenen Ticker Elemente aus der Liste
	 * @param elements
	 */
	public void remove(TickerTextElement[] elements) {
		
		for(int i = 0; i < elements.length; i++) {	
			textElements.remove(elements[i]);	
		}
		
	}
	
	/**
	 * Das übergebene Element wird editiert.
	 * Der Text wird verändert.
	 * @param element
	 * @param newText
	 */
	public void editElement(TickerTextElement element, String newText) {
		element.setText(newText);
	}
	
	/**
	 * Generiert den vollständigen Ticker String welcher nachher als Basis für die Anzeige dient.
	 * Berücksichtigt alle Trennzeichen (+++) sowie ob toShow gesetzt ist oder nicht.
	 */
	public void generateCompleteTickerString() {
		
		completeTickerText = "";
		
		for(int i = 0; i < textElements.size(); i++) {
			
			if(textElements.get(i).getToShow()) {
				
				completeTickerText += textElements.get(i).getText() + Constants.SEPERATOR;
			}
			
		}

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
	
}
