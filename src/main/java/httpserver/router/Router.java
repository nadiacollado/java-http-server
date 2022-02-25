package httpserver.router;

import httpserver.interfaces.IRouter;
import httpserver.utils.Methods;
import httpserver.models.Request;

import java.util.HashMap;
import java.util.Map;

import static httpserver.utils.Methods.*;

public class Router implements IRouter {
    public Map<String, String[]> routes;

    public Router() {
        this.routes = addRoutes();
    }

    public Map<String, String[]> addRoutes() {
        Map<String, String[]> routes = new HashMap<>();

        routes.put("/simple_get", new String[]{GET, HEAD});
        routes.put("/simple_get_with_body", new String[]{GET});
        routes.put("/head_request", new String[]{HEAD, OPTIONS});
        routes.put("/method_options", new String[]{GET, HEAD, OPTIONS});
        routes.put("/method_options2", new String[]{GET, HEAD, OPTIONS, POST, PUT});
        routes.put("/echo_body", new String[]{POST});
        routes.put("/redirect", new String[]{GET});
        routes.put("/text_response", new String[]{GET});
        routes.put("/html_response", new String[]{GET});
        routes.put("/json_response", new String[]{GET});
        routes.put("/xml_response", new String[]{GET});
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
