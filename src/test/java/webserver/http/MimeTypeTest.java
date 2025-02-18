package webserver.http;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.response.header.MimeType;

import java.lang.reflect.Field;
import java.util.Map;


class MimeTypeTest {


    @Test
    @DisplayName("맵에 mime타입이 전부 저장되어야 한다.")
    void do_right() throws NoSuchFieldException, IllegalAccessException {
        MimeType mimeType = MimeType.createHttpContentType();
        Class<MimeType> clazz = MimeType.class;
        Field field = clazz.getDeclaredField("extension");
        field.setAccessible(true);
        Map<String, String> map = (Map<String, String>) field.get(mimeType);
        SoftAssertions softAssertions = new SoftAssertions();
        for(String key : map.keySet()) {
            softAssertions.assertThat(map.get(key)).isEqualTo(mimeType.getContentType(key));
        }
        softAssertions.assertAll();
    }
}