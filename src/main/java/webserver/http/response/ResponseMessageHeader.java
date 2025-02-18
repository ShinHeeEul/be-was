package webserver.http.response;

import webserver.server.WebServer;

import static webserver.http.response.header.ResponseHeader.*;
import static webserver.http.response.header.StatusLine.*;

public class ResponseMessageHeader {
    private static final String KEY_VALUE_PARSER = ":";
    private static final String SEMI_COLUMN = ";";
    private static final String EQUAL = "=";
    private static final String CHARSET = "charset";
    private static final String UTF_8 = "utf-8";
    private static final String NEW_LINE = "\r\n";
    private static final String PATH = "Path";

    public static final String BLANK = " ";

    public String response200Header(String contentType, String cookie) {
        String header = "";
        header += getStatusHeader(RESPONSE_200)+ NEW_LINE;
        header += getContentTypeHeader(contentType);
        if(cookie != null) {
            header += setCookie(cookie);
        }
        header += NEW_LINE;

        return header;

    }

    public String response302Header(String redirectUrl, String cookie) {
        String header = "";
        header += getStatusHeader(RESPONSE_302)+ NEW_LINE;
        header += LOCATION.getConstant() + KEY_VALUE_PARSER + BLANK + WebServer.HOME_URL + redirectUrl + NEW_LINE;
        if(cookie != null) {
            header += setCookie(cookie);
        }
        header += NEW_LINE;

        return header;
    }

    public String response404Header() {
        String header = "";
        header += getStatusHeader(RESPONSE_404) + NEW_LINE;
        return header;
    }

    private String setCookie(String cookie) {
        return SET_COOKIE.getConstant() + KEY_VALUE_PARSER + BLANK + cookie + SEMI_COLUMN + BLANK + PATH + EQUAL + "/" + NEW_LINE;
    }
    private String getContentTypeHeader(String contentType) {
        return CONTENT_TYPE.getConstant() + KEY_VALUE_PARSER + BLANK + contentType + SEMI_COLUMN + CHARSET + EQUAL + UTF_8 + NEW_LINE;
    }

}
