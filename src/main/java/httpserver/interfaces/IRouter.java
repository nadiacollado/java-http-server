package httpserver.interfaces;

import httpserver.models.Request;

import java.util.Map;

public interface IRouter {
    Map<String, String[]> addRoutes();
    boolean isRequestValid(Request request);
    String[] getMethods(Request request);
}
