package httpserver.server;

import httpserver.interfaces.ISocketHandler;

import java.io.IOException;

public class Server {
    ISocketHandler socketHandler;

    public Server(ISocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    public void start(int port) {
        try {
            socketHandler.connectSockets(port);
        } catch (IOException e) {
            System.out.println("Cannot connect sockets");
        }

    }
}
