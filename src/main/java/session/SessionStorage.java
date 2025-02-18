package session;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStorage {
    private static final ConcurrentHashMap<UUID, SessionValue> sessionStorage = new ConcurrentHashMap<>();

    public static String setSession(String userId) {
        UUID sessionId = UUID.randomUUID();
        SessionValue sessionValue = new SessionValue(userId);
        sessionStorage.put(sessionId, sessionValue);
        return sessionId.toString();
    }

    public static SessionValue getSessionValue(String sessionId) {
        SessionValue sessionValue;
        try{
            sessionValue = sessionStorage.get(UUID.fromString(sessionId));
        } catch(IllegalArgumentException e) {
            return null;
        }

        return sessionValue;
    }
}
