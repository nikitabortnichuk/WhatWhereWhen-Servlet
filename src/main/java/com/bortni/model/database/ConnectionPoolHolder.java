package com.bortni.model.database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionPoolHolder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPoolHolder.class);
    private static volatile DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    Properties properties = new Properties();
                    String propFileName = "database.properties";

                    try (InputStream inputStream = Thread.currentThread()
                            .getContextClassLoader()
                            .getResourceAsStream(propFileName)) {

                        if (inputStream != null) {
                            properties.load(inputStream);
                        } else {
                            LOGGER.error("Connection error: Class loader input stream is null");
                        }
                        Class.forName(properties.getProperty("db.connection.driver"));
                        dataSource = getBasicDataSource(properties);

                    } catch (IOException | ClassNotFoundException e) {
                        LOGGER.error("Connection error");
                        System.out.println("error");
                    }
                }
            }
        }

        return dataSource;
    }

    private static BasicDataSource getBasicDataSource(Properties properties) {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(properties.getProperty("db.connection.url"));
        ds.setUsername(properties.getProperty("db.connection.username"));
        ds.setPassword(properties.getProperty("db.connection.password"));
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
        return ds;
    }

}
