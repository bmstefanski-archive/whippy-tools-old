package pl.bmstefanski.tools.basic.util;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import pl.bmstefanski.tools.basic.Ban;
import pl.bmstefanski.tools.manager.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BanUtils {

    private static final List<Ban> bans = new ArrayList<>();
    private static final DatabaseManager database = DatabaseManager.getInstance();

    public static void saveBans() {
        try {
            for (Ban ban : bans) {
                String sql = "UPDATE `bans` SET `reason`=?, `until`=? WHERE `punished`=?";

                PreparedStatement preparedStatement = database.getConnection().prepareStatement(sql);
                preparedStatement.setString(1, ban.getReason());
                preparedStatement.setLong(2, ban.getUntil());
                preparedStatement.setString(3, ban.getPunishedUUID().toString());

                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void loadBans() {
        try {
            String sql = "SELECT * FROM `bans`";

            PreparedStatement preparedStatement = database.getConnection().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                OfflinePlayer punishedPlayer = Bukkit.getOfflinePlayer(UUID.fromString(rs.getString("punished")));
                Ban ban = new Ban(punishedPlayer, rs.getString("punisher"));
                ban.setReason(rs.getString("reason"));
                ban.setUntil(rs.getLong("until"));

                bans.add(ban);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void addBan(Ban ban) {
        try {
            String sql = "INSERT INTO `bans` (`punisher`, `punished`, `until`, `reason`) VALUES (?, ?, ?, ?)";

            PreparedStatement preparedStatement = database.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, ban.getPunisherName());
            preparedStatement.setString(2, ban.getPunishedUUID().toString());
            preparedStatement.setLong(3, ban.getUntil());
            preparedStatement.setString(4, ban.getReason());

            preparedStatement.executeUpdate();
            bans.add(ban);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void removeBan(Ban ban) {
        try {
            String sql = "DELETE FROM `bans` WHERE `punished`=?";

            PreparedStatement preparedStatement = database.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, ban.getPunishedUUID().toString());

            preparedStatement.executeUpdate();
            bans.remove(ban);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isBanned(OfflinePlayer offlinePlayer) {
        Ban ban = BanUtils.getBan(offlinePlayer);

        return ban != null && ban.getUntil() != 0L && ban.getUntil() <= System.currentTimeMillis();
    }

    public static Ban getBan(OfflinePlayer offlinePlayer) {
        for (Ban ban : bans) {
            if (ban.getPunishedUUID().equals(offlinePlayer.getUniqueId())) return ban;
        }

        return null;
    }

    public static List<Ban> getBans() {
        return bans;
    }
}
