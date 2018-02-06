package pl.bmstefanski.tools.util.reflect;

import org.bukkit.entity.Player;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.tools.util.reflect.transition.PacketPlayOutTitle;

import java.util.Collection;
import java.util.Collections;

public class TitleSender implements Messageable {

    public void send(PacketPlayOutTitle.EnumTitleAction action, Player player, String message) {
        resetTitle();

        String json = "{\"text\":\"" + fixColor(message) + "\"}";
        PacketSender.sendPacket(player, Collections.singletonList(new PacketPlayOutTitle(action, json).getPacket()));
    }

    public void send(PacketPlayOutTitle.EnumTitleAction action, Collection<? extends Player> players, String message) {
        resetTitle();

        String json = "{\"text\":\"" + fixColor(message) + "\"}";
        PacketSender.sendPacket(players, Collections.singletonList(new PacketPlayOutTitle(action, json).getPacket()));
    }

    public void clearTitle() {
        new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.CLEAR, null);
    }

    public void resetTitle() {
        new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.RESET, null);
    }
}
