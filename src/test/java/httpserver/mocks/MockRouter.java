package httpserver.mocks;

import httpserver.interfaces.IRouter;
import httpserver.models.Request;
import httpserver.utils.Methods;

import java.util.HashMap;
import java.util.Map;

public class MockRouter implements IRouter {
    public Map<String, String[]> routes;

    public MockRouter() {
        this.routes = addRoutes();
    }

    @Override
    public Map<String, String[]> addRoutes() {
        Map<String, String[]> routes = new HashMap<>();
        routes.put("/simple_get_with_body", new String[]{Methods.GET});
        return routes;
    }

    @Override
    public String[] getMethods(Request request) {
        return routes.get(request.path);
    }

    @Override
    public boolean isPathValid(Request request) {
        return routes.containsKey(request.path);
    }

    @Override
    public boolean isMethodValid(Request request, String[] methods) {
        if (request.method.equals(Methods.GET)) return true;
        return false;
    }
}
