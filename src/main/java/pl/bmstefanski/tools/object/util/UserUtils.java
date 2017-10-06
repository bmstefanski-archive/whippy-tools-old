package pl.bmstefanski.tools.object.util;

import org.apache.commons.lang.Validate;
import pl.bmstefanski.tools.object.User;

import java.util.ArrayList;
import java.util.List;

public class UserUtils {

    private final static List<User> users = new ArrayList<>();

    public static void addUser(final User user) {
        Validate.notNull(user, "User cannot be null!");

        if (!users.contains(user)) {
            users.add(user);
        }
    }

    public static List<User> getUsers() {
        return new ArrayList<>(users);
    }

}
