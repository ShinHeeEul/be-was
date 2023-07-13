package controller;

import db.Database;
import model.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class SignupControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(SignupControllerTest.class);

    SignupController signupController = new SignupController();

    @Test
    @DisplayName("멀티 쓰레드 시 잘 작동하는 지??")
    void multi() throws InterruptedException {
        // given
        final int THREADS = 100;
        signupController = new SignupController();
        ExecutorService service = Executors.newFixedThreadPool(THREADS);
        CountDownLatch latch = new CountDownLatch(THREADS);
        // when

        long startTime = System.currentTimeMillis();
        for(int thread = 0; thread < THREADS; thread++) {
            int finalThread = thread;
            service.execute(() -> {
                try {
                    HttpRequest httpRequest = new HttpRequest("GET /user/create?userId=" + finalThread + "&password=sss&name=sss" + finalThread + "&email=sss%40naver.com HTTP/1.1");
                    signupController.execute(httpRequest, null);
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
        Assertions.assertEquals(THREADS, users.size());
        Assertions.assertEquals("sss10", Database.findUserById("10").getName());
    }

}