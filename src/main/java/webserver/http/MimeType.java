package webserver.http;

public enum MimeType {

    HTML(".html", "text/html"),
    CSS(".css", "text/css"),
    JS(".js", "text/javascript"),
    WOFF(".woff","application/x-font-woff"),
    TTF(".ttf","application/x-font-ttf"),

    ICO(".ico", "image/x-ico"),
    PNG(".png", "image/x-png"),
    JPG(".jpg", "image/jpeg");
    private final String extension;
    private final String mimeType;
    MimeType(String extension, String mimeType) {
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
