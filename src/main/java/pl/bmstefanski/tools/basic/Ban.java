package pl.bmstefanski.tools.basic;

import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class Ban {

    private final String punishedName;
    private final UUID punishedUUID;
    private final String punisher;
    private String reason;
    private long until;

    public Ban(OfflinePlayer punished, String punisher) {
        this.punishedName = punished.getName();
        this.punishedUUID = punished.getUniqueId();
        this.punisher = punisher;
    }

    public String getPunishedName() {
        return punishedName;
    }

    public String getPunisherName() {
        return punisher;
    }

    public UUID getPunishedUUID() {
        return punishedUUID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public long getUntil() {
        return until;
    }

    public void setUntil(long until) {
        this.until = until;
    }
}
