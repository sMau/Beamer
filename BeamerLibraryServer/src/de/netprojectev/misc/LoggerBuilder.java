package de.netprojectev.misc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class LoggerBuilder {
	
	Logger log = createLogger(this.getClass());
	
	private LoggerBuilder() {
		
	}
	
	public static Logger createLogger(Class<?> clazz) {
		Logger logger = LogManager.getLogger(clazz);
		
		return logger;
	}

}
