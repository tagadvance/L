package com.tagadvance.l;

import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
			.filter(predicate)
			.skip(2)
			.findFirst()
			.orElseThrow();
	}

	private L() {
	}

}
