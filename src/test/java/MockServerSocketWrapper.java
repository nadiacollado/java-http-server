import httpserver.SocketWrapper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MockServerSocketWrapper implements SocketWrapper {
    public boolean connectSocketCalled = false;
    public boolean acceptClientCalled = false;
    public boolean createServerSocketCalled = false;
    public boolean closeCalled = false;

    @Override
    public void connectSockets(int port) throws IOException {
        connectSocketCalled = true;
        createServerSocket(port);
    }

    @Override
    public ServerSocket createServerSocket(int port) throws IOException {
        createServerSocketCalled = true;
        acceptClient();
        return null;
    }

    @Override
    public Socket acceptClient() throws IOException {
        acceptClientCalled = true;
        return null;
    }

    @Override
    public void close() throws IOException {
        closeCalled = true;
    }

    public boolean wasConnectSocketsCalled() {
        return connectSocketCalled;
    }

    public boolean wasCreateServerSocketCalled() {
        return createServerSocketCalled;
    }

    public boolean wasAcceptClientCalled() {
        return acceptClientCalled;
    }

    public boolean wasCloseCalled() {
        return closeCalled;
    }
}
