package org.owpk.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class Resources {
    public static final String USER_DETAILS_CONFIG = "cfg.yaml";
    private static final String PROTOCOL = "https://";
    private static final String HOST = "api2.hiveos.farm";
    private static final String BASE_PATH = "/api/v2";
    private static final String TARGET = PROTOCOL + HOST + BASE_PATH;


    public static  <T> T read(String yamlTarget, Class<T> clazz) {
        InputStream in = Resources.class
                .getClassLoader()
                .getResourceAsStream(yamlTarget);
        return new Yaml().loadAs(in, clazz);
    }

    public static <T> T read(String yamlTarget) {
        InputStream in = Resources.class
                .getClassLoader()
                .getResourceAsStream(yamlTarget);
        return new Yaml().load(in);
    }

    public static <T> void write(T obj, String resourceName) {
        try(FileWriter writer = new FileWriter(resourceName)) {
            new Yaml().dump(obj, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getTARGET() {
        return TARGET;
    }
}
