package de.netprojectev.common.utils;

import java.util.logging.*;

public class LoggerBuilder {

	public static Logger createLogger(Class<?> clazz) {

		Logger logger = Logger.getLogger(clazz.getName());
        logger.setLevel(Level.ALL);

        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.FINEST);
        for(Handler h : logger.getHandlers()) {
            logger.removeHandler(h);
        }
        logger.addHandler(consoleHandler);
		return logger;
	}

	private LoggerBuilder() {
        Logger.getLogger("").setLevel(Level.ALL);
        LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME).setLevel(Level.ALL);
    }

}
