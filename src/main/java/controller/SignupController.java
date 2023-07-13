package controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Map;

public class SignupController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(SignupController.class);

    public static final String USERID_KEY = "userId";
    public static final String PASSWORD_KEY = "password";
    public static final String NAME_KEY = "name";
    public static final String EMAIL_KEY = "email";

    @Override
    public String execute(HttpRequest request, HttpResponse response) {

        logger.info("execute");

        Map<String, String> queries = request.getQueries();
        User user = new User(queries.get(USERID_KEY),
                queries.get(PASSWORD_KEY),
                queries.get(NAME_KEY),
                queries.get(EMAIL_KEY));

        Database.addUser(user);
        logger.info("signup ok, userId : {}", queries.get("userId"));

        return "redirect:/";
    }
}
