package pl.bmstefanski.tools.basic.util;

import org.apache.commons.lang.Validate;
import pl.bmstefanski.tools.basic.User;

import java.util.ArrayList;
import java.util.List;

public class UserUtils {

    private final static List<User> users = new ArrayList<>();

    public static void addUser(User user) {
        Validate.notNull(user, "User cannot be null!");

        if (!users.contains(user)) {
            users.add(user);
        }
    }

    public static List<User> getUsers() {
        return new ArrayList<>(users);
    }

}
