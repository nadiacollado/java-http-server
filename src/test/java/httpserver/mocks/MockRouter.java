package httpserver.mocks;

import httpserver.interfaces.IRouter;
import httpserver.models.Request;

import java.util.Map;

public class MockRouter implements IRouter {
    public Map<String, String[]> addRoutes() {
        return null;
    }

    public boolean isPathValid(Request request) {
        return true;
    }

    public boolean isMethodValid(Request request, String[] methods) {
        return true;
    }

    public String[] getMethods(Request request) {
        return null;
    }
}
