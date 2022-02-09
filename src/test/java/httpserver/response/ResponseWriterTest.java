package httpserver.response;

import httpserver.interfaces.IResponseWriter;
import httpserver.mocks.MockResponseWriter;
import httpserver.models.Request;
import httpserver.models.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseWriterTest {
    @Test
    public void returnsASuccessStatusCodeInResponse() {
        IResponseWriter responseWriter = new ResponseWriter();
        Request request = new Request("GET","/simple_get", "HTTP/1.1", null, null);

        Response response = responseWriter.build(request);

        assertEquals("200 OK", response.statusCode);
    }

    @Test
    public void returnsEmptyBodyInResponse() {
        IResponseWriter responseWriter = new ResponseWriter();
        Request request = new Request("GET","/simple_get", "HTTP/1.1", null, null);

        Response response = responseWriter.build(request);

        assertEquals(null, response.body);
    }

    @Test
    public void returnsResponseHeaders() {
        IResponseWriter responseWriter = new ResponseWriter();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html");
        Request request = new Request("GET","/simple_get", "HTTP/1.1", headers, null);

        Response response = responseWriter.build(request);

        assertEquals(headers, response.headers);
    }

    @Test
    public void createsFormattedResponse() {
        ResponseWriter responseWriter = new ResponseWriter();
        Response response = new Response();
        String statusLine = "HTTP/1.1 200 OK\r\n\r\n";
        response.protocol = "HTTP/1.1";
        response.statusCode = "200 OK";
        String formattedResponse = responseWriter.formatResponse(response);

        assertEquals(statusLine, formattedResponse);
    }

    @Test
    public void returnsA404ResponseAPageThatDoesNotExist() {
        ResponseWriter responseWriter = new ResponseWriter();
        Request request = new Request("GET","/does_not_exist", "HTTP/1.1", null, null);

        Response response = responseWriter.build(request);

        assertEquals("404 Not Found", response.statusCode);
    }
}
