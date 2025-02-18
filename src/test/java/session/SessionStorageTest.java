package session;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionStorageTest {

    @Test
    @DisplayName("세션 아이디를 통해 유저 아이디를 가져와야 한다.")
    void testSetAndGetSession() {
        String userId = "user123";
        String sessionId = SessionStorage.setSession(userId);

        // SessionStorage에 저장된 SessionValue 객체를 가져와서 userId가 맞는지 확인
        SessionValue sessionValue = SessionStorage.getSessionValue(sessionId);
        Assertions.assertNotNull(sessionValue);
        Assertions.assertEquals(userId, sessionValue.getUserId());
    }

    @Test
    @DisplayName("존재하지 않는 세션 아이디로의 접근을 허용해서 안된다.")
    void testGetNonExistingSession() {
        // 존재하지 않는 sessionId를 사용하여 SessionStorage에서 가져오기 시도
        String sessionId = "nonExistingSessionId";
        SessionValue sessionValue = SessionStorage.getSessionValue(sessionId);
        Assertions.assertNull(sessionValue);
    }
}
