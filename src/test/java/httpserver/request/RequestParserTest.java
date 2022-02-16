package httpserver.request;

import httpserver.models.Request;
import httpserver.utils.Methods;
import httpserver.utils.Constants;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class RequestParserTest {
    @Test
    public void parsesHTTPRequest () throws IOException {
        String request =
                "GET /simple_get_with_body HTTP/1.1\n" +
                        "Connection: close\n"  +
                        "Host: 127.0.0.1:5000\n" +
                        "User-Agent: http.rb/4.3.0\n" +
                        "Content-Length: 0";

        InputStream requestStream = new ByteArrayInputStream(request.getBytes());

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Connection", "close");
        headers.put("Host", "127.0.0.1:5000");
        headers.put("User-Agent", "http.rb/4.3.0");
        headers.put("Content-Length", "0");

        RequestParser handler = new RequestParser();
        Request formattedRequest = handler.parse(requestStream);

        assertEquals(Methods.GET, formattedRequest.method);
        assertEquals("/simple_get_with_body", formattedRequest.path);
        assertEquals(Constants.PROTOCOL, formattedRequest.protocol);
        assertEquals(headers, formattedRequest.headers);
        assertNull(formattedRequest.body);
    }

    @Test
    public void setsHeaders() throws IOException {
        String headers =
                "Connection: close\n" +
                "Host: 127.0.0.1:5000\n" +
                "User-Agent: http.rb/4.3.0\n" +
                "Content-Length: 0";
        InputStream headersBytes = new ByteArrayInputStream(headers.getBytes());
        HashMap<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Connection", "close");
        expectedHeaders.put("Host", "127.0.0.1:5000");
        expectedHeaders.put("User-Agent", "http.rb/4.3.0");
        expectedHeaders.put("Content-Length", "0");
        RequestParser parser = new RequestParser();
        parser.reader = new BufferedReader(new InputStreamReader(headersBytes));
        RequestBuilder requestBuilder = new RequestBuilder();
        Request request = requestBuilder.build();

        parser.setHeaders(requestBuilder);

        assertEquals(expectedHeaders, request.headers);
    }

    @Test
    public void setHeadersHandlesNewLine() throws IOException {
        String headers =
                "Connection: close\n" +
                        "Host: 127.0.0.1:5000\n" +
                        "User-Agent: http.rb/4.3.0\n" +
                        "Content-Length: 0" +
                        "\n";
        InputStream headersBytes = new ByteArrayInputStream(headers.getBytes());
        HashMap<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Connection", "close");
        expectedHeaders.put("Host", "127.0.0.1:5000");
        expectedHeaders.put("User-Agent", "http.rb/4.3.0");
        expectedHeaders.put("Content-Length", "0");
        RequestParser parser = new RequestParser();
        parser.reader = new BufferedReader(new InputStreamReader(headersBytes));
        RequestBuilder requestBuilder = new RequestBuilder();
        Request request = requestBuilder.build();

        parser.setHeaders(requestBuilder);

        assertEquals(expectedHeaders, request.headers);
    }

    @Test
    public void setsBody() throws IOException {
        String requestWithBody =
                "GET /simple_get_with_body HTTP/1.1\n" +
                        "Connection: close\n"  +
                        "Host: 127.0.0.1:5000\n" +
                        "User-Agent: http.rb/4.3.0\n" +
                        "Content-Length: 11\r\n\r\n" +
                        "Body Line";
        InputStream requestBytes = new ByteArrayInputStream(requestWithBody.getBytes());
        RequestParser parser = new RequestParser();
        parser.reader = new BufferedReader(new InputStreamReader(requestBytes));
        Request request = parser.parse(requestBytes);

        assertEquals("Body Line", request.body);
    }

    @Test
    public void setsBodyToNullWhenRequestDoesNotIncludeBody() throws IOException {
        String headers =
                        "Host: 127.0.0.1:5000\n" +
                        "Content-Length: 0\n" +
                        "\n";
        InputStream headersBytes = new ByteArrayInputStream(headers.getBytes());
        HashMap<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Host", "127.0.0.1:5000");
        expectedHeaders.put("Content-Length", "0");
        RequestParser parser = new RequestParser();
        parser.reader = new BufferedReader(new InputStreamReader(headersBytes));
        RequestBuilder requestBuilder = new RequestBuilder();
        Request request = requestBuilder.build();

        parser.setHeaders(requestBuilder);
        parser.setBody("0", requestBuilder);

        assertEquals(null, request.body);
    }
}
