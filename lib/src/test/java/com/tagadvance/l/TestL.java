package com.tagadvance.l;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestL {

    @Test
    void logger() {
        final Logger logger = L.logger();

        assertNotNull(logger);
    }

    @Test
    void nestedLogger() {
        final Logger logger = L.nestedLogger();

        assertNotNull(logger);
    }

    /**
     * Test {@link L#getClassName()} instead of {@link L#logger()} because {@link Logger#getName()} always returns "NOP".
     */
    @Test
    void loggerReturnsNameOfEnclosingClass() {
        final String className = L.getClassName();

        assertEquals(TestL.class.getName(), className);
    }

    @Test
    void loggerReturnsNameOfEnclosingClassFromIntermediateClass() {
        final String className = new IntermediateClass().call();

        assertEquals(TestL.class.getName(), className);
    }

    @Test
    void loggerReturnsNameOfEnclosingClassFromInnerClass() {
        final String className = new IntermediateStaticClass.InnerStaticClass().call();

        assertEquals(TestL.class.getName(), className);
    }

    private class IntermediateClass implements Callable<String> {

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
        final String className = L.getNestClassName();

        assertEquals(TestL.class.getName(), className);
    }

    @Test
    void nestReturnsNameOfEnclosingClassFromIntermediateClass() {
        final String className = new IntermediateNestClass().call();

        assertEquals(TestL.IntermediateNestClass.class.getName(), className);
    }

    @Test
    void nestReturnsNameOfEnclosingClassFromInnerClass() {
        final String className = new IntermediateStaticNestClass.InnerStaticNestClass().call();

        assertEquals(TestL.IntermediateStaticNestClass.InnerStaticNestClass.class.getName(), className);
    }

    private class IntermediateNestClass implements Callable<String> {

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

}
