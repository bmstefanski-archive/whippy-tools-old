package pl.bmstefanski.tools.storage;

import pl.bmstefanski.tools.api.storage.Database;
import pl.bmstefanski.tools.api.storage.Storage;
import pl.bmstefanski.tools.type.StatementType;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractDatabase implements Database, Storage {

    protected Connection connection;
    private final Map<StatementType, PreparedStatement> preparedStatementMap = new HashMap<>();

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

    public void addPreparedStatement(StatementType statementType, PreparedStatement statement) {
        preparedStatementMap.put(statementType, statement);
    }

    public PreparedStatement getPreparedStatement(StatementType statementType) throws SQLException {
        if (preparedStatementMap.isEmpty() || !preparedStatementMap.containsKey(statementType)) {
            prepareStatements();
        }

        PreparedStatement preparedStatement = preparedStatementMap.get(statementType);
        if (preparedStatement != null && preparedStatement.isClosed()) {
            prepareStatements();

            preparedStatement = preparedStatementMap.get(statementType);
        }

        if (preparedStatement == null) {
            throw new IllegalArgumentException("Invalid statement enum");
        }

        preparedStatement.clearParameters();

        return preparedStatement;
    }

    public void setUpTables() throws SQLException {
        PreparedStatement usersTable = getPreparedStatement(StatementType.CHECK_PLAYER);
        PreparedStatement bansTable = getPreparedStatement(StatementType.CHECK_BAN);

        usersTable.executeUpdate();
        bansTable.executeUpdate();

        usersTable.close();
        bansTable.close();
    }

    protected void prepareStatements() {
        try {
            preparedStatementMap.clear();
            connect();

            int returnKeys = isReturnGeneratedKeys() ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS;

            String loadPlayerSql = "SELECT * FROM `players` WHERE `uuid` = ?";
            PreparedStatement loadPlayer = getConnection().prepareStatement(loadPlayerSql, returnKeys);
            addPreparedStatement(StatementType.LOAD_PLAYER, loadPlayer);

            String savePlayerSql = "INSERT INTO `players` (`uuid`, `name`, `ip`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `uuid`=?, `name`=?, `ip`=?";
            PreparedStatement savePlayer = getConnection().prepareStatement(savePlayerSql, returnKeys);
            addPreparedStatement(StatementType.SAVE_PLAYER, savePlayer);

            String checkTableSql = "CREATE TABLE IF NOT EXISTS `players`(`uuid` VARCHAR(100) NOT NULL,`name` VARCHAR(50) NOT NULL,`ip` VARCHAR(32),PRIMARY KEY (`uuid`));";
            PreparedStatement checkTable = getConnection().prepareStatement(checkTableSql, returnKeys);
            addPreparedStatement(StatementType.CHECK_PLAYER, checkTable);

            String saveBansSql = "UPDATE `BANS` SET `reason`=?, `until`=? WHERE `punished`=?";
            PreparedStatement saveBans = getConnection().prepareStatement(saveBansSql, returnKeys);
            addPreparedStatement(StatementType.SAVE_BANS, saveBans);

            String loadBansSql = "SELECT * FROM `bans`";
            PreparedStatement loadBans = getConnection().prepareStatement(loadBansSql, returnKeys);
            addPreparedStatement(StatementType.LOAD_BANS, loadBans);

            String addBanSql = "INSERT INTO `BANS` (`punisher`, `punished`, `until`, `reason`) VALUES (?, ?, ?, ?)";
            PreparedStatement addBan = getConnection().prepareStatement(addBanSql, returnKeys);
            addPreparedStatement(StatementType.ADD_BAN, addBan);

            String removeBanSql = "DELETE FROM `BANS` WHERE `punished`=?";
            PreparedStatement removeBan = getConnection().prepareStatement(removeBanSql, returnKeys);
            addPreparedStatement(StatementType.REMOVE_BAN, removeBan);

            String checkBanSql = "CREATE TABLE IF NOT EXISTS `bans`(`punisher` VARCHAR(100) NOT NULL,`punished` VARCHAR(100) NOT NULL,`until` BIGINT NOT NULL,`reason` VARCHAR(250) NOT NULL);";
            PreparedStatement checkBan = getConnection().prepareStatement(checkBanSql, returnKeys);
            addPreparedStatement(StatementType.CHECK_BAN, checkBan);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
