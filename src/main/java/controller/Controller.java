package controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface Controller {
    String execute(HttpRequest request, HttpResponse response);
}
    