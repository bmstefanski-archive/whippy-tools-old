package pl.bmstefanski.tools.storage;

import pl.bmstefanski.tools.api.storage.Database;
import pl.bmstefanski.tools.api.storage.Storage;
import pl.bmstefanski.tools.type.PreparedStatements;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractDatabase implements Database, Storage {

    protected Connection connection;
    private final Map<String, PreparedStatement> preparedStatementMap = new HashMap<>();

    @Override
    public boolean checkConnection() throws SQLException {
        return connection != null && !connection.isClosed();
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public boolean setUp() {
        return connect();
    }

    @Override
    public boolean closeConnection() throws SQLException {
        if (connection == null) return false;

        connection.close();
        return true;
    }

    public abstract boolean connect();

    public abstract int returnKey(Statement statement);

    public abstract boolean isReturnGeneratedKeys();

    public void addPreparedStatement(String name, PreparedStatement statement) {
        preparedStatementMap.put(name, statement);
    }

    public PreparedStatement getPreparedStatement(String name) throws SQLException {
        if (preparedStatementMap.isEmpty() || !preparedStatementMap.containsKey(name)) {
            prepareStatements();
        }

        PreparedStatement preparedStatement = preparedStatementMap.get(name);
        if (preparedStatement != null && preparedStatement.isClosed()) {
            prepareStatements();

            preparedStatement = preparedStatementMap.get(name);
        }

        if (preparedStatement == null) {
            throw new IllegalArgumentException("Invalid statement enum");
        }

        preparedStatement.clearParameters();

        return preparedStatement;
    }

    public void setUpTables() throws SQLException {
        PreparedStatement preparedStatement = getPreparedStatement(PreparedStatements.CHECK_PLAYER.name());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    protected void prepareStatements() {
        try {
            preparedStatementMap.clear();
            connect();

            int returnKeys = isReturnGeneratedKeys() ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS;

            String loadPlayerSql = "SELECT * FROM `players` WHERE `uuid` = ?";
            PreparedStatement loadPlayer = getConnection().prepareStatement(loadPlayerSql, returnKeys);
            addPreparedStatement(PreparedStatements.LOAD_PLAYER.name(), loadPlayer);

            String savePlayerSql = "INSERT INTO `players` (`uuid`, `name`, `ip`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `uuid`=?, `name`=?, `ip`=?";
            PreparedStatement savePlayer = getConnection().prepareStatement(savePlayerSql, returnKeys);
            addPreparedStatement(PreparedStatements.SAVE_PLAYER.name(), savePlayer);

            String checkTableSql = "CREATE TABLE IF NOT EXISTS `players`(`uuid` VARCHAR(100) NOT NULL,`name` VARCHAR(50) NOT NULL,`ip` VARCHAR(32),PRIMARY KEY (`uuid`));";
            PreparedStatement checkTable = getConnection().prepareStatement(checkTableSql, returnKeys);
            addPreparedStatement(PreparedStatements.CHECK_PLAYER.name(), checkTable);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
