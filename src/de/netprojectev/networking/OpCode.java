package de.netprojectev.networking;

public enum OpCode {

	/*
	 * Client to Server
	 */
	CTS_ADD_MEDIA_FILE, 
	CTS_REMOVE_MEDIA_FILE, 
	CTS_EDIT_MEDIA_FILE, 
	CTS_QUEUE_MEDIA_FILE, 
	CTS_SHOW_MEDIA_FILE, 
	CTS_SHOW_PREVIOUS_MEDIA_FILE, 
	CTS_SHOW_NEXT_MEDIA_FILE,
	CTS_DEQUEUE_MEDIAFILE,

	CTS_ADD_LIVE_TICKER_ELEMENT, 
	CTS_REMOVE_LIVE_TICKER_ELEMENT,
	CTS_EDIT_LIVE_TICKER_ELEMENT,
	CTS_ENABLE_LIVE_TICKER,
	CTS_DISABLE_LIVE_TICKER,
	
	CTS_ADD_PRIORITY, 
	CTS_ADD_THEME,
	CTS_REMOVE_PRIORITY,
	CTS_REMOVE_THEME,
	
	CTS_DISABLE_AUTO_MODE, 
	CTS_ENABLE_AUTO_MODE, 
	CTS_ENABLE_FULLSCREEN,
	CTS_DISABLE_FULLSCREEN,
	
	CTS_LOGIN_REQUEST,
	CTS_DISCONNECT,
	CTS_REQUEST_FULL_SYNC,
	
	
	
	
	/*
	 *  Server to Client TODO not used yet (implement the message handlers client sided)
	 */
	STC_SERVER_SHUTDOWN,
	STC_LOGIN_DENIED,
	STC_CONNECTION_ACK,
	
	STC_ADD_MEDIA_FILE_ACK, 
	STC_REMOVE_MEDIA_FILE_ACK, 
	STC_EDIT_MEDIA_FILE_ACK, 
	STC_QUEUE_MEDIA_FILE_ACK, 
	STC_DEQUEUE_MEDIAFILE_ACK,
	STC_SHOW_MEDIA_FILE_ACK, 

	STC_ADD_LIVE_TICKER_ELEMENT_ACK, 
	STC_REMOVE_LIVE_TICKER_ELEMENT_ACK,
	STC_EDIT_LIVE_TICKER_ELEMENT_ACK,
	STC_ENABLE_LIVE_TICKER_ACK,
	STC_DISABLE_LIVE_TICKER_ACK,
	
	STC_ADD_PRIORITY_ACK, 
	STC_ADD_THEME_ACK,
	STC_REMOVE_PRIORITY_ACK,
	STC_REMOVE_THEME_ACK,
	
	STC_ENABLE_AUTO_MODE_ACK, 
	STC_DISABLE_AUTO_MODE_ACK,
	STC_ENABLE_FULLSCREEN_ACK,
	STC_DISABLE_FULLSCREEN_ACK,
	STC_FULL_SYNC_START,
	STC_FULL_SYNC_STOP;
	
}
