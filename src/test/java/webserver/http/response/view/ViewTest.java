package webserver.http.response.view;

import db.Database;
import org.junit.jupiter.api.Test;
import model.User;

import static org.junit.jupiter.api.Assertions.*;

public class ViewTest {

    @Test
    public void testViewWithRedirectUrl() {
        String redirectUrl = "/user/redirect";
        View view = new View(redirectUrl, null);

        assertNull(view.readBody());
        assertEquals(0, view.getLength());
    }

    @Test
    public void testViewWithHtmlUrl() {
        String htmlUrl = "/user/sample.html";
        User user = new User("testUser", "testPassword", "John Doe", "john@example.com");
        String expectedBody = "<html><body><h1>Welcome, John Doe!</h1></body></html>";

        View view = new View(htmlUrl, user);


        assertNotNull(view.readBody());
        assertEquals(expectedBody, new String(view.readBody()));
        assertEquals(expectedBody.getBytes().length, view.getLength());
    }

    @Test
    public void testViewWithNullUser() {
        String htmlUrl = "/user/sample.html";
        User user = null;
        String expectedBody = "<html><body><h1>Welcome, :model.name:!</h1></body></html>";

        View view = new View(htmlUrl, user);

        assertNotNull(view.readBody());
        assertEquals(expectedBody, new String(view.readBody()));
        assertEquals(expectedBody.getBytes().length, view.getLength());
    }

    @Test
    public void testViewWithUserList() {
        Database.clear();
        String htmlUrl = "/user/user_list.html";
        User user1 = new User("user1", "password1", "User One", "user1@example.com");
        User user2 = new User("user2", "password2", "User Two", "user2@example.com");
        Database.addUser(user1);
        Database.addUser(user2);
        String expectedBody = "<html><body><ul>\n" +
                "    \n" +
                "    <li>User user1 User One</li>\n" +
                "    \n" +
                "    <li>User user2 User Two</li>\n" +
                "    \n" +
                "</ul></body></html>";

        View view = new View(htmlUrl, null);

        assertNotNull(view.readBody());
        assertEquals(expectedBody, new String(view.readBody()));
        assertEquals(expectedBody.getBytes().length, view.getLength());
    }
}
