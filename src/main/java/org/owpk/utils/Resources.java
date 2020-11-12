package org.owpk.utils;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.owpk.entities.jsonConfig.JsonConfig;
import org.owpk.entities.jsonConfig.JsonData;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
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
        private static final String JSON_CONFIG_NAME = "/home/owpk/.config/polybar/scripts/hiveclient.settings";
        private static Properties properties;
        private static JsonData jsonConfigList;

        static {
            try(FileInputStream inputStream = new FileInputStream(CONFIG_NAME);
                FileInputStream jsonData = new FileInputStream(JSON_CONFIG_NAME)) {
                properties = new Properties();
                properties.load(inputStream);
                jsonConfigList = new JsonMapper().readValue(jsonData, JsonData.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static <T extends JsonConfig> JsonConfig defaultFilter(List<T> list, String name) {
            return list.stream()
                   .filter(x -> x.getObjectName().equals(name))
                   .findAny()
                   .orElseThrow(() -> new RuntimeException("check config"));
        }

        public static JsonConfig getJsonConfig(String name) {
            return defaultFilter(jsonConfigList.getData(), name);
        }

        public static JsonConfig getJsonConfig(JsonConfig jsonConfig, String name) {
            return defaultFilter(jsonConfig.getEntitiesToShow(), name);
        }

        public static Properties getProps() {
            return properties;
        }

        public static JsonData getJsonConfigList() {
            return jsonConfigList;
        }
    }

}
