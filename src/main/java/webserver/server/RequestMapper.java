package webserver.server;

import controller.Controller;
import controller.HomeController;
import controller.SignupController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class RequestMapper {

    private static final Logger logger = LoggerFactory.getLogger(RequestMapper.class);
    private final ConcurrentHashMap<String, Controller> map = new ConcurrentHashMap<>();

    public RequestMapper() {
        logger.info("RequestMapper Create");
        map.put("/", new HomeController());
        map.put("/index.html", new HomeController());
        map.put("/user/create", new SignupController());
    }

    public Controller getController(String url) {
        logger.info("GetController");
        return map.get(url);
    }
}
