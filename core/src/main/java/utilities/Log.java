package utilities;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class Log {
	private static final Object lock = new Object();
	private static Log instance;
	private final Logger logger;

	private Log() {
		logger = LogManager.getLogger(Log.class.getName());
	}

	public static Log getInstance() {
		if (instance == null) {
			synchronized (lock) {
				instance = new Log();
			}
		}
		return instance;
	}

	public Log setLevel(Level level) {
		Configurator.setLevel(logger, level);
		return this;
	}

	public void info(String message) {
		if (message != null) {
			logger.info(message);
		}
	}

	public void debug(String message) {
		if (message != null) {
			logger.debug(message);
		}
	}

	public void error(String message) {
		if (message != null) {
			logger.error(message);
		}
	}

	public void error(String message, Object o) {
		logger.error(message, o);
	}

	public void trace(String message) {
		if (message != null) {
			logger.trace(message);
		}
	}

	public void warn(String message) {
		if (message != null) {
			logger.warn(message);
		}
	}

	public void startTest(String name) {
		logger.info("\n\nStarting test: " + name);
	}

	public void endTest(String name) {
		logger.info("Finished test: " + name);
	}

	public void logAssert(boolean assertion, String message) {
		int callingClassIndex = 2;
		if (!assertion) {
			StackTraceElement[] elements = Thread.currentThread().getStackTrace();
			if (elements.length > callingClassIndex) {
				message = String.format("----------\n%s\n%s.%s\n%s\n----------",
						"Framework assertion failure:",
						elements[callingClassIndex].getClassName(),
						elements[callingClassIndex].getMethodName(), message);
			}
			error(message);
			assert false;
		}
	}

}
