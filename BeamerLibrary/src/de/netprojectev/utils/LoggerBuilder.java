package de.netprojectev.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerBuilder {

	public static Logger createLogger(Class<?> clazz) {
		Logger logger = LogManager.getLogger(clazz);

		return logger;
	}

	private LoggerBuilder() {

	}

}
