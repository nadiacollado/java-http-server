package httpserver;

import java.io.IOException;

public class Server {
    SocketWrapper socketWrapper;

    public Server(SocketWrapper socketWrapper) {
        this.socketWrapper = socketWrapper;
    }

    public void start(int port) {
        try {
            socketWrapper.connectSockets(port);
        } catch (IOException e) {
            System.out.println("Cannot connect sockets");
        }

    }
}
