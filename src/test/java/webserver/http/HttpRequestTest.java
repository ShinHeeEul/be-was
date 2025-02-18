package webserver.http;

import model.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpMethod;
import java.util.HashMap;
import java.util.Map;

class HttpRequestTest {

    private final HttpMethod METHOD = HttpMethod.GET;
    private final String URL = "/user/create";
    private final String VERSION = "HTTP/1.1";
    private final String USERID_VAL = "42";
    private final String PASSWORD_VAL = "sss";
    private final String NAME_VAL = "sss";
    private final String EMAIL_VAL = "sss@naver.com";
    HttpRequest httpRequest;

    SoftAssertions softAssertions = new SoftAssertions();

    @BeforeEach
    void setup() {
        httpRequest = new HttpRequest(METHOD + " " + URL +
                "?" + User.USERID_KEY + "=" + USERID_VAL + "&" +
                User.PASSWORD_KEY + "=" + PASSWORD_VAL + "&" +
                User.NAME_KEY + "=" + NAME_VAL + "&" +
                User.EMAIL_KEY + "=" + EMAIL_VAL + " " + VERSION, null);

    }

    @Test
    @DisplayName("url이 파싱되어 와야한다")
    void url_parsing() {
        //given

        //when

        //then
        softAssertions.assertThat(METHOD).isEqualTo(httpRequest.getMethod());
        softAssertions.assertThat(URL).isEqualTo(httpRequest.getUrl());
        softAssertions.assertThat(VERSION).isEqualTo(httpRequest.getVersion());

        softAssertions.assertAll();
    }


    @Test
    @DisplayName("Get 방식의 쿼리 파싱이 되는지?")
    void query_parsing() {
        //given
        Map<String, String> queries = httpRequest.getQueries();

        //when

        //then
        softAssertions.assertThat(USERID_VAL).isEqualTo(queries.get(User.USERID_KEY));
        softAssertions.assertThat(PASSWORD_VAL).isEqualTo(queries.get(User.PASSWORD_KEY));
        softAssertions.assertThat(NAME_VAL).isEqualTo(queries.get(User.NAME_KEY));
        softAssertions.assertThat(EMAIL_VAL).isEqualTo(queries.get(User.EMAIL_KEY));

        softAssertions.assertAll();
    }

    @Test
    @DisplayName("비정상 Request Header가 들어온다면 어떻게 되는지?")
    void not_correct() {
        //given

        //when

        //then
        softAssertions.assertThatCode(() -> new HttpRequest("error", null)).isInstanceOf(RuntimeException.class);
        softAssertions.assertThatCode(() -> new HttpRequest("GET error", null)).isInstanceOf(RuntimeException.class);

        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Body Data가 Post방식으로 들어오면 어떻게 되는지?")
    void post_body() {
        //given
        httpRequest = new HttpRequest("POST /create/user HTTP/1.1", "test=1&test2=2");

        //when
        HashMap<String, String> testMap = new HashMap<>();
        testMap.put("test", "1");
        testMap.put("test2", "2");

        //then
        softAssertions = new SoftAssertions();
        softAssertions.assertThat(testMap).isEqualTo(httpRequest.getBodies());
        softAssertions.assertThat(httpRequest.getQueries()).isNull();

        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Body Data가 Get방식으로 들어오면 어떻게 되는지?")
    void get_body() {
        //given
        httpRequest = new HttpRequest("GET /create/user HTTP/1.1", "test=1&test2=2");

        //when

        //then
        Assertions.assertNull(httpRequest.getQueries());
    }


    @Test
    @DisplayName("Post 방식으로 query가 넘어오면 어떻게 되는지?")
    void post_query() {
        //given
        httpRequest = new HttpRequest("POST /create/user?test11=11&test22=22 HTTP/1.1", null);

        //when
        HashMap<String, String> testMap = new HashMap<>();
        testMap.put("test11","11");
        testMap.put("test22","22");

        //then
        softAssertions = new SoftAssertions();
        softAssertions.assertThat(testMap).isEqualTo(httpRequest.getQueries());
        softAssertions.assertThat(httpRequest.getBodies()).isNull();
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Post 방식으로 query랑 body data 둘 다 넘어오면 어떻게 되는지?")
    void post_query_body() {

        //given
        httpRequest = new HttpRequest("POST /create/user?test11=11&test2=22 HTTP/1.1", "test=1&test2=2");

        //when
        HashMap<String, String> testMap = new HashMap<>();
        testMap.put("test", "1");
        testMap.put("test2", "2");
        HashMap<String,String> testMap2 = new HashMap<>();
        testMap2.put("test11","11");
        testMap2.put("test2", "22");

        //then
        softAssertions = new SoftAssertions();
        softAssertions.assertThat(testMap).isEqualTo(httpRequest.getBodies());
        softAssertions.assertThat(testMap2).isEqualTo(httpRequest.getQueries());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Get 방식으로 query랑 body data 둘 다 넘어오면 어떻게 되는지?")
    void get_query_body() {
        //given
        httpRequest = new HttpRequest("GET /create/user?test11=11&test22=22 HTTP/1.1", "test=1&test2=2");

        //when
        HashMap<String, String> testMap = new HashMap<>();
        testMap.put("test11", "11");
        testMap.put("test22", "22");
        HashMap<String, String> testMap2 = new HashMap<>();
        testMap2.put("test", "1");
        testMap2.put("test2","2");

        //then
        softAssertions = new SoftAssertions();
        softAssertions.assertThat(testMap).isEqualTo(httpRequest.getQueries());
        softAssertions.assertThat(testMap2).isEqualTo(httpRequest.getBodies());

        softAssertions.assertAll();
    }

}