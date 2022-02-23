package httpserver.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import httpserver.models.Request;
import httpserver.models.Response;

import java.io.IOException;
import java.io.OutputStream;

public interface IResponseWriter {
    String formatResponse(Response response) throws IOException;
    Response buildSuccessResponse(Request request, String[] methods) throws IOException;
    Response buildPageNotFoundResponse(Request request);
    Response buildMethodNotAllowedResponse(Request request, String[] methods);
    Response getResponse(Request request, IRouter router) throws IOException;
    void sendResponse(Response response, OutputStream outputStream) throws IOException;
}
