package webserver.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import java.lang.reflect.InvocationTargetException;


public class DispatcherServlet {

    private final AnnotationProcessor annotationProcessor;

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private static final DispatcherServlet dispatcherServlet = new DispatcherServlet();

    private DispatcherServlet() {
        this.annotationProcessor = AnnotationProcessor.createAnnotationProcessor();
    }

    public static DispatcherServlet of() {
        return dispatcherServlet;
    }

    public void service(HttpRequest req, HttpResponse resp) {
        logger.info("DispatcherServlet service");
        ControllerConfig handler = annotationProcessor.getHandler(req.getUrl());
        resp.setToUrl(req.getUrl());
        if(handler != null) {
            executeController(req, resp, handler);
        }

        String toUrl = resp.getToUrl();
        logger.debug("toUrl = {}", toUrl);
        resp.setBody(toUrl);
    }

    private static void executeController(HttpRequest req, HttpResponse resp, ControllerConfig handler) {
            try {
                handler.getMethod().invoke(handler.getObject(),req, resp);
            } catch (IllegalAccessException | InvocationTargetException e) {
                if (e instanceof java.lang.reflect.InvocationTargetException) {
                Throwable targetException = ((java.lang.reflect.InvocationTargetException) e).getTargetException();
                logger.error(e.getClass() + " : " + targetException);
            }
                logger.error(e.getClass() + " : " + e.getMessage());
        }
    }


}
