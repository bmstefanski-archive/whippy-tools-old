package pl.bmstefanski.tools.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.util.MessageUtils;

import java.util.HashMap;

public class TeleportManager {

    private static HashMap<Player, BukkitTask> countdown;
    private final Tools plugin = Tools.getInstance();
    private Player player;
    private int count;

    static {
        countdown = new HashMap<>();
    }

    public TeleportManager(Player player) {
        this.player = player;
        this.count = plugin.getConfig().getInt("countdown");
    }

    public void start(Location location) {
        if (countdown.containsKey(player)) {
            MessageUtils.sendMessage(player, Messages.TELEPORT_CURRENTLY_TELEPORTING);
            return;
        }

        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {

            String bar = "";
            for (int i = 0; i < count; i++) {
                bar = MessageUtils.fixColor(Messages.TELEPORT_COUNTING.replace("%count%", i + ""));
            }
/*
            new PlayOutChatPacket().sendPacket(player, bar, ChatMessageType.GAME_INFO);*/

            if (count == 0) {
                player.teleport(location);
                countdown.get(player).cancel();
                countdown.remove(player);

                MessageUtils.sendMessage(player, Messages.TELEPORT_SUCCESS);
                return;
            }
            count--;

        }, 0, 20);
        countdown.put(player, bukkitTask);
    }

    public void stop() {
        if (!countdown.containsKey(player)) {
            return;
        }

        countdown.get(player).cancel();
        countdown.remove(player);

        MessageUtils.sendMessage(player, Messages.TELEPORT_CANCELLED);
/*        new PlayOutChatPacket().sendPacket(player, MessageUtils.fixColor(Messages.TELEPORT_CANCELLED), ChatMessageType.GAME_INFO);*/
    }

}
