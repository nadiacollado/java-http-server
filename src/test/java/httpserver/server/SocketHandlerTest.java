package httpserver.server;

import httpserver.mocks.MockOutputStream;
import httpserver.mocks.MockRequestParser;
import httpserver.mocks.MockResponseWriter;
import httpserver.mocks.MockRouter;
import httpserver.models.Request;
import httpserver.models.Response;
import httpserver.utils.Methods;
import httpserver.utils.StatusCodes;
import httpserver.utils.Constants;
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
        MockRouter mockRouter = new MockRouter();
        SocketHandler socketHandler = new SocketHandler(mockRequestParser, mockResponseWriter, mockRouter);
        String startLine = String.format("%s %s %s", Methods.GET, "simple_get_with_body", Constants.PROTOCOL);
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
        String startLine = String.format("%s %s %s", Methods.GET, "simple_get_with_body", Constants.PROTOCOL);
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
        String startLine = String.format("%s %s %s", Methods.GET, "simple_get_with_body", Constants.PROTOCOL);
        InputStream inputStream = new ByteArrayInputStream(startLine.getBytes());

        Request request = socketHandler.processClientRequests(inputStream);
        Response response = socketHandler.processServerResponse(request);

        assertTrue(mockResponseWriter.wasBuildSuccessResponseWithMethodsCalled());
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