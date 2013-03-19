package de.netprojectev.client.datastructures;

import java.util.UUID;

import de.netprojectev.server.datastructures.ServerTickerElement;

public class ClientTickerElement {

	private final UUID id;
	private String text;
	private boolean show;
	
	public ClientTickerElement(ServerTickerElement serverElt) {
		this.id = serverElt.getId();
		this.text = serverElt.getText();
		this.show = true;
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
