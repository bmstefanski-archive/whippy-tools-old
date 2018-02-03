package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import pl.bmstefanski.commands.Arguments;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.storage.configuration.Messages;

public class SkullCommand implements Messageable {

    private final Tools plugin;
    private final Messages messages;

    public SkullCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @Command(name = "skull", usage = "[player]", max = 1)
    @Permission("tools.command.skull")
    @GameOnly
    public void command(Arguments arguments) {
        
        Player player = (Player) arguments.getSender();
        ItemStack skullItem = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skullItem.getItemMeta();

        if (arguments.getArgs().length == 0) {
            skullMeta.setOwningPlayer(player);
            skullItem.setItemMeta(skullMeta);
            player.getInventory().addItem(skullItem);
            sendMessage(player, messages.getSkullOnly());
            return;
        }

        OfflinePlayer skullOwner = Bukkit.getOfflinePlayer(arguments.getArgs(0));
        skullMeta.setOwningPlayer(skullOwner);
        skullItem.setItemMeta(skullMeta);
        player.getInventory().addItem(skullItem);
        sendMessage(player, StringUtils.replace(messages.getSkullSomeone(), "%player%", arguments.getArgs(0)));
    }
}
