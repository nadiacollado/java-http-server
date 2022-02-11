package httpserver.router;

import httpserver.utils.Methods;
import httpserver.models.Request;
import httpserver.utils.constants;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RouterTest {

    @Test
    void returnsTrueIfRequestIsValid() {
        Map<String, String[]> routes = new HashMap<>();
        routes.put("/simple_get", new String[]{Methods.GET});
        Router router = new Router();
        Request request = new Request(Methods.GET,"/simple_get", constants.PROTOCOL, null, null);

        assertTrue(router.isRequestValid(request));
    }

    @Test
    void addsRoutes() {
        Router router = new Router();
        Map<String, String[]> routesFromSocket = router.addRoutes();

        assertNotNull(routesFromSocket);
    }
}