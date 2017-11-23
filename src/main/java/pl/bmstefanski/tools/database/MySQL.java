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
    public void checkTable() {
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

    public static MySQL getInstance() {
        if (instance == null) instance = new MySQL();
        return instance;
    }
}
