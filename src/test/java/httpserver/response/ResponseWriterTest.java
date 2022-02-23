package httpserver.response;

import httpserver.interfaces.IRouter;
import httpserver.mocks.MockOutputStream;
import httpserver.mocks.MockRouter;
import httpserver.models.Request;
import httpserver.models.Response;
import httpserver.utils.Constants;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static httpserver.utils.Constants.*;
import static httpserver.utils.Methods.*;
import static httpserver.utils.StatusCodes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResponseWriterTest {

    @Test
    public void returns200ResponseForRequestWithValidPathAndMethod() throws IOException {
        ResponseWriter responseWriter = new ResponseWriter();
        IRouter mockRouter = new MockRouter();
        Request request = new Request(GET,"/simple_get_with_body", PROTOCOL, null, null);

        Response response = responseWriter.getResponse(request, mockRouter);

        assertEquals(SUCCESS, response.statusCode);
    }

    @Test
    public void returns405ResponseForRequestWithValidPathAndInvalidMethod() throws IOException {
        ResponseWriter responseWriter = new ResponseWriter();
        IRouter mockRouter = new MockRouter();
        Request request = new Request(HEAD,"/simple_get_with_body", PROTOCOL, null, null);

        Response response = responseWriter.getResponse(request, mockRouter);

        assertEquals(METHOD_NOT_ALLOWED, response.statusCode);
    }

    @Test
    public void returns404ResponseForRequestWithInvalidPath() throws IOException {
        ResponseWriter responseWriter = new ResponseWriter();
        IRouter mockRouter = new MockRouter();
        Request request = new Request(HEAD,"/simple_donut", PROTOCOL, null, null);

        Response response = responseWriter.getResponse(request, mockRouter);

        assertEquals(PAGE_NOT_FOUND, response.statusCode);
    }

    @Test
    public void returnsA200OKResponse() throws IOException {
        ResponseWriter responseWriter = new ResponseWriter();
        Request request = new Request(GET,"/simple_get", PROTOCOL, null, null);

        Response response = responseWriter.buildSuccessResponse(request, new String[]{request.method});

        assertEquals(SUCCESS, response.statusCode);
    }

    @Test
    public void returnsA405ResponseIfAPathExistsButMethodIsNotAllowed() {
        ResponseWriter responseWriter = new ResponseWriter();
        Request request = new Request(PUT,"/simple_get", PROTOCOL, null, null);

        Response response = responseWriter.buildMethodNotAllowedResponse(request, new String[]{request.method});

        assertEquals(METHOD_NOT_ALLOWED, response.statusCode);
    }

    @Test
    public void returnsA404ResponseIfAPageThatDoesNotExist() {
        ResponseWriter responseWriter = new ResponseWriter();
        Request request = new Request(GET,"/does_not_exist", PROTOCOL, null, null);

        Response response = responseWriter.buildPageNotFoundResponse(request);

        assertEquals(PAGE_NOT_FOUND, response.statusCode);
    }

    @Test
    void sendsResponseBackToClient() throws IOException {
        ResponseWriter responseWriter = new ResponseWriter();
        Response testResponse = new Response(SUCCESS, null, null);
        MockOutputStream mockOutputStream = new MockOutputStream();

        responseWriter.sendResponse(testResponse, mockOutputStream);

        assertTrue(mockOutputStream.wasWriteCalled());
    }

    @Test
    public void createsFormattedResponseWithHeaders() throws IOException {
        ResponseWriter responseWriter = new ResponseWriter();
        Request request = new Request(GET,"/method_options", PROTOCOL, null, null);
        Response response = responseWriter.buildSuccessResponse(request, new String[]{request.method});
        String headers = Constants.ALLOW + ": " + GET;
        String expectedResponse = PROTOCOL + " " + SUCCESS + LINE_BREAK + headers + DOUBLE_LINE_BREAK;

        String formattedResponse = responseWriter.formatResponse(response);

        assertEquals(expectedResponse, formattedResponse);
    }

    @Test
    public void createsFormattedResponseWithHeadersAndBody() throws IOException {
        ResponseWriter responseWriter = new ResponseWriter();
        Request request = new Request(GET,"/text_response", PROTOCOL, null, null);
        Response response = responseWriter.buildSuccessResponse(request, new String[]{request.method});
        String headers = Constants.TYPE + ": text/plain;charset=utf-8";
        String expectedResponse = PROTOCOL + " " + SUCCESS + LINE_BREAK + headers + DOUBLE_LINE_BREAK + "text response";

        String formattedResponse = responseWriter.formatResponse(response);

        assertEquals(expectedResponse, formattedResponse);
    }

    @Test
    public void createsFormattedResponseWithNoHeadersAndNoBody() {
        ResponseWriter responseWriter = new ResponseWriter();
        Request request = new Request(GET,"/does_not_exist", PROTOCOL, null, null);
        Response response = responseWriter.buildPageNotFoundResponse(request);
        String expectedResponse = PROTOCOL + " " + PAGE_NOT_FOUND + DOUBLE_LINE_BREAK;

        String formattedResponse = responseWriter.formatResponse(response);

        assertEquals(expectedResponse, formattedResponse);
    }

    @Test
    public void returnsStringOfHeaders() {
        ResponseWriter responseWriter = new ResponseWriter();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Bananas", "here");
        Response response = new Response(SUCCESS, headers, null);
        String formattedHeaders = responseWriter.stringifyHeaders(response);

        assertEquals("Bananas: here", formattedHeaders.trim());
    }

    @Test
    public void returnsStringOfAllowedMethodsForAllowHeader() {
        ResponseWriter responseWriter = new ResponseWriter();
        String[] methodsList = {"GET", "HEAD", "OPTIONS"};
        String methods = responseWriter.stringifyMethods(methodsList);

        assertEquals("GET, HEAD, OPTIONS", methods);
    }
}
