package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

import static exception.ExceptionMessage.URL_NOT_CORRECT;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final String method;
    private String url;
    private final String version;
    private final ConcurrentHashMap<String, String> queries = new ConcurrentHashMap<>();

    public HttpRequest(String request) {
        logger.info("HttpRequest Create with request");
        String[] req = request.split(" ");
        ValidRequest(req);
        this.method = req[0];
        this.url = null;
        parseQuery(req[1]);
        this.version = req[2];
        logger.info("HttpRequest Create end url = " + url);
    }

    private void ValidRequest(String[] req) {
        if (req.length != 3) {
            throw new RuntimeException(URL_NOT_CORRECT);
        }
    }


    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }

    private void parseQuery(String url) {
        logger.info("parseQuery : " + url);
        String[] urlQuery = url.split("\\?");
        if (urlQuery.length == 1) {
            this.url = url;
            return;
        }
        this.url = urlQuery[0];
        String[] queries = urlQuery[1].split("&");
        for (String query : queries) {
            query = URLDecoder.decode(query, StandardCharsets.UTF_8);
            String[] parse = query.split("=");
            this.queries.put(parse[0], parse[1]);
        }
    }

    public ConcurrentHashMap<String, String> getQueries() {
        logger.info("getQueries");
        return queries;
    }
}
