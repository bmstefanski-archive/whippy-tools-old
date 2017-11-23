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
            String sql = "INSERT INTO `players` (`uuid`, `name`, `ip`) VALUES ('"
                    + user.getUUID().toString()
                    + "','" + user.getName()
                    + "','" + user.getIp()
                    + "') ON DUPLICATE KEY UPDATE `uuid`='" + user.getUUID()
                    + "',`name`='" + user.getName()
                    + "',`ip`='" + user.getIp() + "';";

            PreparedStatement preparedStatement = databaseManager.getConnection().prepareStatement(sql);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            System.out.println("Saved to database " + user.getName() + " | " + user.getIp());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
