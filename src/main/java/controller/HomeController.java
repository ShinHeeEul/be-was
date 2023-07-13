package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;


public class HomeController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Override
    public String execute(HttpRequest request, HttpResponse response) {
        logger.info("execute");
        return "/index.html";
    }
}
