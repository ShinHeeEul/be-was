package model;


public class User {
    private final String userId;
    private final String password;
    private final String name;
    private final String email;
    public static final String USERID_KEY = "userId";
    public static final String PASSWORD_KEY = "password";
    public static final String NAME_KEY = "name";
    public static final String EMAIL_KEY = "email";

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
