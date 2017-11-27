package pl.bmstefanski.tools.database;

import pl.bmstefanski.tools.manager.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQL implements Database {

    private static MySQL instance;
    private DatabaseManager databaseManager;

    private MySQL() {
        this.databaseManager = DatabaseManager.getInstance();
    }

    @Override
    public void checkUser() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS `players`(" +
                    "`uuid` VARCHAR(100) NOT NULL," +
                    "`name` VARCHAR(50) NOT NULL," +
                    "`ip` VARCHAR(32)," +
                    "PRIMARY KEY (`uuid`));";

            PreparedStatement preparedStatement = databaseManager.getConnection().prepareStatement(sql);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void checkBan() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS `bans`(" +
                    "`punisher` VARCHAR(100) NOT NULL," +
                    "`punished` VARCHAR(100) NOT NULL," +
                    "`until` BIGINT NOT NULL," +
                    "`reason` VARCHAR(250) NOT NULL);";

            PreparedStatement preparedStatement = databaseManager.getConnection().prepareStatement(sql);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static MySQL getInstance() {
        if (instance == null) instance = new MySQL();
        return instance;
    }
}
