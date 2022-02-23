package httpserver.handlers;

import httpserver.interfaces.IRequestParser;
import httpserver.interfaces.IResponseWriter;
import httpserver.interfaces.IRouter;
import httpserver.interfaces.ISocketHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketHandler implements ISocketHandler {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private IRequestParser requestParser;
    private IResponseWriter responseWriter;
    private IRouter router;


    public SocketHandler(IRequestParser requestParser, IResponseWriter responseWriter, IRouter router){
        this.requestParser = requestParser;
        this.responseWriter = responseWriter;
        this.router = router;
    }

    public void connectSockets(int port) {
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
                ClientHandler clientHandler = new ClientHandler(clientSocket.getInputStream(), clientSocket.getOutputStream(), requestParser, responseWriter, router);
                Thread thread = new Thread(clientHandler);
                thread.start();
                thread.join();
                close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
    }
}