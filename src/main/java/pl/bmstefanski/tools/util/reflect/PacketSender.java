package pl.bmstefanski.tools.util.reflect;

/* https://github.com/FunnyGuilds/FunnyGuilds/blob/master/src/main/java/net/dzikoysk/funnyguilds/util/reflect/PacketSender.java */

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;


public class PacketSender {

    private static Method getHandle;
    private static Field playerConnection;
    private static Method sendPacket;

    static {
        getHandle = Reflections.getMethod(Reflections.getBukkitClass("entity.CraftPlayer"), "getHandle");
        sendPacket = Reflections.getMethod(Reflections.getCraftClass("PlayerConnection"), "sendPacket");
        playerConnection = Reflections.getField(Reflections.getCraftClass("EntityPlayer"), "playerConnection");
    }

    public static void sendPacket(Collection<? extends Player> players, List<Object> packets) {
        Bukkit.getOnlinePlayers().forEach(player -> sendPacket(player, packets));
    }

    public static void sendPacket(Player target, List<Object> packets) {
        if (target == null) {
            return;
        }

        try {
            Object handle = getHandle.invoke(target);
            Object connection = playerConnection.get(handle);

            for (Object packet : packets) {
                sendPacket.invoke(connection, packet);
            }
        } catch (IllegalAccessException | InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }
}
