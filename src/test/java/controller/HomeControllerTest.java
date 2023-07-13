package controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;


class HomeControllerTest {
    HomeController homeController = new HomeController();

    @Test
    @DisplayName("Home Controller 가 잘 작동하는 지??")
    void home() {
        // given
        HttpRequest httpRequest = new HttpRequest("GET /index.html HTTP/1.1");

        // when
        String s = homeController.execute(httpRequest, null);

        // then
        Assertions.assertEquals("/index.html", s);
    }

}