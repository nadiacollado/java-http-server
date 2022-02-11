package httpserver.interfaces;

import httpserver.models.Request;
import httpserver.models.Response;

import java.io.IOException;

public interface IResponseWriter {
    String formatResponse(Response response) throws IOException;
    Response buildSuccessResponse(Request request);
    Response buildPageNotFoundResponse(Request request);
}
