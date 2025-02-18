import webserver.server.WebServer;


public class ServerApplication {

    public static void main(String[] args) throws Exception {
        WebServer webServer = new WebServer();
        webServer.run(args);
    }
}
