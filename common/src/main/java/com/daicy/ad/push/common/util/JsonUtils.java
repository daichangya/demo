package com.daicy.ad.push.common.util;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class JsonUtils {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);


    private final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(JsonParser.Feature.INTERN_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.CANONICALIZE_FIELD_NAMES, true);
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private JsonUtils() {
    }

    public static String encode(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonGenerationException e) {
            logger.error("encode(Object)", e); //$NON-NLS-1$
        } catch (JsonMappingException e) {
            logger.error("encode(Object)", e); //$NON-NLS-1$
        } catch (IOException e) {
            logger.error("encode(Object)", e); //$NON-NLS-1$
        }
        return null;
    }

    public static <T> T decode(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (JsonParseException e) {
            logger.error("decode(String, Class<T>)", e); //$NON-NLS-1$
        } catch (JsonMappingException e) {
            logger.error("decode(String, Class<T>)", e); //$NON-NLS-1$
        } catch (IOException e) {
            logger.error("decode(String, Class<T>)", e); //$NON-NLS-1$
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T decode(String json, TypeReference<T> reference) {
        try {
            return (T) objectMapper.readValue(json, reference);
        } catch (JsonParseException e) {
            logger.error("decode(String, TypeReference<T>)", e);
        } catch (JsonMappingException e) {
            logger.error("decode(String, TypeReference<T>)", e);
        } catch (IOException e) {
            logger.error("decode(String, TypeReference<T>)", e);
        }
        return null;
    }

}
