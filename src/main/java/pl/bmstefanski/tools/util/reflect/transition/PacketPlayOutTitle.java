/*
 MIT License

 Copyright (c) 2018 Whippy Tools

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package pl.bmstefanski.tools.util.reflect.transition;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import org.bukkit.ChatColor;
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

        IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', string) + "\"}");

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
