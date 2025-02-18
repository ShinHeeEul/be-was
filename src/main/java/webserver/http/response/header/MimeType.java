package webserver.http.response.header;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public class MimeType {
    private enum Type {

        HTML(".html", "text/html"),
        CSS(".css", "text/css"),
        JS(".js", "text/javascript"),
        WOFF(".woff","application/x-font-woff"),
        TTF(".ttf","application/x-font-ttf"),
        ICO(".ico", "image/x-icon"),
        PNG(".png", "image/x-png"),
        JPG(".jpg", "image/jpeg");
        private final String extension;
        private final String mimeType;
        Type(String extension, String mimeType) {
            this.extension = extension;
            this.mimeType = mimeType;
        }

        public String getExtension() {
            return extension;
        }

        public String getMimeType() {
            return mimeType;
        }
    }
    private static final webserver.http.response.header.MimeType MIME_TYPE = new webserver.http.response.header.MimeType();
    private final Map<String, String> extension;

    private MimeType() {
        Map<String, String> tmpMap = new HashMap<>();
        for(Type type : Type.values()) {
            tmpMap.put(type.getExtension(), type.getMimeType());
        }
        extension = ImmutableMap.<String, String>builder()
                .putAll(tmpMap)
                .build();
    }

    public static webserver.http.response.header.MimeType createHttpContentType() {
        return MIME_TYPE;
    }

    public String getContentType(String s) {
        if(s == null) {
            return null;
        }
        return extension.get(s);
    }

}
