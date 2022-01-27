package httpserver.response;

import httpserver.interfaces.IResponseWriter;
import httpserver.models.Request;
import httpserver.models.Response;

public class ResponseWriter implements IResponseWriter {
    final String LINE_BREAK = "\r\n\r\n";

    public Response build(Request request) {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        setStatusLine("HTTP/1.1", "200 OK", responseBuilder);
        setHeaders(responseBuilder);
        if (request.path != null && request.path.equals("/simple_get_with_body")){
            setBody(responseBuilder, "Hello world");
        }
        return responseBuilder.build();
    }

    public String formatResponse(Response response) {
        String formattedResponse;
        if (response.body == null) {
            formattedResponse = response.protocol + " " + response.statusCode + LINE_BREAK;
        } else {
            formattedResponse = response.protocol + " " + response.statusCode + LINE_BREAK + response.body;
        }
        return formattedResponse;
    }

    public void setStatusLine(String protocol, String statusCode, ResponseBuilder responseBuilder) {
        responseBuilder
                .setProtocol(protocol)
                .setStatusCode(statusCode);
    }

    public void setHeaders(ResponseBuilder responseBuilder) {
        responseBuilder.addHeader("Content-Type", "text/html");
    }

    public void setBody(ResponseBuilder responseBuilder, String header) {
        responseBuilder.setBody(header);
    }
}
