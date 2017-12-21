package pl.bmstefanski.tools.util.reflect.transition;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.reflect.Reflections;

import java.lang.reflect.InvocationTargetException;

public class PacketPlayOutTitle {

    private static final Class<?> packetClass = Reflections.getCraftClass("PacketPlayOutTitle");
    private static final Class<?>[] typesClass = new Class<?>[] {Enum.class, IChatBaseComponent.class, int.class, int.class, int.class};
    private static int type = 0;

    static {
        try {
            if (packetClass.getConstructor(typesClass) == null) {
                type = 1;
            }
        } catch (NoSuchMethodException ex) {
            type = 1;
        }
    }

    public static Object getPacket(net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction enumTitleAction, String string, int fadeIn, int time, int fadeOut) {

        IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + MessageUtils.fixColor(string) + "\"}");

        try {
            if (type == 0) {
                return packetClass.getConstructor(typesClass).newInstance(enumTitleAction, iChatBaseComponent, fadeIn, time, fadeOut);
            } else if (type == 1) {
                Class<?> clazz = Reflections.getCraftClass("PacketPlayOutTitle");
                Object packet = packetClass.getConstructor().newInstance();

                Reflections.getPrivateField(clazz, "a").set(packet, enumTitleAction);
                Reflections.getPrivateField(clazz, "b").set(packet, iChatBaseComponent);
                Reflections.getPrivateField(clazz, "c").set(packet, fadeIn);
                Reflections.getPrivateField(clazz, "d").set(packet, time);
                Reflections.getPrivateField(clazz, "e").set(packet, fadeOut);

                return packet;
            }
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
