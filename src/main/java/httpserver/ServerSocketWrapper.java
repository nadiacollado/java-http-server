package httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketWrapper implements SocketWrapper {
    ServerSocket serverSocket;
    Socket clientSocket;

    public void connectSockets(int port) throws IOException {
        try {
            createServerSocket(port);
            System.out.println("Listening for connection on port " + port);
        } catch (IOException e) {
            System.out.println("Error creating server socket");
        }

        while (true) {
            try {
                clientSocket = acceptClient();
                System.out.println("Connected to client!");
            } catch (IOException e) {
                System.out.println("Error connecting to client");

            }

            IO.buildIOStream(clientSocket);
            IO.echo();
            close();
        }
    }

    public ServerSocket createServerSocket(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        return serverSocket;
    }

    public Socket acceptClient() throws IOException {
        clientSocket = serverSocket.accept();
        return clientSocket;
    }

    public void close() throws IOException {
        clientSocket.close();
        serverSocket.close();
    }
}
