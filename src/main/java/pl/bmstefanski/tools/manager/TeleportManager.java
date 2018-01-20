package pl.bmstefanski.tools.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.runnable.TeleportRequestTask;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.MessageUtils;

import java.util.HashMap;
import java.util.Map;

public class TeleportManager {

    private final Tools plugin;
    private final Messages messages;
    public static final Map<Player, BukkitTask> taskMap = new HashMap<>();

    public TeleportManager(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    public void teleport(Player player, Location location, int delay) {

        if (taskMap.containsKey(player)) {
            MessageUtils.sendMessage(player, messages.getCurrentlyTeleporting());
            return;
        }

        MessageUtils.sendMessage(player, messages.getTeleport());

        Runnable runnable = new TeleportRequestTask(plugin, player, location, delay);
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, runnable, 0, 20);
        taskMap.put(player, task);
    }
}
