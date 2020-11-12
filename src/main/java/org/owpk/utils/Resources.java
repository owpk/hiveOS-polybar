package org.owpk.utils;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.Getter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.owpk.entities.jsonConfig.JsonConfig;
import org.owpk.entities.jsonConfig.JsonData;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

public class Resources {
   private static final Logger log = LogManager.getLogger(Resources.class);
   public static final String API_PROTOCOL = "https://";
   public static final String API_HOST = "api2.hiveos.farm";
   public static final String API_BASE_PATH = "/api/v2";
   public static final String API_TARGET = API_PROTOCOL + API_HOST + API_BASE_PATH;
   public static final String HOME = System.getProperty("user.home");
   public static File CURRENT_DIR;
   private static Level level = Level.INFO;

   public static Level getLevel() {
      return level;
   }

   public static void setLevel(Level level) {
      Resources.level = level;
   }

   static {
      try {
         String path = Resources.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
         String decoded = URLDecoder.decode(path, StandardCharsets.UTF_8);
         CURRENT_DIR = new File(decoded).getParentFile();
      } catch (URISyntaxException e) {
         log.error(e);
      }
   }

   //TODO config validator
   //TODO autogenerate config
   public static class ConfigReader {
      private static final String PATH = CURRENT_DIR.getPath();
      private static final String CONFIG_NAME = PATH + "/hiveclient.conf";
      private static final String JSON_CONFIG_NAME = PATH + "/settings.json";
      private static Properties properties;
      private static JsonData jsonConfigList;

      static {
         try (FileInputStream inputStream = new FileInputStream(CONFIG_NAME);
              FileInputStream jsonData = new FileInputStream(JSON_CONFIG_NAME)) {
            properties = new Properties();
            properties.load(inputStream);
            jsonConfigList = new JsonMapper().readValue(jsonData, JsonData.class);
         } catch (IOException e) {
            log.error(e);
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
