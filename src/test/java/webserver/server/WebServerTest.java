package webserver.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class WebServerTest {

    private WebServer webServer;

    @BeforeEach
    public void setUp() {
        webServer = new WebServer();
    }

    @Test
    public void testRunWithInvalidPort() {
        String invalidPort = "invalid";
        assertThrows(NumberFormatException.class, () -> webServer.run(new String[]{invalidPort}));
    }
}
