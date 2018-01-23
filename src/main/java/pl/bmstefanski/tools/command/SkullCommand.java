package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.MessageUtils;

public class SkullCommand implements MessageUtils{

    private final Tools plugin;
    private final Messages messages;

    public SkullCommand(Tools plugin){
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @CommandInfo(
            name = "skull",
            description = "skull command",
            permission = "skull",
            userOnly = true,
            usage = "[player]"
    )
    public void skull(CommandSender sender, CommandContext context){

        Player player = (Player) sender;
        ItemStack skullItem = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skullItem.getItemMeta();

        if(context.getArgs().length == 0){
            skullMeta.setOwningPlayer(player);
            skullItem.setItemMeta(skullMeta);
            player.getInventory().addItem(skullItem);
            sendMessage(player, messages.getSkullOnly());
            return;
        }

        OfflinePlayer skullOwner = Bukkit.getOfflinePlayer(context.getParam(0));
        skullMeta.setOwningPlayer(skullOwner);
        skullItem.setItemMeta(skullMeta);
        player.getInventory().addItem(skullItem);
        sendMessage(player, StringUtils.replace(messages.getSkullSomeone(), "%player%", context.getParam(0)));
    }
}
