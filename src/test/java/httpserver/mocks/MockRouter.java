package httpserver.mocks;

import httpserver.interfaces.IRouter;
import httpserver.models.Request;

import java.util.Map;

public class MockRouter implements IRouter {
    public boolean addRoutesCalled = false;
    public boolean isRequestValidCalled = false;

    public Map<String, String[]> addRoutes() {
        addRoutesCalled = true;
        return null;
    }

    public boolean isRequestValid(Request request) {
        isRequestValidCalled = true;
        return true;
    }

    public String[] getMethods(Request request) {
        return null;
    }

    public boolean wasAddRoutesCalled() {
        return addRoutesCalled;
    }
    public boolean wasIsRequestValidCalled() {
        return isRequestValidCalled;
    }
}
