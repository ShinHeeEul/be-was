package controller;

import service.UserService;
import annotation.Controller;
import annotation.RequestMapping;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import session.SessionStorage;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.Map;

import static model.User.*;

@Controller
public class UserController {

    public static final String USERID_KEY = "userId";
    public static final String PASSWORD_KEY = "password";
    public static final String REDIRECT_LOGIN_FAILED = "redirect:/user/login_failed.html";
    public static final String REDIRECT_HOME = "redirect:/index.html";
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(path = "/user/create")
    public void signUp(HttpRequest request, HttpResponse response) {
        UserService userService = UserService.of();
        Map<String, String> queries = request.getQueries();
        if(request.getMethod() == HttpMethod.POST) {
            queries = request.getBodies();
        }
        User user = new User(queries.get(USERID_KEY),
                queries.get(PASSWORD_KEY),
                queries.get(NAME_KEY),
                queries.get(EMAIL_KEY));

        userService.save(user);
        response.setToUrl(REDIRECT_HOME);
        logger.info("signup ok, userId : {}", queries.get(USERID_KEY));
    }
    @RequestMapping(path = "/user/login")
    public void login(HttpRequest request, HttpResponse response) {
        UserService userService = UserService.of();
        response.setToUrl(REDIRECT_LOGIN_FAILED);

        Map<String, String> data = request.getQueries();
        if(request.getMethod() == HttpMethod.POST) {
            data = request.getBodies();
        }
        String userId = data.get(USERID_KEY);
        String userPw = data.get(PASSWORD_KEY);

        if(userService.canLogin(userId, userPw)) {
            response.setToUrl(REDIRECT_HOME);
            String sessionId = SessionStorage.setSession(userId);
            response.setCookie("sid=" + sessionId);
            logger.debug("login ok");
        }
        logger.debug("login failed");
    }


}
