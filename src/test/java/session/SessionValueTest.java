package session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class SessionValueTest {

    private SessionValue sessionValue;
    private final String USERID = "id";
    private final Instant EXPIRED_TIME = Instant.now().minus(Duration.ofHours(2));
    @BeforeEach
    public void setting() {
        sessionValue = new SessionValue(USERID);
    }

    @Test
    @DisplayName("userId가 반환되어야 한다.")
    public void getUserId() {
        assertEquals(sessionValue.getUserId(), USERID);
    }

    @Test
    @DisplayName("만료 시간이 초과될 시 false를 반환한다.")
    public void outOfExpired() throws NoSuchFieldException, IllegalAccessException {
        //given

        //when
        Class<SessionValue> clazz = SessionValue.class;
        Field field = clazz.getDeclaredField("expired");
        field.setAccessible(true);
        field.set(sessionValue, EXPIRED_TIME);

        //then
        assertFalse(sessionValue.isExpired());
    }

    @Test
    @DisplayName("만료 시간 내에 있으면 true를 반환해야 한다.")
    public void InExpired() {
        assertTrue(sessionValue.isExpired());
    }

}