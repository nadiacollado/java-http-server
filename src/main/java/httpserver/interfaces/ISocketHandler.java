package httpserver.interfaces;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public interface ISocketHandler {
    void connectSockets(int port) throws IOException;
    ServerSocket createServerSocket(int port) throws IOException;
    Socket acceptClient() throws IOException;
}
