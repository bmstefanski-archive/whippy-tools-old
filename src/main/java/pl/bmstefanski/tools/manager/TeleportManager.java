package pl.bmstefanski.tools.manager;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.configuration.Messages;
import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.ReflectionUtils;
import pl.bmstefanski.tools.util.packet.PacketPlayOutTitle;

import java.util.HashMap;

public class TeleportManager {

    private static HashMap<Player, BukkitTask> COUNTDOWN;
    private final Tools plugin = Tools.getInstance();
    private Player player;
    private int count;

    static {
        COUNTDOWN = new HashMap<>();
    }

    public TeleportManager(Player player) {
        this.player = player;
        this.count = plugin.getConfig().getInt("countdown");
    }

    public void start(Location location) {
        if (COUNTDOWN.containsKey(player)) {
            MessageUtils.sendMessage(player, Messages.TELEPORT_CURRENTLY_TELEPORTING);
            return;
        }

        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {

            String bar = "";
            for (int i = 0; i < count; i++) {
                bar = MessageUtils.fixColor(StringUtils.replace(Messages.TELEPORT_COUNTING,"%count%", i + ""));
            }

            ReflectionUtils.sendPacket(player, new PacketPlayOutTitle(bar, -1, -1, -1).getActionBar());

            if (count == 0) {
                player.teleport(location);
                COUNTDOWN.get(player).cancel();
                COUNTDOWN.remove(player);

                MessageUtils.sendMessage(player, Messages.TELEPORT_SUCCESS);
                return;
            }
            count--;

        }, 0, 20);
        COUNTDOWN.put(player, bukkitTask);
    }

    public void stop() {
        if (!COUNTDOWN.containsKey(player)) {
            return;
        }

        COUNTDOWN.get(player).cancel();
        COUNTDOWN.remove(player);

        MessageUtils.sendMessage(player, Messages.TELEPORT_CANCELLED);
        ReflectionUtils.sendPacket(player, new PacketPlayOutTitle(MessageUtils.fixColor(Messages.TELEPORT_CANCELLED), -1, -1, -1).getActionBar());
    }

}
