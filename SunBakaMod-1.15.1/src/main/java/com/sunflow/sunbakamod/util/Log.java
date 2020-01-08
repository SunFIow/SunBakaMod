package com.sunflow.sunbakamod.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sunflow.sunbakamod.SunBakaMod;

public class Log {
	private static final Logger LOGGER = LogManager.getLogger(SunBakaMod.NAME);

	public static void log(Level level, Object logMessage) {
		LOGGER.log(level, logMessage);
	}

	public static void log(Level level, String logMessage, Object... params) {
		LOGGER.log(level, logMessage, params);
	}

	public static void all(Object logMessage) {
		log(Level.ALL, logMessage);
	}

	public static void all(String logMessage, Object... params) {
		log(Level.ALL, logMessage, params);
	}

	public static void debug(Object logMessage) {
		log(Level.DEBUG, logMessage);
	}

	public static void debug(String logMessage, Object... params) {
		log(Level.DEBUG, logMessage, params);
	}

	public static void error(Object logMessage) {
		log(Level.ERROR, logMessage);
	}

	public static void error(String logMessage, Object... params) {
		log(Level.ERROR, logMessage, params);
	}

	public static void fatal(Object logMessage) {
		log(Level.FATAL, logMessage);
	}

	public static void fatal(String logMessage, Object... params) {
		log(Level.FATAL, logMessage, params);
	}

	public static void info(Object logMessage) {
		log(Level.INFO, logMessage);
	}

	public static void info(String logMessage, Object... params) {
		log(Level.INFO, logMessage, params);
	}

	public static void off(Object logMessage) {
		log(Level.OFF, logMessage);
	}

	public static void off(String logMessage, Object... params) {
		log(Level.OFF, logMessage, params);
	}

	public static void trace(Object logMessage) {
		log(Level.TRACE, logMessage);
	}

	public static void trace(String logMessage, Object... params) {
		log(Level.TRACE, logMessage, params);
	}

	public static void warn(Object logMessage) {
		log(Level.WARN, logMessage);
	}

	public static void warn(String logMessage, Object... params) {
		log(Level.WARN, logMessage, params);
	}
}
