package webserver.server;

import controller.Controller;
import controller.ForwardController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;

public class DispatcherServlet {

    private final RequestMapper requestMapper = new RequestMapper();

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    protected void service(HttpRequest req, HttpResponse resp) throws IOException {
        logger.info("DispatcherServlet service");
        Controller controller = requestMapper.getController(req.getUrl());
        String toUrl;
        if (controller == null) {
            controller = new ForwardController();
        }
        toUrl = controller.execute(req, resp);

        if (toUrl.contains("redirect")) {
            String redirectUrl = toUrl.split(":")[1];
            resp.sendRedirect(redirectUrl);
            return;
        }

        resp.forward(toUrl);
    }
}
