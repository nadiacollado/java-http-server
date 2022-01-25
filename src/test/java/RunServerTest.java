import httpserver.Server;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RunServerTest {
    @Test
    public void testSocketsAreCreated() {
        MockServerSocketWrapper mockSocketWrapper = new MockServerSocketWrapper();
        Server server = new Server(mockSocketWrapper);
        server.start(8080);

        assertTrue(mockSocketWrapper.wasConnectSocketsCalled());
        assertTrue(mockSocketWrapper.wasCreateServerSocketCalled());
        assertTrue(mockSocketWrapper.wasAcceptClientCalled());
    }
}