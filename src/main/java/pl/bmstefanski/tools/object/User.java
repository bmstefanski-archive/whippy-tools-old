package pl.bmstefanski.tools.object;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.object.util.UserUtils;

import java.util.UUID;

public class User {

    private final UUID uuid;
    private String name;
    private String ip;
    private boolean god;
    private long join;
    private long last;

    private User(Player player) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
        this.ip = player.getAddress().getHostName();

        UserUtils.addUser(this);
    }

    private User(UUID uuid) {
        this.uuid = uuid;

        UserUtils.addUser(this);
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return isOnline() ? getPlayer().getAddress().getHostName() : ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isGod() {
        return god;
    }

    public void setGod(boolean god) {
        this.god = god;
    }

    public long getJoin() {
        return join;
    }

    public void setJoin(long join) {
        this.join = join;
    }

    public long getLast() {
        return last;
    }

    public void setLast(long last) {
        this.last = last;
    }

    public boolean isOnline() {
        if (this.uuid == null) {
            return false;
        }
        Player player = Bukkit.getPlayer(this.uuid);
        return player != null;
    }

    public Player getPlayer() {
        if (!isOnline()) {
            return null;
        }
        return Bukkit.getPlayer(this.uuid);
    }

    public static User get(Player player) {
        for(User u : UserUtils.getUsers()) {
            if(u.getName() == null) continue;
            if(u.getName().equalsIgnoreCase(player.getName())) return u;
        }
        return new User(player);
    }

    public static User get(UUID uuid) {
        for(User u : UserUtils.getUsers()) if(uuid.equals(u.getUUID())) return u;
        return new User(uuid);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
