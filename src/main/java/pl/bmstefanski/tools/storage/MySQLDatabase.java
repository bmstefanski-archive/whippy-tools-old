package pl.bmstefanski.tools.storage;

import pl.bmstefanski.tools.util.reflect.Reflections;

import java.sql.*;

public class MySQLDatabase extends AbstractDatabase {

    private final String username;
    private final String database;
    private final String password;
    private final String hostname;
    private final String port;

    public MySQLDatabase(String username, String database, String password, String hostname, String port) {
        this.username = username;
        this.database = database;
        this.password = password;
        this.hostname = hostname;
        this.port = port;

        if (!setUp()) {
            throw new RuntimeException("Database is not setup!");
        }
    }

    @Override
    public void openConnection() throws SQLException {
        if (checkConnection()) {
            return;
        }

        Reflections.getClass("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database + "?autoReconnect=true", username, password);
    }

    @Override
    public boolean connect() {
        try {
            if (checkConnection()) {
                return true;
            }

            openConnection();
            setUpTables();
            prepareStatements();

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();

            return false;
        }
    }

    @Override
    public int returnKey(Statement statement) {
        try {
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();

            int id = resultSet.getInt(1);

            if (id == 0) {
                throw new RuntimeException("Could not get generated keys");
            }

            return id;
        } catch (SQLException ex) {
            throw new RuntimeException("Could not get generated keys", ex);
        }
    }

    @Override
    public boolean isReturnGeneratedKeys() {
        return true;
    }
}

