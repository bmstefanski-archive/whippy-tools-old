package pl.bmstefanski.tools.manager;

import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.reflect.PacketSender;
import pl.bmstefanski.tools.util.reflect.transition.PacketPlayOutTitle;

import java.util.*;

public class TeleportManager {

    private static final HashMap<Player, BukkitTask> COUNTDOWN;
    private final Player player;
    private final Tools plugin;
    private int count;

    static {
        COUNTDOWN = new HashMap<>();
    }

    public TeleportManager(Tools plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public void start(Location location) {

        Messages messages = plugin.getMessages();

        if (COUNTDOWN.containsKey(player)) {
            MessageUtils.sendMessage(player, messages.getCurrentlyTeleporting());
            return;
        }

        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {

            String bar = "";
            for (int i = 0; i < count; i++) {
                bar = MessageUtils.fixColor(StringUtils.replace(messages.getTeleportCounting(),"%count%", i + ""));
            }

            PacketSender.sendPacket(player, Collections.singletonList(PacketPlayOutTitle.getPacket(EnumTitleAction.ACTIONBAR, bar, -1, -1, -1)));

            if (count == 0) {
                player.teleport(location);
                COUNTDOWN.get(player).cancel();
                COUNTDOWN.remove(player);

                MessageUtils.sendMessage(player, messages.getTeleportSuccess());
                return;
            }
            count--;

        }, 0, 20);
        COUNTDOWN.put(player, bukkitTask);
    }

    public void stop() {

        Messages messages = plugin.getMessages();

        if (!COUNTDOWN.containsKey(player)) {
            return;
        }

        COUNTDOWN.get(player).cancel();
        COUNTDOWN.remove(player);

        MessageUtils.sendMessage(player, messages.getTeleportCancelled());
    }

}
