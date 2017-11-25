package pl.bmstefanski.tools.util;

import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ReflectionUtils {

    public static Class<?> getCraftBukkitClass(String ClassName) {
        try {
            String name = Bukkit.getServer().getClass().getPackage().getName();
            String version = name.substring(name.lastIndexOf('.') + 1) + ".";
            String className = "org.bukkit.craftbukkit." + version + ClassName;

            return Class.forName(className);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Class<?> getMinecraftClass(String ClassName) {
        try {
            String name = Bukkit.getServer().getClass().getPackage().getName();
            String version = name.substring(name.lastIndexOf('.') + 1) + ".";
            String className = "net.minecraft.server." + version + ClassName;

            return Class.forName(className);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static EntityPlayer getPlayerEntity(Player player) {
        return ((CraftPlayer)player).getHandle();
    }

    private static PlayerConnection getPlayerConnection(Player player) {
        return getPlayerEntity(player).playerConnection;
    }

    public static void sendPacket(Player p, Object packet) {
        try {
            getPlayerConnection(p).sendPacket((Packet<?>) packet);
        } catch (SecurityException | IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }
}
