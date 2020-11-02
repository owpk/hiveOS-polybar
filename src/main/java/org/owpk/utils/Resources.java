package org.owpk.utils;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;

public class Resources {
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

    public static String getTARGET() {
        return TARGET;
    }
}
