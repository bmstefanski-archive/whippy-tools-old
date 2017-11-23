package pl.bmstefanski.tools.basic;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.basic.util.UserUtils;

import java.util.UUID;

public class User {

    private UUID uuid;
    private String name;
    private String ip;
    private boolean god;

    private User(UUID uuid) {
        this.uuid = uuid;

        UserUtils.addUser(this);
    }

    private User(Player player) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
        this.ip = player.getAddress().getHostName();

        UserUtils.addUser(this);
    }

    public static User get(UUID uuid) {
        for (User u : UserUtils.getUsers()) {
            if (u.getName() == null) continue;
            if (uuid.equals(u.getUUID())) return u;
        }
        return new User(uuid);
    }

    public static User get(Player player) {
        for (User u : UserUtils.getUsers()) {
            if (u.getName() == null) continue;
            if (u.getName().equalsIgnoreCase(player.getName())) return u;
        }
        return new User(player);
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
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

    @Override
    public String toString() {
        return this.name;
    }
}
