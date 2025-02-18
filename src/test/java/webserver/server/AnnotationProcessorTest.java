package webserver.server;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnnotationProcessorTest {

    @Test
    @DisplayName("맵이 잘 되는지?")
    void do_right() throws NoSuchFieldException, IllegalAccessException {
        AnnotationProcessor annotationProcessor = AnnotationProcessor.createAnnotationProcessor();
        Class<AnnotationProcessor> clazz = AnnotationProcessor.class;
        Field field = clazz.getDeclaredField("requestMapper");
        field.setAccessible(true);
        Map<String, ControllerConfig> map = (Map<String, ControllerConfig>) field.get(annotationProcessor);
        for(String key : map.keySet()) {
           assertEquals(map.get(key).getMethod(), annotationProcessor.getHandler(key).getMethod());
        }
    }

}