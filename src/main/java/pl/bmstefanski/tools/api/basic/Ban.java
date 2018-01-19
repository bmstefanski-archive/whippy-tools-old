package pl.bmstefanski.tools.api.basic;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface Ban {

    UUID getPunished();

    Player getPunishedPlayer();

    UUID getPunisher();

    Player getPunisherPlayer();

    String getReason();

    long getTime();

    void setReason(String reason);

    void setTime(long time);

}
