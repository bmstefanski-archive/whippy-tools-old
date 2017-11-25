package pl.bmstefanski.tools.util.packet;

import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PacketPlayOutTitle {

    private final String text;
    private final int fadeIn;
    private final int time;
    private final int fadeOut;

    private final Class<?> titleClazz;
    private final Class<?> chatClazz;
    private Object chatObject;
    private Constructor<?> packetConstructor;

    public PacketPlayOutTitle(String text, int fadeIn, int time, int fadeOut) {
        this.text = text;
        this.fadeIn = fadeIn;
        this.time = time;
        this.fadeOut = fadeOut;

        this.titleClazz = ReflectionUtils.getMinecraftClass("PacketPlayOutTitle").getDeclaredClasses()[0];
        this.chatClazz = ReflectionUtils.getMinecraftClass("IChatBaseComponent").getDeclaredClasses()[0];

        try {
            this.chatObject = chatClazz.getMethod("a", String.class).invoke(null, "{\"text\":\"" + MessageUtils.fixColor(text) + "\"}");
            this.packetConstructor = ReflectionUtils.getMinecraftClass("PacketPlayOutTitle")
                    .getConstructor(ReflectionUtils.getMinecraftClass("PacketPlayOutTitle")
                    .getDeclaredClasses()[0], ReflectionUtils.getMinecraftClass("IChatBaseComponent"), int.class, int.class, int.class);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    public Object getTitle() {
        try {
            Object enumTitle = titleClazz.getField("TITLE").get(null);

            return packetConstructor.newInstance(enumTitle, chatObject, fadeIn, time, fadeOut);
        } catch (NoSuchFieldException | InvocationTargetException | IllegalAccessException | InstantiationException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public Object getSubTitle() {
        try {
            Object enumSubTitle = titleClazz.getField("SUBTITLE").get(null);

            return packetConstructor.newInstance(enumSubTitle, chatObject, fadeIn, time, fadeOut);
        } catch (NoSuchFieldException | InvocationTargetException | IllegalAccessException | InstantiationException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public Object getActionBar() {
        try {
            Object enumSubTitle = titleClazz.getField("ACTIONBAR").get(null);

            return packetConstructor.newInstance(enumSubTitle, chatObject, fadeIn, time, fadeOut);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
