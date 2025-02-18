package webserver.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.ResponseWriter;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

class DispatcherServletTest {

    DispatcherServlet dispatcherServlet = DispatcherServlet.of();

    @Test
    @DisplayName("index.html이 반환되어야 한다.")
    void index() {
        String url = "GET /index.html HTTP/1.1";
        sendRequest(url, "HTTP/1.1 200 OK");
    }

    @Test
    @DisplayName("forward 되는지?")
    void forward() {
        String url = "GET / HTTP/1.1";
        sendRequest(url, "HTTP/1.1 200 OK");
    }

    @Test
    @DisplayName("redirect 되는지?")
    void redirect() {
        String url = "GET redirect:/ HTTP/1.1";
        sendRequest(url, "HTTP/1.1 302 Found");
    }

    @Test
    @DisplayName("error 케이스")
    void error() {

        String url = "GET /ddd HTTP/1.1";
        sendRequest(url, "HTTP/1.1 404 Not Found");
    }

    private void sendRequest(String url, String expected) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            HttpResponse httpResponse = new HttpResponse();
            HttpRequest httpRequest = new HttpRequest(url, null);
            dispatcherServlet.service(httpRequest, httpResponse);
            ResponseWriter responseWriter = new ResponseWriter(dataOutputStream, httpResponse);

            RequestHandler requestHandler = new RequestHandler(new Socket());
            Class<RequestHandler> clazz = RequestHandler.class;

            Method method = clazz.getDeclaredMethod("sendResponse", String.class, ResponseWriter.class);
            method.invoke(requestHandler, url, responseWriter);

            Assertions.assertEquals(expected, outputStream.toString());
        } catch(NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {}


    }

}