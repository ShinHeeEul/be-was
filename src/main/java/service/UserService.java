package service;

import db.Database;
import model.User;
import session.SessionStorage;
import session.SessionValue;

import java.util.Collection;

public class UserService {

    private static final UserService userService = new UserService();

    private UserService() {}

    public static UserService of() {
        return userService;
    }

    public void save(User user) {
        Database.addUser(user);
    }

    public boolean canLogin(String userId, String userPw) {
        User user = Database.findUserById(userId);
        if(user == null) {
            return false;
        }
        return user.getPassword().equals(userPw);
    }

    private boolean isLogin(String sessionId) {
        SessionValue sessionValue = SessionStorage.getSessionValue(sessionId);
        return sessionValue != null;
    }

    public User getUser(String sessionId) {
        if (!isLogin(sessionId)) {
                return null;
        }
        SessionValue sessionValue = SessionStorage.getSessionValue(sessionId);
       if(sessionValue == null) {
           return null;
       }
        return Database.findUserById(sessionValue.getUserId());
    }

    public Collection<User> findAll() {
        return Database.findAll();
    }
}
