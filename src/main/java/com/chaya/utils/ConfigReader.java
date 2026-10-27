package com.chaya.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties prop = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties"))
        {

            prop.load(input);
        }
        catch (Exception e) {
            throw new RuntimeException("Could not load config.properties", e);
        }
    }

    public static String get(String key) {
        // 1st priority: value passed from command line / Jenkins (-Dbrowser=firefox)
        String fromCommandLine = System.getProperty(key);
        if (fromCommandLine != null && !fromCommandLine.isBlank()) {
            return fromCommandLine;
        }
        // 2nd priority: config.properties
        return prop.getProperty(key);
    }
}