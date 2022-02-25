package httpserver.response;

import httpserver.interfaces.IRouter;
import httpserver.utils.Mappers;
import httpserver.interfaces.IResponseWriter;
import httpserver.models.Request;
import httpserver.models.Response;

import java.io.IOException;
import java.io.OutputStream;

import static httpserver.utils.Constants.*;
import static httpserver.utils.StatusCodes.*;

public class ResponseWriter implements IResponseWriter {
    public Response getResponse(Request request, IRouter router) throws IOException {
        Response response;
        String[] methods = router.getMethods(request);

        if (router.isPathValid(request) && router.isMethodValid(request, methods)) {
            response = buildSuccessResponse(request, methods);
            return response;
        }

        if (router.isPathValid(request) && !(router.isMethodValid(request, methods))) {
            response = buildMethodNotAllowedResponse(request, methods);
            return response;
        }

        response = buildPageNotFoundResponse(request);
        return response;
    }

    public Response buildSuccessResponse(Request request, String[] methods) throws IOException {
        Response response;
        if (request.path.equals("/simple_get_with_body")) {
            response = new ResponseBuilder()
                    .setStatusCode(SUCCESS)
                    .setBody("Hello world")
                    .build();
            return response;
        }

        if (request.path.equals("/echo_body")) {
            response = new ResponseBuilder()
                    .setStatusCode(SUCCESS)
                    .setBody(request.body)
                    .build();
            return response;
        }

        if (request.path.equals("/redirect")) {
            response = new ResponseBuilder()
                    .setStatusCode(REDIRECT)
                    .addHeader(LOCATION, "http://127.0.0.1:5000/simple_get")
                    .build();
            return response;
        }

        if (request.path.equals("/text_response")) {
            response = new ResponseBuilder()
                    .setStatusCode(SUCCESS)
                    .addHeader(TYPE, "text/plain;charset=utf-8")
                    .setBody("text response")
                    .build();
            return response;
        }

        if (request.path.equals("/html_response")) {
            response = new ResponseBuilder()
                    .setStatusCode(SUCCESS)
                    .addHeader(TYPE, "text/html;charset=utf-8")
                    .setBody("<html><body><p>HTML Response</p></body></html>")
                    .build();
            return response;
        }

        if (request.path.equals("/json_response")) {
            response = new ResponseBuilder()
                    .setStatusCode(SUCCESS)
                    .addHeader(TYPE, "application/json;charset=utf-8")
                    .setBody(Mappers.convertObjToJSON("value1", "value2"))
                    .build();
            return response;
        }

        if (request.path.equals("/xml_response")) {
            response = new ResponseBuilder()
                    .setStatusCode(SUCCESS)
                    .addHeader(TYPE, "application/xml;charset=utf-8")
                    .setBody(Mappers.convertObjToXML("XML Response"))
                    .build();
            return response;
        }

        response = new ResponseBuilder()
                .setStatusCode(SUCCESS)
                .addHeader(ALLOW, stringifyMethods(methods))
                .build();
        return response;
    }

    public Response buildPageNotFoundResponse(Request request) {
        Response response = new ResponseBuilder()
                .setStatusCode(PAGE_NOT_FOUND)
                .build();
        return response;
    }

    public Response buildMethodNotAllowedResponse(Request request, String[] methods) {
        Response response = new ResponseBuilder()
                .setStatusCode(METHOD_NOT_ALLOWED)
                .addHeader(ALLOW, stringifyMethods(methods))
                .build();
        return response;
    }

    public String formatResponse(Response response) {
        StringBuilder formattedResponse = new StringBuilder();
        appendStatusLine(formattedResponse, response);
        appendHeaders(formattedResponse, response);
        appendBody(formattedResponse, response);
        return formattedResponse.toString();
    }

    public void sendResponse(Response response, OutputStream outputStream) throws IOException {
        String formattedResponse = formatResponse(response);
        outputStream.write(formattedResponse.getBytes());
    }

    public String getStatusLine(Response response) {
        return response.protocol + " " + response.statusCode + LINE_BREAK;
    }

    public String stringifyHeaders(Response response) {
        StringBuilder formattedHeaders = new StringBuilder();
        response.headers.forEach((headerName, headerValue) -> {
            formattedHeaders.append(headerName + ": " + headerValue + LINE_BREAK);
        });
        formattedHeaders.append(LINE_BREAK);
        return formattedHeaders.toString();
    }

    public String stringifyMethods(String[] methodsList) {
        return String.join(", ", methodsList);
    }

    private StringBuilder appendStatusLine(StringBuilder builder, Response response) {
        String statusLine = getStatusLine(response);
        builder.append(statusLine);
        return builder;
    }

    private StringBuilder appendHeaders(StringBuilder builder, Response response) {
        if (hasHeaders(response)) {
            String headers = stringifyHeaders(response);
            builder.append(headers);
        }
        return builder;
    }

    private StringBuilder appendBody(StringBuilder builder, Response response) {
        if (hasBody(response)) {
            builder.append(response.body);
        }
        return builder;
    }

    private boolean hasHeaders(Response response) {
        return response.headers != null;
    }

    private boolean hasBody(Response response) {
        return response.body != null;
    }
}