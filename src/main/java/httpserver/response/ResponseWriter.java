package httpserver.response;

import httpserver.interfaces.IResponseWriter;
import httpserver.models.Request;
import httpserver.models.Response;

public class ResponseWriter implements IResponseWriter {
    final String LINE_BREAK = "\r\n\r\n";

    public Response build(Request request) {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        if (request.path.equals("/simple_get")) {
            setStatusCode("200 OK", responseBuilder);
        } else if (request.path.equals("/simple_get_with_body")){
            setStatusCode("200 OK", responseBuilder);
            setBody(responseBuilder, "Hello world");
        } else {
            setStatusCode("404 Not Found", responseBuilder);
            return responseBuilder.build();
        }
        setHeaders(responseBuilder);
        return responseBuilder.build();
    }

    public String formatResponse(Response response) {
        String formattedResponse;
        String statusLine = getStatusLine(response);
        if (response.body == null) {
            formattedResponse = statusLine;
        } else {
            formattedResponse = statusLine + response.body;
        }
        return formattedResponse;
    }

    public void setStatusCode(String statusCode, ResponseBuilder responseBuilder) {
        responseBuilder.setStatusCode(statusCode);
    }

    public String getStatusLine(Response response) {
        return response.protocol + " " + response.statusCode + LINE_BREAK;
    }

    public void setHeaders(ResponseBuilder responseBuilder) {
        responseBuilder.addHeader("Content-Type", "text/html");
    }

    public void setBody(ResponseBuilder responseBuilder, String header) {
        responseBuilder.setBody(header);
    }
}
