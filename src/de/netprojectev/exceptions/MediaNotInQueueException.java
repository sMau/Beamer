package de.netprojectev.exceptions;

public class MediaNotInQueueException extends MediaDoesNotExsistException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2898212926362456573L;

	public MediaNotInQueueException(String msg) {
		super(msg);
		
	}

}
