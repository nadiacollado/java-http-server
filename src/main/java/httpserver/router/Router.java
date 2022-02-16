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
        routes.put("/head_request", new String[]{Methods.HEAD});
        routes.put("/method_options", new String[]{Methods.GET, Methods.HEAD, Methods.OPTIONS});
        routes.put("/method_options2", new String[]{Methods.GET, Methods.HEAD, Methods.OPTIONS, Methods.POST, Methods.PUT});
        return routes;
    }

    public boolean isRequestValid(Request request) {
        if (routes.containsKey(request.path)) {
            String[] methodsForPath = routes.get(request.path);
            for (int i = 0; i < methodsForPath.length; i++) {
                if (methodsForPath[i].equals(request.method)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String[] getMethods(Request request) {
        return routes.get(request.path);
    }
}
