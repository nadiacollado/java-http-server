package httpserver.mocks;

import httpserver.interfaces.ISocketHandler;

import java.net.ServerSocket;
import java.net.Socket;

public class MockSocketHandler implements ISocketHandler {
    public boolean connectSocketCalled = false;
    public boolean acceptClientCalled = false;
    public boolean createServerSocketCalled = false;

    @Override
    public void connectSockets(int port) {
        connectSocketCalled = true;
        createServerSocket(port);
    }

    @Override
    public ServerSocket createServerSocket(int port) {
        createServerSocketCalled = true;
        acceptClient();
        return null;
    }

    @Override
    public Socket acceptClient() {
        acceptClientCalled = true;
        return null;
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
}
