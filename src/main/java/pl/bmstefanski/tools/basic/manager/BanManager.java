package pl.bmstefanski.tools.basic.manager;

public class BanManager {

//    private static final Tools PLUGIN = new Tools();
//    private static final List<Ban> BANS = new ArrayList<>();
//
//    public static void saveBans() {
//        try {
//            for (Ban ban : BANS) {
//                String sql = "UPDATE `BANS` SET `reason`=?, `until`=? WHERE `punished`=?";
//
//                PreparedStatement preparedStatement = PLUGIN.getDatabaseManager().getConnection().prepareStatement(sql);
//                preparedStatement.setString(1, ban.getReason());
//                preparedStatement.setLong(2, ban.getUntil());
//                preparedStatement.setString(3, ban.getPunishedUUID().toString());
//
//                preparedStatement.executeUpdate();
//                preparedStatement.close();
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    public static void loadBans() {
//        try {
//            String sql = "SELECT * FROM `BANS`";
//
//            PreparedStatement preparedStatement = PLUGIN.getDatabaseManager().getConnection().prepareStatement(sql);
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                OfflinePlayer punishedPlayer = Bukkit.getOfflinePlayer(UUID.fromString(rs.getString("punished")));
//                Ban ban = new Ban(punishedPlayer, rs.getString("punisher"));
//                ban.setReason(rs.getString("reason"));
//                ban.setUntil(rs.getLong("until"));
//
//                BANS.add(ban);
//            }
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    public static void addBan(Ban ban) {
//        try {
//            String sql = "INSERT INTO `BANS` (`punisher`, `punished`, `until`, `reason`) VALUES (?, ?, ?, ?)";
//
//            PreparedStatement preparedStatement = PLUGIN.getDatabaseManager().getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, ban.getPunisherName());
//            preparedStatement.setString(2, ban.getPunishedUUID().toString());
//            preparedStatement.setLong(3, ban.getUntil());
//            preparedStatement.setString(4, ban.getReason());
//
//            preparedStatement.executeUpdate();
//            BANS.add(ban);
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    public static void removeBan(Ban ban) {
//        try {
//            String sql = "DELETE FROM `BANS` WHERE `punished`=?";
//
//            PreparedStatement preparedStatement = PLUGIN.getDatabaseManager().getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, ban.getPunishedUUID().toString());
//
//            preparedStatement.executeUpdate();
//            BANS.remove(ban);
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    public static boolean isBanned(OfflinePlayer offlinePlayer) {
//        Ban ban = BanManager.getBan(offlinePlayer);
//
//        return ban != null && ban.getUntil() != 0L && ban.getUntil() <= System.currentTimeMillis();
//    }
//
//    public static Ban getBan(OfflinePlayer offlinePlayer) {
//        for (Ban ban : BANS) {
//            if (ban.getPunishedUUID().equals(offlinePlayer.getUniqueId())) return ban;
//        }
//
//        return null;
//    }
//
//    public static List<Ban> getBans() {
//        return BANS;
//    }
}
