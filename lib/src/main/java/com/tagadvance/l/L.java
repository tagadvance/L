package com.tagadvance.l;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.function.Predicate;

public final class L {

    public static Logger logger() {
        final String className = getClassName();

        return LoggerFactory.getLogger(className);
    }

    public static Logger nestedLogger() {
        final String className = getNestClassName();

        return LoggerFactory.getLogger(className);
    }

    static String getClassName() {
        return getClassName(name -> !name.contains("$"));
    }

    static String getClassName(final Predicate<String> predicate) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        return Arrays.stream(stackTrace)
                .map(StackTraceElement::getClassName)
                .filter(name -> !name.startsWith("java.lang"))
                .filter(name -> !name.startsWith(L.class.getName()))
                .filter(predicate)
                .findFirst().orElseThrow();
    }

    static String getNestClassName() {
        return getClassName(name -> true);
    }

    private L() {
    }

}
