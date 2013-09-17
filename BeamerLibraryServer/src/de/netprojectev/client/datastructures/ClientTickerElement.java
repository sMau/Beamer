package de.netprojectev.client.datastructures;

import java.io.Serializable;
import java.util.UUID;

import de.netprojectev.server.datastructures.ServerTickerElement;

public class ClientTickerElement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7318943668603033104L;
	private final UUID id;
	private String text;
	private boolean show;
	
	private ClientTickerElement(ClientTickerElement elt) {
		this.id = elt.id;
		this.text = elt.text;
		this.show = elt.show;
	}
	
	public ClientTickerElement(ServerTickerElement serverElt) {
		this.id = serverElt.getId();
		this.text = serverElt.getText();
		this.show = serverElt.isShow();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public UUID getId() {
		return id;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}
	
	public ClientTickerElement copy() {
		return new ClientTickerElement(this);
	}
	
	@Override
	public boolean equals(Object other){
	    if (other == null || this == null) {
	    	return false;
	    } else if(other == this){
	    	return true;
	    } else if(other instanceof ClientTickerElement) {
	    	return ((ClientTickerElement) other).getId().equals(this.getId());
	    } else {
	    	return false;
	    }
	}
	
}