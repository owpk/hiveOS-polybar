package org.owpk.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Resources {
    private static final String PROTOCOL = "https://";
    private static final String HOST = "api2.hiveos.farm";
    private static final String BASE_PATH = "/api/v2";
    private static final String TARGET = PROTOCOL + HOST + BASE_PATH;

    public static String getTARGET() {
        return TARGET;
    }

    //TODO config validator
    //TODO autogenerate config
    public static class ConfigReader {
        private static final String path = "/home/owpk/hiveclient/";
        private static final String CONFIG_NAME = "/home/owpk/.config/polybar/scripts/hiveclient.conf";
        private static Properties properties;
        static {
            try(FileInputStream inputStream = new FileInputStream(CONFIG_NAME)) {
                properties = new Properties();
                properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public static Properties getProps() {
            return properties;
        }
    }
}
