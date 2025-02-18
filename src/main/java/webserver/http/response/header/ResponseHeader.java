package webserver.http.response.header;


public enum ResponseHeader {

    LOCATION("Location"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    SET_COOKIE("Set-Cookie");


    private final String literal;

    ResponseHeader(String literal) {
        this.literal = literal;
    }

    public String getConstant() {
        return literal;
    }

}
