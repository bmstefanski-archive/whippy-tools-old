package pl.bmstefanski.tools.basic.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.basic.UserImpl;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class UserManager {

    private static final Map<UUID, User> players = new HashMap<>();
    private static final Cache<UUID, User> playersCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();

    public static User getUser(UUID uuid) {
        User user = playersCache.getIfPresent(uuid);

        if (user == null) {
            user = players.get(uuid);

            if (user != null) {
                players.put(uuid, user);
                return user;
            }

            return new UserImpl(uuid);
        }

        return user;
    }

    public static void addUser(User user) {
        players.put(user.getUUID(), user);
        playersCache.put(user.getUUID(), user);
    }

    public static void removeUser(User user) {
        players.remove(user.getUUID());
        playersCache.invalidate(user.getUUID());
    }

    public Collection<User> getPlayers() {
        return players.values();
    }

    public Collection<User> getPlayersCache() {
        return playersCache.asMap().values();
    }

    public Collection<User> getOnlinePlayers() {
        Collection<User> userCollection = new HashSet<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            userCollection.add(getUser(player.getUniqueId()));
        }

        return userCollection;
    }
}
