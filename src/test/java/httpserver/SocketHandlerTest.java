package httpserver;

import httpserver.mocks.MockOutputStream;
import httpserver.mocks.MockRequestParser;
import httpserver.mocks.MockResponseWriter;
import httpserver.models.Request;
import httpserver.models.Response;
import httpserver.response.ResponseWriter;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class SocketHandlerTest {

    @Test
    void processClientRequestsParsesARequest() throws IOException {
        MockRequestParser mockRequestParser = new MockRequestParser();
        MockResponseWriter mockResponseWriter = new MockResponseWriter();
        SocketHandler socketHandler = new SocketHandler(mockRequestParser, mockResponseWriter);
        String startLine = "GET /simple_get_with_body HTTP/1.1";
        InputStream inputStream = new ByteArrayInputStream(startLine.getBytes());

        socketHandler.processClientRequests(inputStream);

        assertTrue(mockRequestParser.wasParseCalled());
    }

    @Test
    void processClientRequestsReturnsARequestObject() throws IOException {
        MockRequestParser mockRequestParser = new MockRequestParser();
        MockResponseWriter mockResponseWriter = new MockResponseWriter();
        SocketHandler socketHandler = new SocketHandler(mockRequestParser, mockResponseWriter);
        String startLine = "GET /simple_get_with_body HTTP/1.1";
        InputStream inputStream = new ByteArrayInputStream(startLine.getBytes());

        Request request = socketHandler.processClientRequests(inputStream);

        assertTrue(mockRequestParser.wasParseCalled());
        assertNotNull(request);
    }

    @Test
    void processServerResponseReturnsAResponseObject() throws IOException {
        MockRequestParser mockRequestParser = new MockRequestParser();
        MockResponseWriter mockResponseWriter = new MockResponseWriter();
        SocketHandler socketHandler = new SocketHandler(mockRequestParser, mockResponseWriter);
        String startLine = "GET /simple_get_with_body HTTP/1.1";
        InputStream inputStream = new ByteArrayInputStream(startLine.getBytes());

        Request request = socketHandler.processClientRequests(inputStream);
        Response response = socketHandler.processServerResponse(request);

        assertTrue(mockResponseWriter.wasBuildCalled());
        assertNotNull(response);
    }

    @Test
    public void sendsResponseBackToClient() throws IOException {
        MockRequestParser mockRequestParser = new MockRequestParser();
        MockResponseWriter mockResponseWriter = new MockResponseWriter();
        SocketHandler socketHandler = new SocketHandler(mockRequestParser, mockResponseWriter);
        Response testResponse = new Response();
        testResponse.protocol = "HTTP/1.1";
        testResponse.statusCode = "200";
        MockOutputStream mockOutputStream = new MockOutputStream();

        socketHandler.sendResponse(testResponse, mockOutputStream);

        assertTrue(mockResponseWriter.wasFormatResponseCalled());
        assertTrue(mockOutputStream.wasWriteCalled());
    }
}