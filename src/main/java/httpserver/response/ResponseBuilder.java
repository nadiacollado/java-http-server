package httpserver.response;

import httpserver.models.Response;

import java.util.HashMap;

public class ResponseBuilder {
    private String statusCode;
    private String protocol;
    private HashMap<String, String> headers;
    private String body;

    public ResponseBuilder() {
        headers = new HashMap<>();
    }

    public Response build() {
        return new Response(protocol, statusCode, headers, body);
    }

    public ResponseBuilder setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public ResponseBuilder setStatusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public ResponseBuilder addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public ResponseBuilder setBody(String body) {
        this.body = body;
        return this;
    }
}
