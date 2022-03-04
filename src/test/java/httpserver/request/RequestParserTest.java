package httpserver.request;

import httpserver.mocks.MockBufferedReader;
import httpserver.mocks.MockReader;
import httpserver.mocks.MockRequestReader;
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
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Connection", "close");
        RequestBuilder requestBuilder = new RequestBuilder();
        MockReader mockReader = new MockReader();
        MockBufferedReader mockBufferedReader = new MockBufferedReader(mockReader);
        mockBufferedReader.setReadLineValues(new String[] {"GET /simple_get_with_body HTTP/1.1", "Connection: close"});
        MockRequestReader mockRequestReader = new MockRequestReader(mockBufferedReader);
        RequestParser handler = new RequestParser(requestBuilder);

        Request formattedRequest = handler.parse(mockRequestReader);

        assertTrue(mockRequestReader.wasReadLineCalled());
        assertEquals(GET, formattedRequest.method);
        assertEquals("/simple_get_with_body", formattedRequest.path);
        assertEquals(PROTOCOL, formattedRequest.protocol);
        assertEquals(headers, formattedRequest.headers);
        assertEquals(null, formattedRequest.body);
    }

    @Test
    public void setsHeaders() throws IOException {
        HashMap<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Connection", "close");
        RequestBuilder requestBuilder = new RequestBuilder();
        MockReader mockReader = new MockReader();
        MockBufferedReader mockBufferedReader = new MockBufferedReader(mockReader);
        mockBufferedReader.setReadLineValues(new String[] {"Connection: close"});
        MockRequestReader mockRequestReader = new MockRequestReader(mockBufferedReader);
        RequestParser parser = new RequestParser(requestBuilder);
        parser.reader = mockRequestReader;
        Request request = requestBuilder.build();

        parser.setHeaders();

        assertTrue(mockRequestReader.wasReadLineCalled());
        assertEquals(expectedHeaders, request.headers);
    }

    @Test
    public void setHeadersHandlesNewLine() throws IOException {
        HashMap<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Content-Length", "0");
        RequestBuilder requestBuilder = new RequestBuilder();
        MockReader mockReader = new MockReader();
        MockBufferedReader mockBufferedReader = new MockBufferedReader(mockReader);
        mockBufferedReader.setReadLineValues(new String[] {"Content-Length: 0", DOUBLE_LINE_BREAK});
        MockRequestReader mockRequestReader = new MockRequestReader(mockBufferedReader);
        RequestParser parser = new RequestParser(requestBuilder);
        parser.reader = mockRequestReader;
        Request request = requestBuilder.build();

        parser.setHeaders();

        assertTrue(mockRequestReader.wasReadLineCalled());
        assertEquals(expectedHeaders, request.headers);
    }

    @Test
    public void setsBody() throws IOException {
        String requestBody = "Body Line";
        RequestBuilder requestBuilder = new RequestBuilder();
        MockReader mockReader = new MockReader();
        MockBufferedReader mockBufferedReader = new MockBufferedReader(mockReader);
        mockBufferedReader.setReadValues(requestBody);
        MockRequestReader mockRequestReader = new MockRequestReader(mockBufferedReader);
        RequestParser parser = new RequestParser(requestBuilder);
        parser.reader = mockRequestReader;

        parser.setBody("9");
        Request request = requestBuilder.build();

        assertTrue(mockRequestReader.wasReadCalled());
        assertEquals(requestBody, request.body);
    }

    @Test
    public void setsBodyToNullWhenRequestDoesNotIncludeBody() throws IOException {
        RequestBuilder requestBuilder = new RequestBuilder();
        MockReader mockReader = new MockReader();
        MockBufferedReader mockBufferedReader = new MockBufferedReader(mockReader);
        MockRequestReader mockRequestReader = new MockRequestReader(mockBufferedReader);
        RequestParser parser = new RequestParser(requestBuilder);
        parser.reader = mockRequestReader;

        Request request = requestBuilder.build();
        parser.setBody("0");

        assertFalse(mockRequestReader.wasReadCalled());
        assertEquals(null, request.body);
    }
}
