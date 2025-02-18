package webserver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ViewResolver {

    private static final Logger logger = LoggerFactory.getLogger(ViewResolver.class);

    public static byte[] getContent(String url, String extension) {
        byte[] body;
        String resourceUrl = getResourceUrl(url,extension);

        try {
            File file = new File(resourceUrl);
            if(file.isFile()) {
                body = Files.readAllBytes(file.toPath());
                return body;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    private static String getResourceUrl(String url, String urlExtension) {
        final String TEMPLATE_URL = "./src/main/resources/templates";
        final String STATIC_URL = "./src/main/resources/static";
        final String NOT_FOUND = "";

        if(!url.contains(".")) {
            return NOT_FOUND;
        }
        if(urlExtension.equals(".html")) {
            return TEMPLATE_URL + url;
        }
        return STATIC_URL + url;
    }



}
