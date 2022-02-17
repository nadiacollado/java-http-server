package httpserver.server;

import httpserver.interfaces.IRequestParser;
import httpserver.interfaces.IResponseWriter;
import httpserver.interfaces.IRouter;
import httpserver.interfaces.ISocketHandler;
import httpserver.models.Request;
import httpserver.models.Response;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketHandler implements ISocketHandler {
    ServerSocket serverSocket;
    Socket clientSocket;
    IRequestParser requestParser;
    IResponseWriter responseWriter;
    IRouter router;

    public SocketHandler(IRequestParser requestParser, IResponseWriter responseWriter, IRouter router){
        this.requestParser = requestParser;
        this.responseWriter = responseWriter;
        this.router = router;
    }

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


            Request request = processClientRequests(clientSocket.getInputStream());
            Response response = processServerResponse(request);
            sendResponse(response, clientSocket.getOutputStream());
            close();
        }
    }

    public Request processClientRequests(InputStream clientInputStream) throws IOException {
        Request request = requestParser.parse(clientInputStream);
        return request;
    }

    public Response processServerResponse(Request request) {
        Response response;
        String[] methods = router.getMethods(request);

        if (router.isPathValid(request) && router.isMethodValid(request, methods)) {
            response = responseWriter.buildSuccessResponse(request, methods);
            return response;
        }

        if (router.isPathValid(request) && !(router.isMethodValid(request, methods))) {
            response = responseWriter.buildMethodNotAllowedResponse(request, methods);
            return response;
        }

        response = responseWriter.buildPageNotFoundResponse(request);
        return response;
    }

    public void sendResponse(Response response, OutputStream outputStream) throws IOException {
        String formattedResponse = responseWriter.formatResponse(response);
        outputStream.write(formattedResponse.getBytes());
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
