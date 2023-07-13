package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.util.IOUtils;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {


    private final DataOutputStream dos;
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    public HttpResponse(DataOutputStream dos) {
        logger.info("HttpServletResponse Create with dos");
        this.dos = dos;
    }

    public void sendRedirect(String redirectUrl) {

        logger.info("HttpResponse redirect");
        try {
            dos.writeBytes(HttpHeader.response302Header(redirectUrl));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void forward(String url) {
        logger.info("HttpResponse forward");
        HttpContentType httpContentType = new HttpContentType();
        String extension = getUrlExtension(url);
        String contentType = httpContentType.getContentType(extension);
        byte[] body = IOUtils.getContent(url, extension);
        try {
            if (body != null) {
                dos.writeBytes(HttpHeader.response200Header(body.length, contentType));
                responseBody(body);
                return;
            }
            dos.writeBytes(HttpHeader.response404Header());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String getUrlExtension(String url) {
        return url.contains(".")?url.substring(url.lastIndexOf(".")):null;
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
