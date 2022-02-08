package httpserver.server;

import httpserver.mocks.MockSocketHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RunServerTest {
    @Test
    public void testSocketsAreCreated() {
        MockSocketHandler mockSocketHandler = new MockSocketHandler();
        Server server = new Server(mockSocketHandler);
        server.start(8080);

        assertTrue(mockSocketHandler.wasConnectSocketsCalled());
        assertTrue(mockSocketHandler.wasCreateServerSocketCalled());
        assertTrue(mockSocketHandler.wasAcceptClientCalled());
    }
}