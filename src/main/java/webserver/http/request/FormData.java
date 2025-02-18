package webserver.http.request;

import java.util.Map;

public class FormData {
    private final Map<String, String> bodies;
    private final Map<String, String> queries;

    public FormData(Map<String, String> parseQuery, Map<String, String> parseBody) {
        this.queries = parseQuery;
        this.bodies = parseBody;
    }

    public Map<String, String> getQueries() {
        return queries;
    }

    public Map<String, String> getBodies() {
        return bodies;
    }
}
