package webserver.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;


class HttpContentTypeTest {


    @Test
    @DisplayName("맵이 잘 되는지?")
    void do_right() throws NoSuchFieldException, IllegalAccessException {
        HttpContentType httpContentType = new HttpContentType();
        Class<HttpContentType> clazz = HttpContentType.class;
        Field field = clazz.getDeclaredField("extension");
        field.setAccessible(true);
        Map<String, String> map = (Map<String, String>) field.get(httpContentType);
        for(String key : map.keySet()) {
            Assertions.assertEquals(map.get(key), httpContentType.getContentType(key));
        }
    }
}