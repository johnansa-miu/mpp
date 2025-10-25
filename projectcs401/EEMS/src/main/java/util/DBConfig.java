package main.java.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Holds database configuration for the application.
 * Can load configuration from a properties file.
 */
public record DBConfig(String url, String username, String password) {
    private static final String PROPERTIES_FILE = "database.properties";

    /**
     * Loads DBConfig from a properties file.     *
     * @return DBConfig instance
     */
    public static DBConfig getDBConfig() throws IOException {
        Properties props = loadProperties();
        String url = props.getProperty("db.url");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");
        return new DBConfig(url, username, password);
    }

    private static Properties loadProperties() throws IOException {
        Properties properties = new Properties();

        try (InputStream input = DBConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new IOException("Unable to find " + PROPERTIES_FILE + " in resources folder");
            }
            properties.load(input);
        }

        return properties;
    }
}
