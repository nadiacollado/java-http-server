package httpserver.router;

import httpserver.interfaces.IRouter;
import httpserver.utils.Methods;
import httpserver.models.Request;

import java.util.HashMap;
import java.util.Map;

public class Router implements IRouter {
    public Map<String, String[]> routes;

    public Router() {
        this.routes = addRoutes();
    }

    public Map<String, String[]> addRoutes() {
        Map<String, String[]> routes = new HashMap<>();

        routes.put("/simple_get", new String[]{Methods.GET, Methods.HEAD});
        routes.put("/simple_get_with_body", new String[]{Methods.GET});
        routes.put("/head_request", new String[]{Methods.HEAD, Methods.OPTIONS});
        routes.put("/method_options", new String[]{Methods.GET, Methods.HEAD, Methods.OPTIONS});
        routes.put("/method_options2", new String[]{Methods.GET, Methods.HEAD, Methods.OPTIONS, Methods.POST, Methods.PUT});
        routes.put("/echo_body", new String[]{Methods.POST});
        routes.put("/redirect", new String[]{Methods.GET});
        routes.put("/text_response", new String[]{Methods.GET});
        routes.put("/html_response", new String[]{Methods.GET});
        return routes;
    }

    public String[] getMethods(Request request) {
        return routes.get(request.path);
    }

    public boolean isPathValid(Request request) {
        return routes.containsKey(request.path);
    }

    public boolean isMethodValid(Request request, String[] methods) {
        for (String method : methods) {
            if (method.equals(request.method)) {
                return true;
            }
        }
        return false;
    }
}
