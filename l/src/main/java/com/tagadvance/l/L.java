package com.tagadvance.l;

import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.helpers.CheckReturnValue;
import org.slf4j.spi.LoggingEventBuilder;

@SuppressWarnings("unused")
public final class L {

	/**
	 * An alias of {@link #logger()}.
	 */
	public static Logger log() {
		return logger();
	}

	public static Logger logger() {
		final var className = getClassName();

		return LoggerFactory.getLogger(className);
	}

	static String getClassName() {
		return getClassName(name -> !name.contains("$"));
	}

	public static Logger nestedLogger() {
		final var className = getNestClassName();

		return LoggerFactory.getLogger(className);
	}

	static String getNestClassName() {
		return getClassName(name -> true);
	}

	static String getClassName(final Predicate<String> predicate) {
		final var stackTrace = Thread.currentThread().getStackTrace();
		final var regex = "^(com\\.sun|java|javax|jdk|org\\.w3c\\.dom|org\\.xml\\.sax)\\.";
		final var pattern = Pattern.compile(regex);

		return Stream.of(stackTrace)
			.map(StackTraceElement::getClassName)
			.filter(name -> !pattern.matcher(name).find())
			.filter(name -> !name.equals(L.class.getName()))
			.filter(predicate)
			.findFirst()
			.orElseThrow();
	}

	private L() {
	}

	/**
	 * Alias of {@link Logger#isEnabledForLevel(Level)}.
	 */
	public static boolean isEnabledForLevel(Level level) {
		return logger().isEnabledForLevel(level);
	}

	/**
	 * Alias of {@link Logger#isTraceEnabled()}.
	 */
	public static boolean isTraceEnabled() {
		return logger().isTraceEnabled();
	}

	/**
	 * Alias of {@link Logger#trace(String)}.
	 */
	public static void trace(String msg) {
		logger().trace(msg);
	}

	/**
	 * Alias of {@link Logger#trace(String, Object)}.
	 */
	public static void trace(String format, Object arg) {
		logger().trace(format, arg);
	}

	/**
	 * Alias of {@link Logger#trace(String, Object, Object)}.
	 */
	public static void trace(String format, Object arg1, Object arg2) {
		logger().trace(format, arg1, arg2);
	}

	/**
	 * Alias of {@link Logger#trace(String, Object...)}.
	 */
	public static void trace(String format, Object... arguments) {
		logger().trace(format, arguments);
	}

	/**
	 * Alias of {@link Logger#trace(String, Throwable)}.
	 */
	public static void trace(String msg, Throwable t) {
		logger().trace(msg, t);
	}

	/**
	 * Alias of {@link Logger#isTraceEnabled(Marker)}.
	 */
	public static boolean isTraceEnabled(Marker marker) {
		return logger().isTraceEnabled(marker);
	}

	/**
	 * Alias of {@link Logger#trace(Marker, String)}.
	 */
	public static void trace(Marker marker, String msg) {
		logger().trace(marker, msg);
	}

	/**
	 * Alias of {@link Logger#trace(Marker, String, Object)}.
	 */
	public void trace(Marker marker, String format, Object arg) {
		logger().trace(marker, format, arg);
	}

	/**
	 * Alias of {@link Logger#trace(Marker, String, Object, Object)}.
	 */
	public static void trace(Marker marker, String format, Object arg1, Object arg2) {
		logger().trace(marker, format, arg1, arg2);
	}

	/**
	 * Alias of {@link Logger#trace(Marker, String, Object...)}.
	 */
	public static void trace(Marker marker, String format, Object... arguments) {
		logger().trace(marker, format, arguments);
	}

	/**
	 * Alias of {@link Logger#trace(Marker, String, Throwable)}.
	 */
	public static void trace(Marker marker, String msg, Throwable t) {
		logger().trace(marker, msg, t);
	}


	/**
	 * Alias of {@link Logger#atTrace()}.
	 */
	@CheckReturnValue
	public static LoggingEventBuilder atTrace() {
		return logger().atTrace();
	}

	/**
	 * Alias of {@link Logger#isDebugEnabled()}.
	 */
	public static boolean isDebugEnabled() {
		return logger().isDebugEnabled();
	}

	/**
	 * Alias of {@link Logger#debug(String)}.
	 */
	public static void debug(String msg) {
		logger().debug(msg);
	}

	/**
	 * Alias of {@link Logger#debug(String, Object)}.
	 */
	public static void debug(String format, Object arg) {
		logger().debug(format, arg);
	}

	/**
	 * Alias of {@link Logger#debug(String, Object, Object)}.
	 */
	public static void debug(String format, Object arg1, Object arg2) {
		logger().debug(format, arg1, arg2);
	}

	/**
	 * Alias of {@link Logger#debug(String, Object...)}.
	 */
	public static void debug(String format, Object... arguments) {
		logger().debug(format, arguments);
	}

	/**
	 * Alias of {@link Logger#debug(String, Throwable)}.
	 */
	public static void debug(String msg, Throwable t) {
		logger().debug(msg, t);
	}

	/**
	 * Alias of {@link Logger#isDebugEnabled(Marker)}.
	 */
	public static boolean isDebugEnabled(Marker marker) {
		return logger().isDebugEnabled(marker);
	}

	/**
	 * Alias of {@link Logger#debug(Marker, String)}.
	 */
	public static void debug(Marker marker, String msg) {
		logger().debug(marker, msg);
	}

	/**
	 * Alias of {@link Logger#debug(Marker, String, Object)}.
	 */
	public void debug(Marker marker, String format, Object arg) {
		logger().debug(marker, format, arg);
	}

	/**
	 * Alias of {@link Logger#debug(Marker, String, Object, Object)}.
	 */
	public static void debug(Marker marker, String format, Object arg1, Object arg2) {
		logger().debug(marker, format, arg1, arg2);
	}

	/**
	 * Alias of {@link Logger#debug(Marker, String, Object...)}.
	 */
	public static void debug(Marker marker, String format, Object... arguments) {
		logger().debug(marker, format, arguments);
	}

	/**
	 * Alias of {@link Logger#debug(Marker, String, Throwable)}.
	 */
	public static void debug(Marker marker, String msg, Throwable t) {
		logger().debug(marker, msg, t);
	}


	/**
	 * Alias of {@link Logger#atDebug()}.
	 */
	@CheckReturnValue
	public static LoggingEventBuilder atDebug() {
		return logger().atDebug();
	}

	/**
	 * Alias of {@link Logger#isInfoEnabled()}.
	 */
	public static boolean isInfoEnabled() {
		return logger().isInfoEnabled();
	}

	/**
	 * Alias of {@link Logger#info(String)}.
	 */
	public static void info(String msg) {
		logger().info(msg);
	}

	/**
	 * Alias of {@link Logger#info(String, Object)}.
	 */
	public static void info(String format, Object arg) {
		logger().info(format, arg);
	}

	/**
	 * Alias of {@link Logger#info(String, Object, Object)}.
	 */
	public static void info(String format, Object arg1, Object arg2) {
		logger().info(format, arg1, arg2);
	}

	/**
	 * Alias of {@link Logger#info(String, Object...)}.
	 */
	public static void info(String format, Object... arguments) {
		logger().info(format, arguments);
	}

	/**
	 * Alias of {@link Logger#info(String, Throwable)}.
	 */
	public static void info(String msg, Throwable t) {
		logger().info(msg, t);
	}

	/**
	 * Alias of {@link Logger#isInfoEnabled(Marker)}.
	 */
	public static boolean isInfoEnabled(Marker marker) {
		return logger().isInfoEnabled(marker);
	}

	/**
	 * Alias of {@link Logger#info(Marker, String)}.
	 */
	public static void info(Marker marker, String msg) {
		logger().info(marker, msg);
	}

	/**
	 * Alias of {@link Logger#info(Marker, String, Object)}.
	 */
	public void info(Marker marker, String format, Object arg) {
		logger().info(marker, format, arg);
	}

	/**
	 * Alias of {@link Logger#info(Marker, String, Object, Object)}.
	 */
	public static void info(Marker marker, String format, Object arg1, Object arg2) {
		logger().info(marker, format, arg1, arg2);
	}

	/**
	 * Alias of {@link Logger#info(Marker, String, Object...)}.
	 */
	public static void info(Marker marker, String format, Object... arguments) {
		logger().info(marker, format, arguments);
	}

	/**
	 * Alias of {@link Logger#info(Marker, String, Throwable)}.
	 */
	public static void info(Marker marker, String msg, Throwable t) {
		logger().info(marker, msg, t);
	}


	/**
	 * Alias of {@link Logger#atInfo()}.
	 */
	@CheckReturnValue
	public static LoggingEventBuilder atInfo() {
		return logger().atInfo();
	}

	/**
	 * Alias of {@link Logger#isWarnEnabled()}.
	 */
	public static boolean isWarnEnabled() {
		return logger().isWarnEnabled();
	}

	/**
	 * Alias of {@link Logger#warn(String)}.
	 */
	public static void warn(String msg) {
		logger().warn(msg);
	}

	/**
	 * Alias of {@link Logger#warn(String, Object)}.
	 */
	public static void warn(String format, Object arg) {
		logger().warn(format, arg);
	}

	/**
	 * Alias of {@link Logger#warn(String, Object, Object)}.
	 */
	public static void warn(String format, Object arg1, Object arg2) {
		logger().warn(format, arg1, arg2);
	}

	/**
	 * Alias of {@link Logger#warn(String, Object...)}.
	 */
	public static void warn(String format, Object... arguments) {
		logger().warn(format, arguments);
	}

	/**
	 * Alias of {@link Logger#warn(String, Throwable)}.
	 */
	public static void warn(String msg, Throwable t) {
		logger().warn(msg, t);
	}

	/**
	 * Alias of {@link Logger#isWarnEnabled(Marker)}.
	 */
	public static boolean isWarnEnabled(Marker marker) {
		return logger().isWarnEnabled(marker);
	}

	/**
	 * Alias of {@link Logger#warn(Marker, String)}.
	 */
	public static void warn(Marker marker, String msg) {
		logger().warn(marker, msg);
	}

	/**
	 * Alias of {@link Logger#warn(Marker, String, Object)}.
	 */
	public void warn(Marker marker, String format, Object arg) {
		logger().warn(marker, format, arg);
	}

	/**
	 * Alias of {@link Logger#warn(Marker, String, Object, Object)}.
	 */
	public static void warn(Marker marker, String format, Object arg1, Object arg2) {
		logger().warn(marker, format, arg1, arg2);
	}

	/**
	 * Alias of {@link Logger#warn(Marker, String, Object...)}.
	 */
	public static void warn(Marker marker, String format, Object... arguments) {
		logger().warn(marker, format, arguments);
	}

	/**
	 * Alias of {@link Logger#warn(Marker, String, Throwable)}.
	 */
	public static void warn(Marker marker, String msg, Throwable t) {
		logger().warn(marker, msg, t);
	}


	/**
	 * Alias of {@link Logger#atWarn()}.
	 */
	@CheckReturnValue
	public static LoggingEventBuilder atWarn() {
		return logger().atWarn();
	}

	/**
	 * Alias of {@link Logger#isErrorEnabled()}.
	 */
	public static boolean isErrorEnabled() {
		return logger().isErrorEnabled();
	}

	/**
	 * Alias of {@link Logger#error(String)}.
	 */
	public static void error(String msg) {
		logger().error(msg);
	}

	/**
	 * Alias of {@link Logger#error(String, Object)}.
	 */
	public static void error(String format, Object arg) {
		logger().error(format, arg);
	}

	/**
	 * Alias of {@link Logger#error(String, Object, Object)}.
	 */
	public static void error(String format, Object arg1, Object arg2) {
		logger().error(format, arg1, arg2);
	}

	/**
	 * Alias of {@link Logger#error(String, Object...)}.
	 */
	public static void error(String format, Object... arguments) {
		logger().error(format, arguments);
	}

	/**
	 * Alias of {@link Logger#error(String, Throwable)}.
	 */
	public static void error(String msg, Throwable t) {
		logger().error(msg, t);
	}

	/**
	 * Alias of {@link Logger#isErrorEnabled(Marker)}.
	 */
	public static boolean isErrorEnabled(Marker marker) {
		return logger().isErrorEnabled(marker);
	}

	/**
	 * Alias of {@link Logger#error(Marker, String)}.
	 */
	public static void error(Marker marker, String msg) {
		logger().error(marker, msg);
	}

	/**
	 * Alias of {@link Logger#error(Marker, String, Object)}.
	 */
	public void error(Marker marker, String format, Object arg) {
		logger().error(marker, format, arg);
	}

	/**
	 * Alias of {@link Logger#error(Marker, String, Object, Object)}.
	 */
	public static void error(Marker marker, String format, Object arg1, Object arg2) {
		logger().error(marker, format, arg1, arg2);
	}

	/**
	 * Alias of {@link Logger#error(Marker, String, Object...)}.
	 */
	public static void error(Marker marker, String format, Object... arguments) {
		logger().error(marker, format, arguments);
	}

	/**
	 * Alias of {@link Logger#error(Marker, String, Throwable)}.
	 */
	public static void error(Marker marker, String msg, Throwable t) {
		logger().error(marker, msg, t);
	}


	/**
	 * Alias of {@link Logger#atError()}.
	 */
	@CheckReturnValue
	public static LoggingEventBuilder atError() {
		return logger().atError();
	}

}
