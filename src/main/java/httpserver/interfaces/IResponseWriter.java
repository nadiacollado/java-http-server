package httpserver.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import httpserver.models.Request;
import httpserver.models.Response;

import java.io.IOException;

public interface IResponseWriter {
    String formatResponse(Response response) throws IOException;
    Response buildSuccessResponse(Request request, String[] methods) throws IOException;
    Response buildPageNotFoundResponse(Request request);
    Response buildMethodNotAllowedResponse(Request request, String[] methods);
}
