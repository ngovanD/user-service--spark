package vn.cmc.du21.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.Properties;

public class Setting {
    private static final String FILE_CONFIG = "config.properties";

    public static final String DB_DRIVER_CLASS;
    public static final String DB_URL;
    public static final String DB_USER;
    public static final String DB_PWD;
    public static final Integer DB_INITIAL_POOL_SIZE;
    public static final Integer DB_MAX_POOL_SIZE;

    public static final Integer EXTERNAL_PORT;

    public static final Integer N_HTTP_CONN_POOL = 100;
    public static final Integer N_THREAD = Math.min(Runtime.getRuntime().availableProcessors() - 1, 10);;



    static {
        try {
            try (InputStream fileInputStream = Setting.class.getClassLoader().getResourceAsStream(FILE_CONFIG)) {
                Properties properties = new Properties();
                properties.load(fileInputStream);

                //init setting
                DB_DRIVER_CLASS = properties.getProperty("db.driver_class");
                DB_URL = properties.getProperty("db.url");
                DB_USER = properties.getProperty("db.user");
                DB_PWD = properties.getProperty("db.password");
                DB_INITIAL_POOL_SIZE = Integer.valueOf(properties.getProperty("db.initial_pool_size"));
                DB_MAX_POOL_SIZE = Integer.valueOf(properties.getProperty("db.max_pool_size"));
                EXTERNAL_PORT = Integer.valueOf(properties.getProperty("db.port"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
