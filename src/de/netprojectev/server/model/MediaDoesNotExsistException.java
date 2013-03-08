package de.netprojectev.server.model;

public class MediaDoesNotExsistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3043533917436076512L;

	public MediaDoesNotExsistException(String msg) {
		super(msg);	
	}	
}
