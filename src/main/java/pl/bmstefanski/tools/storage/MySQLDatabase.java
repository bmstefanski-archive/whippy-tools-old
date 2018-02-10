package pl.bmstefanski.tools.storage;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLDatabase extends AbstractDatabase {

    private HikariDataSource dataSource;

    private final String serverName;
    private final int port;
    private final String databaseName;
    private final String user;
    private final String password;

    public MySQLDatabase(String serverName, int port, String databaseName, String user, String password) {
        this.serverName = serverName;
        this.port = port;
        this.databaseName = databaseName;
        this.user = user;
        this.password = password;
        this.dataSource = new HikariDataSource();

        connect();
    }

    @Override
    public void connect() {
        this.dataSource.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        this.dataSource.addDataSourceProperty("serverName", serverName);
        this.dataSource.addDataSourceProperty("port", port);
        this.dataSource.addDataSourceProperty("databaseName", databaseName);
        this.dataSource.addDataSourceProperty("user", user);
        this.dataSource.addDataSourceProperty("password", password);
        this.dataSource.setMaximumPoolSize(10);
    }

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
