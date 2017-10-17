package pl.bmstefanski.tools.manager;

import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.util.Utils;

import java.util.HashMap;

public class SpawnManager {

    private final FileConfiguration fileConfiguration;
    private static HashMap<Player, BukkitTask> countdown;
    private Player player;
    private int count;

    static {
        countdown = new HashMap<>();
    }

    public SpawnManager(Player player) {
        this.fileConfiguration = Tools.getInstance().getConfig();
        this.player = player;
        this.count = this.fileConfiguration.getInt("countdowns.spawn");
    }

    public void start() {
        if (countdown.containsKey(player)) {
            Utils.sendMessage(player, Messages.SPAWN_CURRENTLY_TELEPORTING);
            return;
        }

        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(Tools.getInstance(), () -> {
            sendBar(count);

            if (count == 0) {
                player.teleport(getSpawn());
                countdown.get(player).cancel();
                countdown.remove(player);

                Utils.sendMessage(player, Messages.SPAWN_SUCCESS);

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

        Utils.sendMessage(player, Messages.SPAWN_CANCELLED);
        sendBar(player, Utils.fixColor(Messages.SPAWN_CANCELLED));
    }

    private void sendBar(int count) {

        String bar = "";

        for (int i = 0; i < count; i++) {
            bar = Utils.fixColor(Messages.SPAWN_COUNTING.replace("%count%", i + ""));
        }

        sendBar(player, bar);
    }

    // TODO reflections in other class
    private void sendBar(Player player, String message) {
        IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(iChatBaseComponent, ChatMessageType.GAME_INFO);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutChat);
    }

    public Location getSpawn() {
        final int x = fileConfiguration.getInt("spawn.x");
        final int y = fileConfiguration.getInt("spawn.y");
        final int z = fileConfiguration.getInt("spawn.z");
        final World world = Bukkit.getWorld(fileConfiguration.getString("spawn.world"));

        return new Location(world, x, y, z);
    }
}
