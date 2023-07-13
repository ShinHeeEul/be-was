package webserver.server;

import controller.Controller;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RequestMapperTest {

    @Test
    @DisplayName("맵이 잘 되는지?")
    void do_right() throws NoSuchFieldException, IllegalAccessException {
        RequestMapper requestMapper = new RequestMapper();
        Class<RequestMapper> clazz = RequestMapper.class;
        Field field = clazz.getDeclaredField("map");
        field.setAccessible(true);
        Map<String, Controller> map = (Map<String, Controller>) field.get(requestMapper);
        for(String key : map.keySet()) {
            assertEquals(map.get(key), requestMapper.getController(key));
        }
    }

}