package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.response.ResponseMessageHeader;

import static org.junit.jupiter.api.Assertions.*;

class ResponseMessageHeaderTest {


    @Test
    @DisplayName("202 forward 헤더가 형식에 맞게 생성되어야 한다.")
    void forward() {
        //given
        String expected = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html;charset=utf-8\r\n"
                + "\r\n";

        ResponseMessageHeader responseMessageHeader = new ResponseMessageHeader();
        //when

        String header = responseMessageHeader.response200Header("text/html", null);

        //then
        assertEquals(expected, header);

    }

    @Test
    @DisplayName("302 Redirect 헤더가 형식에 맞게 생성되어야 한다.")
    void redirect() {
        //given
        String url = "/";
        String expected = "HTTP/1.1 302 Found\r\n"
                + "Location: http://localhost:8080/\r\n"
                + "\r\n";

        //when

        ResponseMessageHeader responseMessageHeader = new ResponseMessageHeader();
        String header = responseMessageHeader.response302Header(url, null);

        //then
        assertEquals(expected, header);
    }

    @Test
    @DisplayName("404 not found 헤더가 형식에 맞게 생성되어야 한다.")
    void not_found() {
        //given
        String expected = "HTTP/1.1 404 Not Found\r\n";

        //when

        ResponseMessageHeader responseMessageHeader = new ResponseMessageHeader();
        String header = responseMessageHeader.response404Header();

        //then
        assertEquals(expected, header);


    }
}