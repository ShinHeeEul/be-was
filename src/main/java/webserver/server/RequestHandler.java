package webserver.server;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());


        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dataOutputStream = new DataOutputStream(out);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

            String requestFirstLine = bufferedReader.readLine();
            HttpRequest httpRequest = new HttpRequest(requestFirstLine);
            logger.info("First Header : " + requestFirstLine);
            while (bufferedReader.ready()) {
                String requestHeader = bufferedReader.readLine();
                logger.info("Remain Header : " + requestHeader);
            }
            DispatcherServlet dispatcherServlet = new DispatcherServlet();
            dispatcherServlet.service(httpRequest, new HttpResponse(dataOutputStream));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
