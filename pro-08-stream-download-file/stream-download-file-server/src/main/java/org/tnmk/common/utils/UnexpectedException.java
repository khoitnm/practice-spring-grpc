package org.tnmk.common.utils;

/**
 * This is a very general exception.
 * We only throw this exception only if there's something wrong and the root cause is totally out of our awareness.<br/>
 * <p>
 * <pre>
 * For example:
 * With the logic of your code, you will have a sorted list. Then you call binarySearch(sortedList).
 * But inside your binarySearch(), you find out that input list was not sorted, throw UnexpectedException.
 * </pre>
 */
public class UnexpectedException extends RuntimeException {

    public UnexpectedException(final String message) {
        super(message);
    }

    public UnexpectedException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
