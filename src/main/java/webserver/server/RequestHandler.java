package webserver.server;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.ResponseWriter;
import webserver.util.Parser;

import static webserver.http.response.ResponseMessageHeader.BLANK;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection;
    public static final String REDIRECT = "redirect:";

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        DataOutputStream dataOutputStream = null;
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            dataOutputStream = new DataOutputStream(out);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            handleRequest(dataOutputStream, bufferedReader);

        } catch (Exception e) {
            logger.error(e.getMessage());
            ResponseWriter responseWriter = new ResponseWriter(dataOutputStream, new HttpResponse());
            responseWriter.sendBadRequest();
        }

    }

    private static void handleRequest(DataOutputStream dataOutputStream, BufferedReader bufferedReader) throws IOException {
        String requestFirstLine = bufferedReader.readLine();
        logger.info("First Header : " + requestFirstLine);

        String[] parseHeader = Parser.parseRequestHeader(bufferedReader);

        int contentLength = Integer.parseInt(parseHeader[0]);
        String cookie = parseHeader[1];

        DispatcherServlet dispatcherServlet = DispatcherServlet.of();

        String requestBody = readBody(bufferedReader, contentLength);
        HttpRequest httpRequest = new HttpRequest(requestFirstLine, requestBody, cookie);
        HttpResponse httpResponse = new HttpResponse(cookie);

        dispatcherServlet.service(httpRequest, httpResponse);

        sendResponse(httpResponse.getToUrl(), new ResponseWriter(dataOutputStream, httpResponse));
    }

    private static void sendResponse(String toUrl, ResponseWriter responseWriter) {
        if(toUrl.contains(REDIRECT)) {
            sendRedirect(toUrl, responseWriter);
            return;
        }
        responseWriter.forward(toUrl);
    }

    private static String readBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        String requestBody = null;
        if(contentLength != 0) {
            char[] socketBody = new char[contentLength];
            int readLength = bufferedReader.read(socketBody, 0, contentLength);
            if(readLength == contentLength) {
                requestBody = new String(socketBody);
            }
        }
        return requestBody;
    }

    private static void sendRedirect(String toUrl, ResponseWriter responseWriter) {
        String redirectUrl = toUrl.split(REDIRECT)[1];
        if(redirectUrl.contains(BLANK)) {
            redirectUrl = redirectUrl.trim();
        }
        responseWriter.sendRedirect(redirectUrl);
    }

}
