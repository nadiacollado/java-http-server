package httpserver.models;

import java.util.HashMap;

public class Response {
    public String statusCode;
    public String body;
    public String protocol;
    public HashMap<String, String> headers = new HashMap<>();

    public Response() {}

    public Response(String protocol, String statusCode, HashMap<String, String> headers, String body) {
        this.protocol = protocol;
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }
}


