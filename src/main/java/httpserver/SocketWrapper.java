package httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public interface SocketWrapper {
    void connectSockets(int port) throws IOException;
    ServerSocket createServerSocket(int port) throws IOException;
    Socket acceptClient() throws IOException;
    void close() throws IOException;
}
