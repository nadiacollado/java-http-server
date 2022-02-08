package httpserver.interfaces;

import httpserver.models.Request;
import httpserver.models.Response;

import java.io.IOException;

public interface IResponseWriter {
    Response build(Request request);
    String formatResponse(Response response) throws IOException;
}
