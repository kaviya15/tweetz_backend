package com.myapp.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;  // Add this import
import java.sql.SQLException;

public class DBConnection {
    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/xapplication");
        config.setUsername("");
        config.setPassword("");

        config.setMaximumPoolSize(10); // Maximum number of connections
        config.setMinimumIdle(2);     // Minimum number of idle connections
        config.setIdleTimeout(30000); // Idle timeout in milliseconds
        config.setMaxLifetime(1800000); // Max lifetime of a connection (30 minutes)
        config.setConnectionTimeout(30000); // Connection timeout in milliseconds

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}

