package pl.bmstefanski.tools.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.basic.util.UserUtils;
import pl.bmstefanski.tools.runnable.LoadDataTask;

public class PlayerPreLogin implements Listener {

    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        User user = User.get(event.getUniqueId());

        new LoadDataTask(user).runTaskAsynchronously(Tools.getInstance());

        System.out.println(UserUtils.nameUserMap + " | " +  UserUtils.uuidUserMap);
    }
}
