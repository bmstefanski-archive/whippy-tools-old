package pl.bmstefanski.tools.database;

import pl.bmstefanski.tools.manager.DatabaseManager;
import pl.bmstefanski.tools.object.User;
import pl.bmstefanski.tools.object.util.UserUtils;
import pl.bmstefanski.tools.util.Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class MySQL implements Database {

    private static MySQL instance;
    private DatabaseManager databaseManager;

    private MySQL() {
        this.databaseManager = DatabaseManager.getInstance();
    }

    @Override
    public void checkTable() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS players(" +
                    "uuid VARCHAR(100) NOT NULL," +
                    "name VARCHAR(50) NOT NULL," +
                    "ip VARCHAR(32)," +
                    "PRIMARY KEY (uuid));";

            PreparedStatement preparedStatement = databaseManager.getConnection().prepareStatement(sql);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void loadData() {
        int loaded = 0;

        try {
            String sql = "SELECT * FROM players";

            PreparedStatement preparedStatement = databaseManager.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = User.get(UUID.fromString(resultSet.getString("uuid")));
                user.setName(resultSet.getString("name"));
                user.setIp(resultSet.getString("ip"));

                loaded++;
            }
            resultSet.close();
            preparedStatement.close();
            Utils.sendMessageToConsole("&eZaladowano &7" + loaded + " &egraczy.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void saveData() {
        int saved = 0;

        try {
            for (User user : UserUtils.getUsers()) {
                String sql = "INSERT INTO players (uuid, name, ip) VALUES ('"
                        + user.getUUID().toString()
                        + "','" + user.getName()
                        + "','" + user.getIp()
                        + "') ON DUPLICATE KEY UPDATE name='" + user.getName()
                        + "',name='" + user.getName()
                        + "',ip='" + user.getIp() + "';";

                PreparedStatement preparedStatement = databaseManager.getConnection().prepareStatement(sql);
                preparedStatement.executeUpdate();
                preparedStatement.close();

                saved++;
            }
            Utils.sendMessageToConsole("&eZapisano &7" + saved + " &egraczy.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static MySQL getInstance() {
        if (instance == null) instance = new MySQL();
        return instance;
    }
}
