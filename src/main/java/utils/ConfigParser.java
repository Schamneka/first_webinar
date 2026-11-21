package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public final class ConfigParser {

    private static final Properties CONFIG = load();

    private ConfigParser() {
    }

    public static String getProperty(String propertyName) {
        String value = CONFIG.getProperty(propertyName);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Отсутствует или пуст параметр: " + propertyName);
        }
        return value;
    }


    private static Properties load() {
        Properties properties = new Properties();
        try (InputStream in = ConfigParser.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (in == null) {
                throw new UncheckedIOException(new IOException(
                        "config.properties не найден в classpath (src/test/resources)"));
            }
            properties.load(new InputStreamReader(in, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException("Не удалось прочитать config.properties", e);
        }
        return properties;
    }
}

