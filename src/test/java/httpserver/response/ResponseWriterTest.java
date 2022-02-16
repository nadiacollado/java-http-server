package httpserver.response;

import httpserver.interfaces.IResponseWriter;
import httpserver.models.Request;
import httpserver.models.Response;
import httpserver.utils.Methods;
import httpserver.utils.StatusCodes;
import httpserver.utils.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseWriterTest {
    @Test
    public void returnsA200OKResponse() {
        IResponseWriter responseWriter = new ResponseWriter();
        Request request = new Request(Methods.GET,"/simple_get", Constants.PROTOCOL, null, null);

        Response response = responseWriter.buildSuccessResponse(request, new String[]{request.method});

        assertEquals(StatusCodes.SUCCESS, response.statusCode);
    }

    @Test
    public void returnsA404ResponseIfAPageThatDoesNotExist() {
        ResponseWriter responseWriter = new ResponseWriter();
        Request request = new Request(Methods.GET,"/does_not_exist", Constants.PROTOCOL, null, null);

        Response response = responseWriter.buildPageNotFoundResponse(request);

        assertEquals(StatusCodes.PAGE_NOT_FOUND, response.statusCode);
    }

    @Test
    public void createsFormattedResponseWithHeaders() {
        ResponseWriter responseWriter = new ResponseWriter();
        Request request = new Request(Methods.GET,"/method_options", Constants.PROTOCOL, null, null);
        Response response = responseWriter.buildSuccessResponse(request, new String[]{request.method});
        String headers = "Allow: GET";
        String expectedResponse = Constants.PROTOCOL + " " + StatusCodes.SUCCESS + Constants.LINE_BREAK + headers + Constants.LINE_BREAK + Constants.LINE_BREAK;

        String formattedResponse = responseWriter.formatResponse(response);

        assertEquals(expectedResponse, formattedResponse);
    }

    @Test
    public void createsFormattedResponseWithHeadersAndBody() {
        ResponseWriter responseWriter = new ResponseWriter();
        Request request = new Request(Methods.GET,"/simple_get_with_body", Constants.PROTOCOL, null, null);
        Response response = responseWriter.buildSuccessResponse(request, new String[]{request.method});
        String headers = "Allow: GET";
        String expectedResponse = Constants.PROTOCOL + " " + StatusCodes.SUCCESS + Constants.LINE_BREAK + headers + Constants.LINE_BREAK + Constants.LINE_BREAK + "Hello world";

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
}
