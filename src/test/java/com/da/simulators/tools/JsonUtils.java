package com.da.simulators.tools;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Json Util class does MARSHALLING: convert string JSON or file into Object - UNMARSHALLING:
 * convert Object into string JSON.
 */
public final class JsonUtils
{

    /**
     * Private constructor.
     */
    private JsonUtils()
    {
        // No, you can't instantiate this.
    }

    /**
     * Converts a JSON string to a instance of a specified class.
     *
     * @param <T> - the type of the target object
     * @param str - JSON string
     * @param clazz - the class info of the target object
     * @return - Converted object
     * @throws IOException if the conversion fails
     */
    public static <T> T json2Object(final String str, final Class<T> clazz)
        throws IOException
    {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(str, clazz);
    }

    /**
     * Converts a specified object to a JSON string.
     *
     * @param <T> - the type of the passed in object
     * @param obj - the object to be converted
     * @return String - converted JSON string
     * @throws IOException if the conversion fails
     */
    public static <T> String object2Json(final T obj) throws IOException
    {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * Converts a specified JSON file to an instances of specified class.
     *
     * @param <T> - the type of the target object
     * @param fileName - the path to the file
     * @param clazz - the class info of the target object
     * @return - Converted object
     * @throws IOException if the conversion fails
     */
    public static <T> T jsonFile2Object(final String fileName, final Class<T> clazz)
        throws IOException
    {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try (InputStream fileStream = clazz.getResourceAsStream(fileName)) {
            return objectMapper.readValue(fileStream, clazz);
        }
    }

    /**
     * Converts an array of objects in a JSON file into a List of objects of a specified type.
     *
     * @param <T> The type of the objects to be stored in the list.
     * @param fileName The name of the JSON file to be deserialized.
     * @param valueTypeRef A TypeReference to the List of objects to be deserialized.
     * @return A List of objects of type T.
     * @throws IOException If there is an error reading the JSON file.
     */
    public static <T> List<T> jsonArrayFile2List(final String fileName, final TypeReference<List<T>> valueTypeRef) throws IOException
    {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // Read JSON list into a list of Type T
        try (InputStream fileStream = JsonUtils.class.getResourceAsStream(fileName)) {
            return objectMapper.readValue(fileStream, valueTypeRef);
        }
    }
}
