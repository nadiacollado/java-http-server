package httpserver.handlers;

import httpserver.interfaces.IRequestParser;
import httpserver.interfaces.IResponseWriter;
import httpserver.interfaces.IRouter;
import httpserver.mocks.*;
import httpserver.request.RequestBuilder;
import httpserver.request.RequestParser;
import httpserver.response.ResponseWriter;
import httpserver.utils.Constants;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class ClientHandlerTest {

    @Test
    public void runWritesResponse() {
        MockInputStream input = new MockInputStream();
        MockOutputStream output = new MockOutputStream();
        MockRequestParser parser = new MockRequestParser();
        MockResponseWriter writer = new MockResponseWriter();
        IRouter router = new MockRouter();
        ClientHandler clientHandler = new ClientHandler(input, output, parser, writer, router);

        clientHandler.run();

        assertTrue(parser.wasParseCalled());
        assertTrue(writer.wasGetResponseCalled());
        assertTrue(writer.wasSendResponseCalled());
    }
}