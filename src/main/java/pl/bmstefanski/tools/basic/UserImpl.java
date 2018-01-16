package pl.bmstefanski.tools.basic;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.basic.manager.UserManager;

import java.util.UUID;

public class UserImpl implements User {

    private UUID uuid;
    private String name;
    private String ip;
    private boolean god;

    public UserImpl(UUID uuid) {
        this.uuid = uuid;
        this.name = Bukkit.getOfflinePlayer(uuid).getName();

        UserManager.addUser(this);
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIp() {
        return isOnline() ? getPlayer().getAddress().getHostName() : ip;
    }

    @Override
    public Player getPlayer() {
        if (!isOnline()) {
            return null;
        }

        return Bukkit.getPlayer(this.uuid);
    }

    @Override
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public void setGod(boolean god) {
        this.god = god;
    }

    @Override
    public boolean isOnline() {
        if (this.uuid == null) {
            return false;
        }

        Player player = Bukkit.getPlayer(this.uuid);
        return player != null;
    }

    @Override
    public boolean isGod() {
        return god;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
