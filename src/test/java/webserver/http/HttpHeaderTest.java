package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpHeaderTest {


    @Test
    @DisplayName("202 forward 헤더가 제대로 생성되는지?")
    void forward() {
        //given
        final int BODY_OF_LENGTH = 6902;
        String expected = "HTTP/1.1 200\r\n"
                + "Content-Type: text/html;charset=utf-8\r\n"
                + "Content-Length: " + BODY_OF_LENGTH + "\r\n"
                + "\r\n";

        //when
        String header = HttpHeader.response200Header(BODY_OF_LENGTH,MimeType.HTML.getMimeType());

        //then
        assertEquals(expected, header);

    }

    @Test
    @DisplayName("302 Redirect 헤더가 제대로 생성되는지?")
    void redirect() {
        //given
        String url = "/";
        String expected = "HTTP/1.1 302\r\n"
                + "Location: http://localhost:8080/\r\n"
                + "Content-Length: 0\r\n"
                + "\r\n";

        //when
        String header = HttpHeader.response302Header(url);

        //then
        assertEquals(expected, header);
    }

    @Test
    @DisplayName("404 not found 헤더가 제대로 생성되는지?")
    void not_found() {
        //given
        String expected = "HTTP/1.1 404\r\n";

        //when
        String header = HttpHeader.response404Header();

        //then
        assertEquals(expected, header);


    }
}