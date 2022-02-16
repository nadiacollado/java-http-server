package httpserver.models;

import httpserver.utils.Constants;

import java.util.HashMap;

public class Response {
    public String statusCode;
    public String body;
    public String protocol;
    public HashMap<String, String[]> headers;

    public Response(String statusCode, HashMap<String, String[]> headers, String body) {
        this.protocol = Constants.PROTOCOL;
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }
}


