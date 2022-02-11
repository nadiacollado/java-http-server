package httpserver.response;

import httpserver.interfaces.IResponseWriter;
import httpserver.mocks.MockResponseWriter;
import httpserver.models.Request;
import httpserver.models.Response;
import httpserver.utils.Methods;
import httpserver.utils.StatusCodes;
import httpserver.utils.constants;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseWriterTest {
    @Test
    public void returnsASuccessStatusCodeInResponse() {
        IResponseWriter responseWriter = new ResponseWriter();
        Request request = new Request(Methods.GET,"/simple_get", constants.PROTOCOL, null, null);

        Response response = responseWriter.buildSuccessResponse(request);

        assertEquals(StatusCodes.SUCCESS, response.statusCode);
    }

    @Test
    public void returnsEmptyBodyInResponse() {
        IResponseWriter responseWriter = new ResponseWriter();
        Request request = new Request(Methods.GET,"/simple_get", constants.PROTOCOL, null, null);

        Response response = responseWriter.buildSuccessResponse(request);

        assertEquals(null, response.body);
    }

    @Test
    public void returnsResponseHeaders() {
        IResponseWriter responseWriter = new ResponseWriter();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html");
        Request request = new Request(Methods.GET,"/simple_get", constants.PROTOCOL, null, null);

        Response response = responseWriter.buildSuccessResponse(request);

        assertEquals(headers, response.headers);
    }

    @Test
    public void createsFormattedResponse() {
        ResponseWriter responseWriter = new ResponseWriter();
        Response response = new Response(StatusCodes.SUCCESS, null, null);
        String statusLine = "HTTP/1.1 200 OK\r\n\r\n";
        String formattedResponse = responseWriter.formatResponse(response);

        assertEquals(statusLine, formattedResponse);
    }

    @Test
    public void returnsA404ResponseAPageThatDoesNotExist() {
        ResponseWriter responseWriter = new ResponseWriter();
        Request request = new Request(Methods.GET,"/does_not_exist", constants.PROTOCOL, null, null);

        Response response = responseWriter.buildPageNotFoundResponse(request);

        assertEquals(StatusCodes.PAGE_NOT_FOUND, response.statusCode);
    }
}
