package httpserver.response;

import httpserver.interfaces.IRouter;
import httpserver.mocks.MockOutputStream;
import httpserver.mocks.MockRouter;
import httpserver.models.Request;
import httpserver.models.Response;
import httpserver.utils.Methods;
import httpserver.utils.StatusCodes;
import httpserver.utils.Constants;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResponseWriterTest {

    @Test
    public void returns200ResponseForRequestWithValidPathAndMethod() throws IOException {
        ResponseWriter responseWriter = new ResponseWriter();
        IRouter mockRouter = new MockRouter();
        Request request = new Request(Methods.GET,"/simple_get_with_body", Constants.PROTOCOL, null, null);

        Response response = responseWriter.getResponse(request, mockRouter);

        assertEquals(StatusCodes.SUCCESS, response.statusCode);
    }

    @Test
    public void returns405ResponseForRequestWithValidPathAndInvalidMethod() throws IOException {
        ResponseWriter responseWriter = new ResponseWriter();
        IRouter mockRouter = new MockRouter();
        Request request = new Request(Methods.HEAD,"/simple_get_with_body", Constants.PROTOCOL, null, null);

        Response response = responseWriter.getResponse(request, mockRouter);

        assertEquals(StatusCodes.METHOD_NOT_ALLOWED, response.statusCode);
    }

    @Test
    public void returns404ResponseForRequestWithInvalidPath() throws IOException {
        ResponseWriter responseWriter = new ResponseWriter();
        IRouter mockRouter = new MockRouter();
        Request request = new Request(Methods.HEAD,"/simple_donut", Constants.PROTOCOL, null, null);

        Response response = responseWriter.getResponse(request, mockRouter);

        assertEquals(StatusCodes.PAGE_NOT_FOUND, response.statusCode);
    }

    @Test
    public void returnsA200OKResponse() throws IOException {
        ResponseWriter responseWriter = new ResponseWriter();
        Request request = new Request(Methods.GET,"/simple_get", Constants.PROTOCOL, null, null);

        Response response = responseWriter.buildSuccessResponse(request, new String[]{request.method});

        assertEquals(StatusCodes.SUCCESS, response.statusCode);
    }

    @Test
    public void returnsA405ResponseIfAPathExistsButMethodIsNotAllowed() {
        ResponseWriter responseWriter = new ResponseWriter();
        Request request = new Request(Methods.PUT,"/get_simple", Constants.PROTOCOL, null, null);

        Response response = responseWriter.buildMethodNotAllowedResponse(request, new String[]{request.method});

        assertEquals(StatusCodes.METHOD_NOT_ALLOWED, response.statusCode);
    }

    @Test
    public void returnsA404ResponseIfAPageThatDoesNotExist() {
        ResponseWriter responseWriter = new ResponseWriter();
        Request request = new Request(Methods.GET,"/does_not_exist", Constants.PROTOCOL, null, null);

        Response response = responseWriter.buildPageNotFoundResponse(request);

        assertEquals(StatusCodes.PAGE_NOT_FOUND, response.statusCode);
    }

    @Test
    void sendsResponseBackToClient() throws IOException {
        ResponseWriter responseWriter = new ResponseWriter();
        Response testResponse = new Response(StatusCodes.SUCCESS, null, null);
        MockOutputStream mockOutputStream = new MockOutputStream();

        responseWriter.sendResponse(testResponse, mockOutputStream);

        assertTrue(mockOutputStream.wasWriteCalled());
    }

    @Test
    public void createsFormattedResponseWithHeaders() throws IOException {
        ResponseWriter responseWriter = new ResponseWriter();
        Request request = new Request(Methods.GET,"/method_options", Constants.PROTOCOL, null, null);
        Response response = responseWriter.buildSuccessResponse(request, new String[]{request.method});
        String headers = Constants.ALLOW + ": " + Methods.GET;
        String expectedResponse = Constants.PROTOCOL + " " + StatusCodes.SUCCESS + Constants.LINE_BREAK + headers + Constants.LINE_BREAK + Constants.LINE_BREAK;

        String formattedResponse = responseWriter.formatResponse(response);

        assertEquals(expectedResponse, formattedResponse);
    }

    @Test
    public void createsFormattedResponseWithHeadersAndBody() throws IOException {
        ResponseWriter responseWriter = new ResponseWriter();
        Request request = new Request(Methods.GET,"/text_response", Constants.PROTOCOL, null, null);
        Response response = responseWriter.buildSuccessResponse(request, new String[]{request.method});
        String headers = Constants.TYPE + ": text/plain;charset=utf-8";
        String expectedResponse = Constants.PROTOCOL + " " + StatusCodes.SUCCESS + Constants.LINE_BREAK + headers + Constants.LINE_BREAK + Constants.LINE_BREAK + "text response";

        String formattedResponse = responseWriter.formatResponse(response);

        assertEquals(expectedResponse, formattedResponse);
    }

    @Test
    public void createsFormattedResponseWithNoHeadersAndNoBody() {
        ResponseWriter responseWriter = new ResponseWriter();
        Request request = new Request(Methods.GET,"/does_not_exist", Constants.PROTOCOL, null, null);
        Response response = responseWriter.buildPageNotFoundResponse(request);
        String expectedResponse = Constants.PROTOCOL + " " + StatusCodes.PAGE_NOT_FOUND + Constants.LINE_BREAK + Constants.LINE_BREAK;

        String formattedResponse = responseWriter.formatResponse(response);

        assertEquals(expectedResponse, formattedResponse);
    }

    @Test
    public void returnsStringOfHeaders() {
        ResponseWriter responseWriter = new ResponseWriter();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Bananas", "here");
        Response response = new Response(StatusCodes.SUCCESS, headers, null);
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
