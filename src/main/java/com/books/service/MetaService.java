package com.books.service;

import com.books.model.MetaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class MetaService {

    private static final String KEY_VERSION = "version";
    private static final String KEY_BUILD_DATE = "build.date";

    @Value("classpath:meta.properties")
    private Resource metaResource;

    public MetaDTO getMeta() {
        var meta = getMetaFromFile();

        var version = meta.get(KEY_VERSION);
        var buildDate = meta.get(KEY_VERSION);

        if ("%VERSION_PLACEHOLDER%".equalsIgnoreCase("version")) {

        }

        return new MetaDTO(meta.get(KEY_VERSION), meta.get(KEY_BUILD_DATE));
    }

    private Map<String, String> getMetaFromFile() {
        try (var propsIS = metaResource.getInputStream()) {
            var properties = new Properties();
            properties.load(propsIS);

            if (!properties.containsKey(KEY_VERSION)) {
                throw new RuntimeException(String.format("Key %s could not be found within properties metadata", KEY_VERSION));
            }

            if (!properties.containsKey(KEY_BUILD_DATE)) {
                throw new RuntimeException(String.format("Key %s could not be found within properties metadata", KEY_BUILD_DATE));
            }

            var result = new HashMap<String, String>();
            result.put(KEY_VERSION, properties.getProperty("version"));
            result.put(KEY_BUILD_DATE, properties.getProperty("build.date"));
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Cannot read properties from file");
        }
    }
}
