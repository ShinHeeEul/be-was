package controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;


class ForwardControllerTest {

    ForwardController forwardController;

    @Test
    @DisplayName("Home Controller 가 잘 작동하는 지??")
    void home() {
        // given
        String url = "/sss";
        forwardController = new ForwardController();
        HttpRequest httpRequest = new HttpRequest("GET " + url + " HTTP/1.1");

        // when
        String s = forwardController.execute(httpRequest, null);

        // then
        Assertions.assertEquals(url, s);
    }
}