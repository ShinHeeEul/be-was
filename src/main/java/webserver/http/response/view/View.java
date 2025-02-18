package webserver.http.response.view;

import service.UserService;
import model.User;
import webserver.util.Parser;
import webserver.util.ViewResolver;

import java.util.Collection;

public class View {
    private final byte[] body;

    public View(String url, User user) {
        byte[] byteBody = ViewResolver.getContent(url, Parser.getUrlExtension(url));

        if(url.contains("redirect")) {
            body = null;
            return;
        }
        if(url.contains(".html")) {
            String stringBody = parseBody(user, byteBody);
            if(stringBody != null) {
                body = stringBody.getBytes();
                return;
            }
            body = null;
            return;
        }
        body = byteBody;
    }

    private static String parseBody(User user, byte[] byteBody) {
        if(byteBody == null) {
             return null;
        }
        String stringBody = new String(byteBody);
        stringBody = getUserList(stringBody);
        if (user != null) {
            stringBody = stringBody.replaceAll("not_login::", "<!--");
            stringBody = stringBody.replaceAll("::not_login", "-->");
            stringBody = stringBody.replaceAll("login::", "");
            stringBody = stringBody.replaceAll("::login", "");


            stringBody = stringBody.replaceAll(":model.userId:", user.getUserId());
            stringBody = stringBody.replaceAll(":model.name:", user.getName());
            stringBody = stringBody.replaceAll(":model.email:", user.getEmail());

            return stringBody;
        }
        stringBody = stringBody.replaceAll("not_login::", "");
        stringBody = stringBody.replaceAll("::not_login", "");
        stringBody = stringBody.replaceAll("login::", "<!--");
        stringBody = stringBody.replaceAll("::login", "-->");
        return stringBody;
    }

    private static String getUserList(String stringBody) {
        StringBuilder stringBuilder = new StringBuilder();
        UserService userService = UserService.of();
        if(stringBody.contains("for::")) {
            Collection<User> users = userService.findAll();
            int i = 1;
            String subString = stringBody.substring(stringBody.indexOf("for::"), stringBody.indexOf("::for"));
            for (User u : users) {
                String repeatPart = subString;
                repeatPart = existReplace(repeatPart, ":i:", i + "");
                repeatPart = existReplace(repeatPart,":model.userId:", u.getUserId());
                repeatPart = existReplace(repeatPart,":model.name:", u.getName());
                repeatPart = existReplace(repeatPart,":model.email:", u.getEmail());

                stringBuilder.append(repeatPart);
                i++;
            }
            System.out.println(stringBuilder);
            stringBody = stringBody.replace(subString, stringBuilder.toString());
            stringBody = stringBody.replaceAll("for::", "");
            stringBody = stringBody.replaceAll("::for", "");
        }
        return stringBody;
    }

    private static String existReplace(String repeatPart, String regex, String replacement) {
        if(!repeatPart.contains(regex)) {
            return repeatPart;
        }
        return repeatPart.replace(regex, replacement);
    }

    public int getLength() {
        return body == null ?0:body.length;
    }

    public byte[] readBody() {
        return body;
    }
}
