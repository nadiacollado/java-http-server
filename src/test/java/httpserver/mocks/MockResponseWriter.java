package httpserver.mocks;

import httpserver.interfaces.IResponseWriter;
import httpserver.interfaces.IRouter;
import httpserver.models.Request;
import httpserver.models.Response;

import java.io.OutputStream;

public class MockResponseWriter implements IResponseWriter {
    public boolean buildSuccessResponseCalled = false;
    public boolean formatResponseCalled = false;
    public boolean getResponseCalled = false;
    public boolean sendResponseCalled = false;

    @Override
    public Response buildSuccessResponse(Request request, String[] methods){
        buildSuccessResponseCalled = true;
        return new Response(null, null, null);
    }

    @Override
    public Response buildPageNotFoundResponse(Request request){
        return new Response(null, null, null);
    }

    @Override
    public Response buildMethodNotAllowedResponse(Request request, String[] methods){
        return new Response(null, null, null);
    }

    @Override
    public Response getResponse(Request request, IRouter router) {
        getResponseCalled = true;
        return null;
    }

    @Override
    public void sendResponse(Response response, OutputStream outputStream) {
        sendResponseCalled = true;
    }

    @Override
    public String formatResponse(Response response) {
        formatResponseCalled = true;
        return response.protocol + " " + response.statusCode;
    }

    public boolean wasGetResponseCalled() {
        return getResponseCalled;
    }
    public boolean wasSendResponseCalled() {
        return sendResponseCalled;
    }
}