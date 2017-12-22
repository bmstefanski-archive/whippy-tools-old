package pl.bmstefanski.tools.runnable;

import org.bukkit.scheduler.BukkitRunnable;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.manager.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveDataTask extends BukkitRunnable {

    private final User user;
    private final DatabaseManager databaseManager;

    public SaveDataTask(User user) {
        this.user = user;
        this.databaseManager = DatabaseManager.getInstance();
    }

    @Override
    public void run() {
        try {
            String sql = "INSERT INTO `players` (`uuid`, `name`, `ip`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `uuid`=?, `name`=?, `ip`=?";

            PreparedStatement preparedStatement = databaseManager.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, user.getUUID().toString());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getIp());
            preparedStatement.setString(4, user.getUUID().toString());
            preparedStatement.setString(5, user.getName());
            preparedStatement.setString(6, user.getIp());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            System.out.println("Saved to database " + user.getName() + " | " + user.getIp());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
