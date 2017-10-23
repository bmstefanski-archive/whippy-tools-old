package pl.bmstefanski.tools.manager;

import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.util.Utils;

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
            Utils.sendMessage(player, Messages.TELEPORT_CURRENTLY_TELEPORTING);
            return;
        }

        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {

            String bar = "";
            for (int i = 0; i < count; i++) {
                bar = Utils.fixColor(Messages.TELEPORT_COUNTING.replace("%count%", i + ""));
            }
            sendBar(player, bar);

            if (count == 0) {
                player.teleport(location);
                countdown.get(player).cancel();
                countdown.remove(player);

                Utils.sendMessage(player, Messages.TELEPORT_SUCCESS);
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

        Utils.sendMessage(player, Messages.TELEPORT_CANCELLED);
        sendBar(player, Utils.fixColor(Messages.TELEPORT_CANCELLED));
    }


    private void sendBar(Player player, String message) {
        IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(iChatBaseComponent, ChatMessageType.GAME_INFO);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutChat);
    }

}
