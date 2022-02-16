package httpserver.interfaces;

import httpserver.models.Request;

import java.util.Map;

public interface IRouter {
    Map<String, String[]> addRoutes();
    String[] getMethods(Request request);
    boolean isPathValid(Request request);
    boolean isMethodValid(Request request, String[] methods);
}
