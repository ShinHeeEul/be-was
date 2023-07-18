package webserver.http.response;


public enum ResponseHeader {

    LOCATION("Location"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length");


    private final String literal;

    ResponseHeader(String literal) {
        this.literal = literal;
    }

    public String getConstant() {
        return literal;
    }

}
