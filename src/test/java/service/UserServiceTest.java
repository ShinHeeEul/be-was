package service;

import db.Database;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import session.SessionStorage;

import java.util.Collection;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        // 테스트에 사용할 UserService 객체 생성
        Database.clear();
        userService = UserService.of();
    }

    @Test
    @DisplayName("유저가 데이터베이스에 저장이 되고 조회되어야 한다.")
    void testSaveUser() {
        // 테스트에 사용할 User 객체 생성
        User user = new User("testUser", "password123","nametest", "email@1");

        // UserService의 save 메서드 호출
        userService.save(user);

        // Database에 사용자가 잘 저장되었는지 확인
        User storedUser = Database.findUserById(user.getUserId());
        Assertions.assertNotNull(storedUser);
        Assertions.assertEquals(user.getUserId(), storedUser.getUserId());
        Assertions.assertEquals(user.getPassword(), storedUser.getPassword());
    }

    @Test
    @DisplayName("로그인할 수 있으면 true를 없으면 false를 반환해야 한다.")
    void testCanLogin() {
        // 테스트에 사용할 User 객체 생성
        User user = new User("testUser", "password123","name1", "email@1");
        Database.addUser(user);

        // UserService의 canLogin 메서드를 테스트
        boolean result1 = userService.canLogin(user.getUserId(), user.getPassword());
        boolean result2 = userService.canLogin(user.getUserId(), "wrongPassword");

        Assertions.assertTrue(result1);
        Assertions.assertFalse(result2);
    }

    @Test
    @DisplayName("유저를 추가하고 가져올 수 있어야 한다.")
    void testGetUser() {
        // 테스트에 사용할 User 객체 생성
        User user = new User("testUser", "password123","name1", "email@1");
        Database.addUser(user);

        // SessionStorage에 임시 세션 저장
        String sessionId = SessionStorage.setSession(user.getUserId());

        // UserService의 getUser 메서드를 테스트
        User retrievedUser = userService.getUser(sessionId);
        Assertions.assertNotNull(retrievedUser);
        Assertions.assertEquals(user.getUserId(), retrievedUser.getUserId());
    }

    @Test
    @DisplayName("유효하지 않은 세션아이디로 접근을 허용해서 안된다.")
    void testGetUserWithInvalidSession() {
        // 유효하지 않은 세션 ID로 UserService의 getUser 메서드를 테스트
        User retrievedUser = userService.getUser("invalidSessionId");
        Assertions.assertNull(retrievedUser);
    }

    @Test
    @DisplayName("여러 유저를 추가하고 전부 가져올 수 있어야 한다.")
    void testFindAll() {
        // 테스트에 사용할 User 객체들 생성
        User user1 = new User("user1", "pw1", "name1", "email@1");
        User user2 = new User("user2", "pw2","name2", "email@2");
        User user3 = new User("user3", "pw3","name3", "email@3");
        Database.addUser(user1);
        Database.addUser(user2);
        Database.addUser(user3);

        // UserService의 findAll 메서드를 테스트
        Collection<User> userList = userService.findAll();
        Assertions.assertEquals(3, userList.size());
    }
}
