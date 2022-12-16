package alghosproject.dao;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DAOLoader {
    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    static {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Properties properties = new Properties();
            try(InputStream resourceStream = loader.getResourceAsStream("db_properties.properties")) {
                properties.load(resourceStream);
            }
            Class.forName(properties.getProperty("driver_class_name"));
            connection = DriverManager.getConnection(properties.getProperty("url"),
                    properties.getProperty("username"), properties.getProperty("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
