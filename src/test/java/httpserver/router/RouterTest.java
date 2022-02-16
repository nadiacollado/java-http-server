package httpserver.router;

import httpserver.utils.Methods;
import httpserver.models.Request;
import httpserver.utils.Constants;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RouterTest {

    @Test
    void addsRoutes() {
        Router router = new Router();
        Map<String, String[]> routesFromSocket = router.addRoutes();

        assertNotNull(routesFromSocket);
    }

    @Test
    void returnsRoutesForGivenRequest() {
        Request request = new Request(Methods.GET,"/simple_get", Constants.PROTOCOL, null, null);
        Router router = new Router();

        String[] methodsList = router.getMethods(request);
        String methods = Arrays.toString(methodsList);

        assertEquals("[GET, HEAD]", methods);
    }

    @Test
    void returnsTrueIfPathExistsInRouter() {
        Request request = new Request(Methods.GET,"/simple_get", Constants.PROTOCOL, null, null);
        Router router = new Router();

        assertTrue(router.isPathValid(request));
    }

    @Test
    void returnsFalseIfPathDoesNotExistInRouter() {
        Request request = new Request(Methods.GET,"/simple_get_thousand", Constants.PROTOCOL, null, null);
        Router router = new Router();

        assertFalse(router.isPathValid(request));
    }

    @Test
    void returnsTrueIfMethodIsAllowedForRequest() {
        Request request = new Request(Methods.GET,"/simple_get", Constants.PROTOCOL, null, null);
        Router router = new Router();
        String[] methods = router.getMethods(request);

        assertTrue(router.isMethodValid(request, methods));
    }

    @Test
    void returnsFalseIfMethodIsNotAllowedForRequest() {
        Request request = new Request(Methods.OPTIONS,"/simple_get", Constants.PROTOCOL, null, null);
        Router router = new Router();
        String[] methods = router.getMethods(request);

        assertFalse(router.isMethodValid(request, methods));
    }
}