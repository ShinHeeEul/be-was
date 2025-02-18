package webserver.http.response.header;

import static webserver.http.response.ResponseMessageHeader.BLANK;

public enum StatusLine {

    RESPONSE_200("200","OK"),
    RESPONSE_302("302", "Found"),
    RESPONSE_404("404", "Not Found");

    private final String code;
    private final String message;
    private static final String HTTP_VERSION = "HTTP/1.1";

    StatusLine(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage(){
        return message;
    }

    public static String getStatusHeader(StatusLine statusLine) {
        return HTTP_VERSION + BLANK + statusLine.getCode() + BLANK +  statusLine.getMessage();
    }
}
