package pl.bmstefanski.tools.basic.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import pl.bmstefanski.tools.basic.User;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class UserUtils {

    public final static HashMap<UUID, User> uuidUserMap = new HashMap<>();
    public final static HashMap<String, User> nameUserMap = new HashMap<>();

    private final static Cache<UUID, User> uuidUserCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();
    private final static Cache<String, User> nameUserCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();

    public static User get(String nickname) {
        User user = nameUserCache.getIfPresent(nickname);

        if (user == null) {
            user = nameUserMap.get(nickname);

            if (user != null) {
                nameUserCache.put(nickname, user);
                return user;
            }
            return null;
        }
        return user;
    }

    public static User get(UUID uuid) {
        User user = uuidUserCache.getIfPresent(uuid);

        if (user == null) {
            user = uuidUserMap.get(uuid);

            if (user != null) {
                uuidUserMap.put(uuid, user);
                return user;
            }
            return null;
        }
        return user;
    }

    public static void addUser(User user) {
        uuidUserMap.put(user.getUUID(), user);
        uuidUserCache.put(user.getUUID(), user);

        if (user.getName() != null) {
            nameUserMap.put(user.getName(), user);
            nameUserCache.put(user.getName(), user);
        }
    }

    public static void removeUser(User user) {
        uuidUserCache.invalidate(user.getUUID());
        nameUserCache.invalidate(user.getName());

        uuidUserMap.remove(user.getUUID());
        nameUserMap.remove(user.getName());
    }

    public static List<User> getUsers() {
        return new ArrayList<>(uuidUserMap.values());
    }

}
