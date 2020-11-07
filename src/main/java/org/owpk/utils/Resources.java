package org.owpk.utils;

import java.io.IOException;
import java.util.Properties;

public class Resources {
    private static final String PROTOCOL =  "https://";
    private static final String HOST =      "api2.hiveos.farm";
    private static final String BASE_PATH = "/api/v2";
    private static final String TARGET = PROTOCOL + HOST + BASE_PATH;

    public static String getTARGET() {
        return TARGET;
    }

    public static class ConfigReader {
        private static final String path = "/home/owpk/hiveclient/";
        private Properties properties;

        public ConfigReader(String confName) {
            try {
                properties = new Properties();
                properties.load(Resources.class.getClassLoader().getResourceAsStream(confName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public Properties getProps(){
            return properties;
        }
    }
}
