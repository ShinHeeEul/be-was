package webserver.http;

import controller.SignupController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

class HttpRequestTest {

    private final String METHOD = "GET";
    private final String URL = "/user/create";
    private final String VERSION = "HTTP/1.1";
    private final String USERID_VAL = "42";
    private final String PASSWORD_VAL = "sss";
    private final String NAME_VAL = "sss";
    private final String EMAIL_VAL = "sss@naver.com";
    HttpRequest httpRequest;
    @BeforeEach
    void setup() {
        httpRequest = new HttpRequest(METHOD + " " +  URL +
                "?" + SignupController.USERID_KEY + "=" + USERID_VAL + "&" +
                SignupController.PASSWORD_KEY + "=" + PASSWORD_VAL + "&" +
                SignupController.NAME_KEY + "=" + NAME_VAL + "&" +
                SignupController.EMAIL_KEY + "=" + EMAIL_VAL + " " + VERSION);

    }

    @Test
    @DisplayName("url이 파싱이 제대로 되는지?")
    void url_parsing() {
        //given

        //when

        //then
        Assertions.assertEquals(METHOD, httpRequest.getMethod());
        Assertions.assertEquals(URL, httpRequest.getUrl());
        Assertions.assertEquals(VERSION, httpRequest.getVersion());
    }


    @Test
    @DisplayName("쿼리 파싱이 제대로 되는지?")
    void query_parsing() {
        //given
        Map<String, String> queries = httpRequest.getQueries();

        //when

        //then
        Assertions.assertEquals(USERID_VAL, queries.get(SignupController.USERID_KEY));
        Assertions.assertEquals(PASSWORD_VAL, queries.get(SignupController.PASSWORD_KEY));
        Assertions.assertEquals(NAME_VAL, queries.get(SignupController.NAME_KEY));
        Assertions.assertEquals(EMAIL_VAL, queries.get(SignupController.EMAIL_KEY));

    }

    @Test
    @DisplayName("비정상 값이 들어온다면 어떻게 되는지?")
    void not_correct() {
        //given

        //when

        //then
        Assertions.assertThrows(RuntimeException.class, () -> new HttpRequest("error"));

        Assertions.assertThrows(RuntimeException.class, () -> new HttpRequest("GET error"));
    }


}