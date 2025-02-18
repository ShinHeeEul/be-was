package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.response.view.View;
import webserver.http.response.header.MimeType;
import webserver.util.Parser;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseWriter {


    private final DataOutputStream dos;
    private final HttpResponse httpResponse;

    private static final Logger logger = LoggerFactory.getLogger(ResponseWriter.class);

    public ResponseWriter(DataOutputStream dos, HttpResponse httpResponse) {
        this.dos = dos;
        this.httpResponse = httpResponse;
    }

    public void sendRedirect(String redirectUrl) {
        ResponseMessageHeader responseMessageHeader = httpResponse.getHeader();
        try {
            writeHeader(responseMessageHeader.response302Header(redirectUrl, httpResponse.getCookie()));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        logger.info("HttpResponse redirect end");
    }

    public void forward(String url) {
        MimeType mimeType = MimeType.createHttpContentType();
        String extension = Parser.getUrlExtension(url);
        String contentType = mimeType.getContentType(extension);
        ResponseMessageHeader responseMessageHeader = httpResponse.getHeader();
        View view = httpResponse.getBody();
        try {
            if (view.readBody() != null) {
                write200Response(contentType, responseMessageHeader, view);
                dos.flush();
                return;
            }
            writeHeader(responseMessageHeader.response404Header());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        logger.info("HttpResponse forward end");
    }

    public void sendBadRequest() {
        try {
            writeHeader(new ResponseMessageHeader().response404Header());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        logger.info("HttpResponse BadRequest end");
    }

    private void write200Response(String contentType, ResponseMessageHeader responseMessageHeader, View view) throws IOException {
        logger.debug("ContentType : " + contentType + ", ResponseMessageHeader : " + view.getLength());
        writeHeader(responseMessageHeader.response200Header(contentType, httpResponse.getCookie()));
        writeBody(view);
    }

    private void writeHeader(String responseMessageHeader) throws IOException {
        dos.writeBytes(responseMessageHeader);
    }


    private void writeBody(View view) throws IOException {
        dos.write(view.readBody(), 0, view.getLength());
    }


}
