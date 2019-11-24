package org.tnmk.common.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * version: 1.0.1
 * 2018/04/04
 *
 * @author khoi.tran on 7/5/17.
 */
public final class IOUtils {

    private IOUtils() {
    }

    /**
     * @param path a relative path in classpath. E.g. "images/email/logo.png"
     *             From Class, the path is relative to the package of the class unless you include a leading slash.
     *             So if you don't want to use the current package, include a slash like this: "/SomeTextFile.txt"
     * @return
     */
    public static String loadTextFileInClassPath(final String path) {
        try {
            final InputStream inputStream = validateExistInputStreamFromClassPath(path);
            return org.apache.commons.io.IOUtils.toString(inputStream, Charset.forName("UTF-8"));
        } catch (final IOException e) {
            final String msg = String.format("Cannot load String from '%s'", path);
            throw new UnexpectedException(msg, e);
        }
    }

    /**
     * @param path a relative path in classpath. E.g. "images/email/logo.png"
     *             From Class, the path is relative to the package of the class unless you include a leading slash.
     *             So if you don't want to use the current package, include a slash like this: "/SomeTextFile.txt"
     * @return
     */
    public static byte[] loadBinaryFileInClassPath(final String path) {
        try {
            final InputStream inputStream = validateExistInputStreamFromClassPath(path);
            return org.apache.commons.io.IOUtils.toByteArray(inputStream);
        } catch (final IOException e) {
            final String msg = String.format("Cannot load String from '%s'", path);
            throw new UnexpectedException(msg, e);
        }
    }

    public static InputStream validateExistInputStreamFromClassPath(final String path) {
        final InputStream inputStream = loadInputStreamFileInClassPath(path);
        if (inputStream == null) {
            throw new UnexpectedException(String.format("Not found file from '%s'", path));
        }
        return inputStream;
    }

    /**
     * @param path view more in {@link #loadBinaryFileInClassPath(String)}
     * @return
     */
    public static InputStream loadInputStreamFileInClassPath(final String path) {
        return IOUtils.class.getResourceAsStream(path);
    }

    public static void writeTextToFile(String absolutePath, String content) {
        try {
            Files.write(Paths.get(absolutePath), content.getBytes());
        } catch (IOException e) {
            final String msg = String.format("Cannot write data to '%s'", absolutePath);
            throw new UnexpectedException(msg, e);
        }
    }

    public static byte[] readBytesFromSystemFile(String path) {
        try {
            return Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            final String msg = String.format("Cannot read bytes from '%s'", path);
            throw new UnexpectedException(msg, e);
        }
    }

    public static String loadTextFileInSystem(final String path) {
        byte[] bytes = readBytesFromSystemFile(path);
        return new String(bytes);
    }
    /**
     * @param path the path could be relative path or absolute path.
     * @return
     */
    public static FileInputStream loadInputStreamSystemFile(String path) {
        try {
            return new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            throw new UnexpectedException(String.format("Cannot load InputStream from file '%s'", path), e);
        }
    }
}
