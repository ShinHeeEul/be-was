package webserver.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.*;

class DispatcherServletTest {

    DispatcherServlet dispatcherServlet = new DispatcherServlet();

    @Test
    @DisplayName("forward 되는지?")
    void forward() throws IOException {
        String url = "GET / HTTP/1.1";
        sendRequest(url, "HTTP/1.1 200");
    }

    @Test
    @DisplayName("redirect 되는지?")
    void redirect() throws IOException {
        String url = "GET redirect:/ HTTP/1.1";
        sendRequest(url, "HTTP/1.1 302");
    }

    @Test
    @DisplayName("error 케이스")
    void error() throws IOException {

        String url = "GET /ddd HTTP/1.1";
        sendRequest(url, "HTTP/1.1 404");
    }

    private void sendRequest(String url,String expected) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        HttpResponse httpResponse = new HttpResponse(dataOutputStream);
        HttpRequest httpRequest = new HttpRequest(url);
        dispatcherServlet.service(httpRequest, httpResponse);
        String header = outputStream.toString().split("\r\n")[0];


        Assertions.assertEquals(expected, header);

    }

}