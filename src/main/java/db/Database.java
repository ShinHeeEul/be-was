package db;

import com.google.common.collect.Maps;

import model.User;

import java.util.Collection;
import java.util.concurrent.ConcurrentMap;

public class Database {
    private final static ConcurrentMap<String, User> users = Maps.newConcurrentMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    public static void clear() {
        users.clear();
    }
}
