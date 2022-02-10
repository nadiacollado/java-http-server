package httpserver.server;

import httpserver.interfaces.IRouter;
import httpserver.mocks.MockOutputStream;
import httpserver.mocks.MockRequestParser;
import httpserver.mocks.MockResponseWriter;
import httpserver.mocks.MockRouter;
import httpserver.models.Request;
import httpserver.models.Response;
import httpserver.router.Router;
import httpserver.server.SocketHandler;
import httpserver.utils.StatusCodes;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SocketHandlerTest {

    @Test
    void processClientRequestsParsesARequest() throws IOException {
        MockRequestParser mockRequestParser = new MockRequestParser();
        MockResponseWriter mockResponseWriter = new MockResponseWriter();
        MockRouter mockRouter = new MockRouter();
        SocketHandler socketHandler = new SocketHandler(mockRequestParser, mockResponseWriter, mockRouter);
        String startLine = "GET /simple_get_with_body HTTP/1.1";
        InputStream inputStream = new ByteArrayInputStream(startLine.getBytes());

        socketHandler.processClientRequests(inputStream);

        assertTrue(mockRequestParser.wasParseCalled());
    }

    @Test
    void processClientRequestsReturnsARequestObject() throws IOException {
        MockRequestParser mockRequestParser = new MockRequestParser();
        MockResponseWriter mockResponseWriter = new MockResponseWriter();
        MockRouter mockRouter = new MockRouter();
        SocketHandler socketHandler = new SocketHandler(mockRequestParser, mockResponseWriter, mockRouter);
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
        MockRouter mockRouter = new MockRouter();
        SocketHandler socketHandler = new SocketHandler(mockRequestParser, mockResponseWriter, mockRouter);
        String startLine = "GET /simple_get_with_body HTTP/1.1";
        InputStream inputStream = new ByteArrayInputStream(startLine.getBytes());

        Request request = socketHandler.processClientRequests(inputStream);
        Response response = socketHandler.processServerResponse(request);

        assertTrue(mockResponseWriter.wasBuildSuccessResponseCalled());
        assertNotNull(response);
    }

    @Test
    void sendsResponseBackToClient() throws IOException {
        MockRequestParser mockRequestParser = new MockRequestParser();
        MockResponseWriter mockResponseWriter = new MockResponseWriter();
        MockRouter mockRouter = new MockRouter();
        SocketHandler socketHandler = new SocketHandler(mockRequestParser, mockResponseWriter, mockRouter);
        Response testResponse = new Response(StatusCodes.SUCCESS, null, null);
        MockOutputStream mockOutputStream = new MockOutputStream();

        socketHandler.sendResponse(testResponse, mockOutputStream);

        assertTrue(mockResponseWriter.wasFormatResponseCalled());
        assertTrue(mockOutputStream.wasWriteCalled());
    }
}