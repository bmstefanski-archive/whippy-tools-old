package pl.bmstefanski.tools.runnable;

import org.bukkit.scheduler.BukkitRunnable;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.manager.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class LoadDataTask extends BukkitRunnable {

    private final User user;
    private final DatabaseManager databaseManager;

    public LoadDataTask(User user) {
        this.user = user;
        this.databaseManager = DatabaseManager.getInstance();
    }

    @Override
    public void run() {
        try {
            String sql = "SELECT * FROM `players` WHERE `uuid` = ?";

            PreparedStatement preparedStatement = databaseManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, user.getUUID().toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user.setUUID(UUID.fromString(resultSet.getString("uuid")));
                user.setName(resultSet.getString("name"));
                user.setIp(resultSet.getString("ip"));
            }

            resultSet.close();
            preparedStatement.close();

            System.out.println("Loaded from database " + user.getName() + " | " + user.getIp());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
