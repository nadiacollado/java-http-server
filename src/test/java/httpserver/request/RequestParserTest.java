package httpserver.request;

import httpserver.models.Request;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;

import static httpserver.utils.Constants.*;
import static httpserver.utils.Methods.*;
import static org.junit.jupiter.api.Assertions.*;

public class RequestParserTest {
    @Test
    public void parsesHTTPRequest () throws IOException {
        String request =
                "GET /simple_get_with_body HTTP/1.1\n" +
                        "Connection: close\n"  +
                        "Host: 127.0.0.1:5000\n" +
                        "User-Agent: http.rb/4.3.0\n" +
                        "Content-Length: 0" + DOUBLE_LINE_BREAK;
        InputStream requestStream = new ByteArrayInputStream(request.getBytes());
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Connection", "close");
        headers.put("Host", "127.0.0.1:5000");
        headers.put("User-Agent", "http.rb/4.3.0");
        headers.put("Content-Length", "0");
        RequestBuilder requestBuilder = new RequestBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(requestStream));
        RequestReader reader = new RequestReader(bufferedReader);
        RequestParser handler = new RequestParser(requestBuilder);

        Request formattedRequest = handler.parse(reader);

        assertEquals(GET, formattedRequest.method);
        assertEquals("/simple_get_with_body", formattedRequest.path);
        assertEquals(PROTOCOL, formattedRequest.protocol);
        assertEquals(headers, formattedRequest.headers);
        assertEquals(null, formattedRequest.body);
    }

    @Test
    public void setsHeaders() throws IOException {
        String headers =
                "Connection: close\n" +
                "Host: 127.0.0.1:5000\n" +
                "User-Agent: http.rb/4.3.0\n" +
                "Content-Length: 0" + DOUBLE_LINE_BREAK;
        InputStream headersBytes = new ByteArrayInputStream(headers.getBytes());
        HashMap<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Connection", "close");
        expectedHeaders.put("Host", "127.0.0.1:5000");
        expectedHeaders.put("User-Agent", "http.rb/4.3.0");
        expectedHeaders.put("Content-Length", "0");
        RequestBuilder requestBuilder = new RequestBuilder();
        RequestParser parser = new RequestParser(requestBuilder);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(headersBytes));
        RequestReader reader = new RequestReader(bufferedReader);
        parser.reader = reader;
        Request request = requestBuilder.build();

        parser.setHeaders();

        assertEquals(expectedHeaders, request.headers);
    }

    @Test
    public void setHeadersHandlesNewLine() throws IOException {
        String headers =
                "Connection: close\n" +
                        "Host: 127.0.0.1:5000\n" +
                        "User-Agent: http.rb/4.3.0\n" +
                        "Content-Length: 0" + DOUBLE_LINE_BREAK;
        InputStream headersBytes = new ByteArrayInputStream(headers.getBytes());
        HashMap<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Connection", "close");
        expectedHeaders.put("Host", "127.0.0.1:5000");
        expectedHeaders.put("User-Agent", "http.rb/4.3.0");
        expectedHeaders.put("Content-Length", "0");
        RequestBuilder requestBuilder = new RequestBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(headersBytes));
        RequestReader reader = new RequestReader(bufferedReader);
        RequestParser parser = new RequestParser(requestBuilder);
        parser.reader = reader;
        Request request = requestBuilder.build();

        parser.setHeaders();

        assertEquals(expectedHeaders, request.headers);
    }

    @Test
    public void setsBody() throws IOException {
        String requestBody = "Body Line";
        InputStream bodyBytes = new ByteArrayInputStream(requestBody.getBytes());
        RequestBuilder requestBuilder = new RequestBuilder();
        RequestParser parser = new RequestParser(requestBuilder);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bodyBytes));
        RequestReader reader = new RequestReader(bufferedReader);
        parser.reader = reader;

        parser.setBody("9");
        Request request = requestBuilder.build();

        assertEquals(requestBody, request.body);
    }

    @Test
    public void setsBodyToNullWhenRequestDoesNotIncludeBody() throws IOException {
        String headers =
                        "Host: 127.0.0.1:5000\n" +
                        "Content-Length: 0" + DOUBLE_LINE_BREAK;
        InputStream headerBytes = new ByteArrayInputStream(headers.getBytes());
        HashMap<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Host", "127.0.0.1:5000");
        expectedHeaders.put("Content-Length", "0");
        RequestBuilder requestBuilder = new RequestBuilder();
        RequestParser parser = new RequestParser(requestBuilder);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(headerBytes));
        RequestReader reader = new RequestReader(bufferedReader);
        parser.reader = reader;
        Request request = requestBuilder.build();

        parser.setHeaders();
        parser.setBody("0");

        assertEquals(null, request.body);
    }
}
