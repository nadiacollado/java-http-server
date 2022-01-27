package httpserver.mocks;

import httpserver.interfaces.IResponseWriter;
import httpserver.models.Request;
import httpserver.models.Response;

public class MockResponseWriter implements IResponseWriter {
    public boolean buildCalled = false;
    public boolean formatResponseCalled = false;

    @Override
    public Response build(Request request) {
        buildCalled = true;
        return new Response();
    }

    @Override
    public String formatResponse(Response response) {
        formatResponseCalled = true;
        return response.protocol + " " + response.statusCode;
    }

    public boolean wasBuildCalled() {
        return buildCalled;
    }
    public boolean wasFormatResponseCalled() {
        return formatResponseCalled;
    }
}
