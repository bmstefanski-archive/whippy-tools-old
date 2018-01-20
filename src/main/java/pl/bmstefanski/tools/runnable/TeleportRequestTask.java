package pl.bmstefanski.tools.runnable;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.manager.TeleportManager;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.MessageUtils;

import java.util.Map;

public class TeleportRequestTask implements Runnable {

    private final Tools plugin;
    private final Player player;
    private final Location location;
    private final Messages messages;
    private final Location startLocation;
    private int delay;

    public TeleportRequestTask(Tools plugin, Player player, Location location, int delay) {
        this.plugin = plugin;
        this.player = player;
        this.location = location;
        this.messages = plugin.getMessages();
        this.startLocation = player.getLocation();
        this.delay = delay;
    }

    @Override
    public void run() {
        Map<Player, BukkitTask> taskMap = TeleportManager.taskMap;

        if (player.getLocation().distance(startLocation) > 0.5) {
            taskMap.get(player).cancel();
            taskMap.remove(player);

            MessageUtils.sendMessage(player, messages.getTeleportCancelled());
            return;
        }

        if (delay == 0) {
            player.teleport(location);
            taskMap.get(player).cancel();
            taskMap.remove(player);

            MessageUtils.sendMessage(player, messages.getTeleportSuccess());
            return;
        }

        delay--;
    }
}
