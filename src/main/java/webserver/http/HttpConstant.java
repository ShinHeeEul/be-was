package webserver.http;

public enum HttpConstant {

    LOCATION("Location"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    HTTP_VERSION("HTTP/1.1 "),
    CHARSET("charset"),
    UTF_8("utf-8"),
    RESPONSE_200("200"),
    RESPONSE_302("302"),
    RESPONSE_404("404"),
    NEW_LINE("\r\n");


    private final String literal;

    HttpConstant(String literal) {
        this.literal = literal;
    }

    public String getConstant() {
        return literal;
    }
}
