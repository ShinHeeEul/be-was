package webserver.http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.util.Parser;

import java.util.Map;

import static exception.ExceptionMessage.URL_NOT_CORRECT;
import static webserver.http.response.ResponseMessageHeader.BLANK;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final HttpMethod method;
    private final String url;
    private final String version;
    private final String cookie;
    private final FormData formData;

    public HttpRequest(String request, String requestBody) {
        String[] req = request.split(BLANK);
        ValidRequest(req);
        method = HttpMethod.valueOf(req[0]);
        this.url = Parser.parseUrl(req[1]);
        this.formData = new FormData(Parser.parseQuery(req[1]), Parser.parseBody(requestBody));
        this.cookie = null;
        this.version = req[2];
        logger.info("HttpRequest Create end url = " + url);
    }

    public HttpRequest(String request, String requestBody, String cookie) {
        String[] req = request.split(BLANK);
        ValidRequest(req);
        method = HttpMethod.valueOf(req[0]);
        this.url = Parser.parseUrl(req[1]);
        this.formData = new FormData(Parser.parseQuery(req[1]), Parser.parseBody(requestBody));
        this.cookie = cookie;
        this.version = req[2];
        logger.info("HttpRequest Create end url = " + url);
    }


    private void ValidRequest(String[] req) {
        if (req.length != 3) {
            throw new RuntimeException(URL_NOT_CORRECT);
        }
    }
    public HttpMethod getMethod() {
        return method;
    }
    public String getUrl() {
        return url;
    }
    public String getVersion() {
        return version;
    }
    public Map<String, String> getQueries() {
        return formData == null ? null : formData.getQueries();
    }

    public Map<String, String> getBodies() {
        return formData == null ? null : formData.getBodies();
    }

}
