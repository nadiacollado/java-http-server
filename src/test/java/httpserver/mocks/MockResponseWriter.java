package httpserver.mocks;

import httpserver.interfaces.IResponseWriter;
import httpserver.models.Request;
import httpserver.models.Response;

public class MockResponseWriter implements IResponseWriter {
    public boolean buildSuccessResponseCalled = false;
    public boolean formatResponseCalled = false;

    @Override
    public Response buildSuccessResponse(Request request){
        buildSuccessResponseCalled = true;
        return new Response(null, null, null);
    }

    @Override
    public Response buildPageNotFoundResponse(Request request){
        return new Response(null, null, null);
    }

    @Override
    public String formatResponse(Response response) {
        formatResponseCalled = true;
        return response.protocol + " " + response.statusCode;
    }

    public boolean wasBuildSuccessResponseCalled() {
        return buildSuccessResponseCalled;
    }
    public boolean wasFormatResponseCalled() {
        return formatResponseCalled;
    }
}
