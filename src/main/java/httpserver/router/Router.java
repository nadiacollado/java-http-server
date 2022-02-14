package httpserver.router;

import httpserver.interfaces.IRouter;
import httpserver.utils.Methods;
import httpserver.models.Request;

import java.util.HashMap;
import java.util.Map;

public class Router implements IRouter {
    Map<String, String[]> routes;

    public Router() {
        this.routes = addRoutes();
    }

    public Map<String, String[]> addRoutes() {
        Map<String, String[]> routes = new HashMap<>();

        routes.put("/simple_get", new String[]{Methods.GET, Methods.HEAD});
        routes.put("/simple_get_with_body", new String[]{Methods.GET});
        routes.put("/head_request", new String[]{Methods.HEAD});
        return routes;
    }

    public boolean isRequestValid(Request request) {
        for (Map.Entry<String, String[]> set : routes.entrySet()) {
            if (set.getKey().equals(request.path)) {
                String[] methodsForPath = set.getValue();
                for (int i = 0; i < methodsForPath.length; i++) {
                    if (methodsForPath[i].equals(request.method)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
