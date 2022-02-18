package httpserver.response;

import httpserver.utils.StatusCodes;
import httpserver.utils.Constants;
import httpserver.interfaces.IResponseWriter;
import httpserver.models.Request;
import httpserver.models.Response;

import java.util.Date;
import java.util.HashMap;

public class ResponseWriter implements IResponseWriter {

    public Response buildSuccessResponse(Request request, String[] methods) {
        Response response;
        if (request.path.equals("/simple_get_with_body")) {
            response = new ResponseBuilder()
                    .setStatusCode(StatusCodes.SUCCESS)
                    .addHeader(Constants.ALLOW, stringifyMethods(methods))
                    .setBody("Hello world")
                    .build();
            return response;
        }

        if (request.path.equals("/echo_body")) {
            response = new ResponseBuilder()
                    .setStatusCode(StatusCodes.SUCCESS)
                    .setBody(request.body)
                    .build();
            return response;
        }

        if (request.path.equals("/redirect")) {
            response = new ResponseBuilder()
                    .setStatusCode(StatusCodes.REDIRECT)
                    .addHeader(Constants.LOCATION, "http://127.0.0.1:5000/simple_get")
                    .build();
            return response;
        }

        if (request.path.equals("/text_response")) {
            response = new ResponseBuilder()
                    .setStatusCode(StatusCodes.SUCCESS)
                    .addHeader(Constants.TYPE, "text/plain;charset=utf-8")
                    .setBody("text response")
                    .build();
            return response;
        }

        response = new ResponseBuilder()
                .setStatusCode(StatusCodes.SUCCESS)
                .addHeader(Constants.ALLOW, stringifyMethods(methods))
                .build();
        return response;
    }

    public Response buildPageNotFoundResponse(Request request) {
        Response response = new ResponseBuilder()
                .setStatusCode(StatusCodes.PAGE_NOT_FOUND)
                .build();
        return response;
    }

    public Response buildMethodNotAllowedResponse(Request request, String[] methods) {
        Response response = new ResponseBuilder()
                .setStatusCode(StatusCodes.METHOD_NOT_ALLOWED)
                .addHeader(Constants.ALLOW, stringifyMethods(methods))
                .build();
        return response;
    }

    public String formatResponse(Response response) {
        String headers = null;
        StringBuilder formattedResponse = new StringBuilder();
        String statusLine = getStatusLine(response);
        formattedResponse.append(statusLine);

        if (hasHeaders(response) && (!hasBody(response))) {
            headers = stringifyHeaders(response);
            formattedResponse.append(headers);
            return formattedResponse.toString();
        }

        if (hasBody(response)){
            headers = stringifyHeaders(response);
            formattedResponse.append(headers);
            formattedResponse.append(response.body);
            return formattedResponse.toString();
        }

        formattedResponse.append(Constants.LINE_BREAK);
        return formattedResponse.toString();
    }

    public String getStatusLine(Response response) {
        return response.protocol + " " + response.statusCode + Constants.LINE_BREAK;
    }

    public String stringifyHeaders(Response response) {
        StringBuilder formattedHeaders = new StringBuilder();
        response.headers.forEach((headerName, headerValue) -> {
            formattedHeaders.append(headerName + ": " + headerValue + Constants.LINE_BREAK);
        });
        formattedHeaders.append(Constants.LINE_BREAK);
        return formattedHeaders.toString();
    }

    public String stringifyMethods(String[] methodsList) {
        return String.join(", ", methodsList);
    }

    private boolean hasHeaders(Response response) {
        return response.headers.size() >= 1;
    }

    private boolean hasBody(Response response) {
        return response.body != null;
    }
}
