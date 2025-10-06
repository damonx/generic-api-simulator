package com.da.simulators.tools;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * Used in test cases to extract a resource from the classpath.
 */
public final class ResourceUtil
{
    /**
     * Should not be instantiated.
     */
    private ResourceUtil()
    {
        // Do not instantiate.
    }

    /**
     * Extract the contents of a file on the class path into a string.
     *
     * @param cls  The class whose classpath will be used.
     * @param fileName  The name of the file to extract.
     *
     * @return The contents of the file.
     * @throws IllegalArgumentException if the file doesn't exist, or can't be read.
     */
    public static String getFileContentAsString(final Class<?> cls, final String fileName)
    {
        try (InputStream stream = cls.getResourceAsStream(fileName)) {
            if (stream == null) {
                throw new IllegalArgumentException("Could not access " + fileName);
            }
            final StringWriter writer = new StringWriter();
            IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
            return writer.toString();
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read " + fileName);
        }
    }
}

