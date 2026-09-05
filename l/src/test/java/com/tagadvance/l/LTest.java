package com.tagadvance.l;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.Callable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.MarkerFactory;

class LTest {

	@Test
	void logger() {
		final var logger = L.log();

		assertNotNull(logger);
	}

	@Test
	void nestedLogger() {
		final var logger = L.nestedLogger();

		assertNotNull(logger);
	}

	/**
	 * Test {@link L#getClassName()} instead of {@link L#logger()} because {@link Logger#getName()}
	 * always returns "NOP".
	 */
	@Test
	void loggerReturnsNameOfEnclosingClass() {
		final var className = L.getClassName();

		assertEquals(LTest.class.getName(), className);
	}

	@Test
	void loggerReturnsNameOfEnclosingClassFromIntermediateClass() {
		final var className = new IntermediateClass().call();

		assertEquals(LTest.class.getName(), className);
	}

	@Test
	void loggerReturnsNameOfEnclosingClassFromInnerClass() {
		final var className = new IntermediateStaticClass.InnerStaticClass().call();

		assertEquals(LTest.class.getName(), className);
	}

	private static class IntermediateClass implements Callable<String> {

		@Override
		public String call() {
			return L.getClassName();
		}

	}

	private static class IntermediateStaticClass implements Callable<String> {

		@Override
		public String call() {
			return L.getClassName();
		}

		private static class InnerStaticClass implements Callable<String> {

			@Override
			public String call() {
				return L.getClassName();
			}

		}

	}

	@Test
	void nestReturnsNameOfEnclosingClass() {
		final var className = L.getNestClassName();

		assertEquals(LTest.class.getName(), className);
	}

	@Test
	void nestReturnsNameOfEnclosingClassFromIntermediateClass() {
		final var className = new IntermediateNestClass().call();

		assertEquals(LTest.IntermediateNestClass.class.getName(), className);
	}

	@Test
	void nestReturnsNameOfEnclosingClassFromInnerClass() {
		final var className = new IntermediateStaticNestClass.InnerStaticNestClass().call();

		assertEquals(LTest.IntermediateStaticNestClass.InnerStaticNestClass.class.getName(),
			className);
	}

	private static class IntermediateNestClass implements Callable<String> {

		@Override
		public String call() {
			return L.getNestClassName();
		}

	}

	private static class IntermediateStaticNestClass implements Callable<String> {

		@Override
		public String call() {
			return L.getNestClassName();
		}

		private static class InnerStaticNestClass implements Callable<String> {

			@Override
			public String call() {
				return L.getNestClassName();
			}

		}

	}

	/**
	 * The five {@code (Marker, String, Object)} overloads were declared without {@code static},
	 * which made them unreachable on a final class with a private constructor. This does not assert
	 * anything at run time - it fails to compile if any of them goes back to being an instance
	 * method.
	 */
	@Test
	@DisplayName("every Marker alias is callable without an instance")
	void markerAliasesAreStatic() {
		final var marker = MarkerFactory.getMarker("test");

		L.trace(marker, "{}", "arg");
		L.debug(marker, "{}", "arg");
		L.info(marker, "{}", "arg");
		L.warn(marker, "{}", "arg");
		L.error(marker, "{}", "arg");
	}

}
