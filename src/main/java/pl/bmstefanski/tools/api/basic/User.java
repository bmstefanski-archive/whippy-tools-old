package pl.bmstefanski.tools.api.basic;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface User {

    UUID getUUID();

    String getName();

    String getIp();

    Player getPlayer();

    void setUUID(UUID uuid);

    void setName(String name);

    void setIp(String ip);

    void setGod(boolean god);

    boolean isGod();

    boolean isOnline();

    boolean isBanned();
}
