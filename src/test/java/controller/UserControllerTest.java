package controller;

import db.Database;
import model.User;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UserControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
    UserController userController = new UserController();

    @Test
    @DisplayName("멀티 쓰레드 환경에서도 데이터 혼용이 없어야 한다.")
    void multi() throws InterruptedException {
        Database.clear();
        // given
        final int THREADS = 100;
        ExecutorService service = Executors.newFixedThreadPool(THREADS);
        CountDownLatch latch = new CountDownLatch(THREADS);

        // when
        long startTime = System.currentTimeMillis();
        for(int thread = 0; thread < THREADS; thread++) {
            int finalThread = thread;
            service.execute(() -> {
                try {
                    HttpRequest httpRequest = new HttpRequest("GET /user/create?userId="
                            + finalThread + "&password=sss&name=sss"
                            + finalThread + "&email=sss%40naver.com HTTP/1.1", null);
                    userController.signUp(httpRequest, new HttpResponse());
                } catch(Exception e) {
                    logger.error(e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        long endTime = System.currentTimeMillis();

        // then
        logger.info("걸린 시간 : " + (endTime - startTime));
        Collection<User> users = Database.findAll();
        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(THREADS).isEqualTo(users.size());
        softAssertions.assertThat(("sss10")).isEqualTo(Database.findUserById("10").getName());

        softAssertions.assertAll();
    }

    @Test
    @DisplayName("로그인이 성공적으로 이루어져야 한다.")
    void login() {
        HttpResponse httpResponse = new HttpResponse();
        HttpRequest httpRequest = new HttpRequest("GET /user/create?userId=1&password=sss&name=sss1&email=sss%40naver.com HTTP/1.1", null);
        userController.signUp(httpRequest, httpResponse);
        userController.login(httpRequest, httpResponse);
        UserService userService = UserService.of();
        assertTrue(userService.canLogin("1", "sss"));

    }

}