package httpserver.handlers;

import httpserver.interfaces.IReader;
import httpserver.interfaces.IRequestParser;
import httpserver.interfaces.IResponseWriter;
import httpserver.interfaces.IRouter;
import httpserver.models.Request;
import httpserver.models.Response;
import httpserver.request.RequestReader;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private IRequestParser requestParser;
    private IResponseWriter responseWriter;
    private IRouter router;
    private IReader reader;

    ClientHandler(Socket clientSocket, IRequestParser requestParser, IResponseWriter responseWriter, IRouter router) {
        this.clientSocket = clientSocket;
        this.requestParser = requestParser;
        this.responseWriter = responseWriter;
        this.router = router;
    }

    @Override
    public void run() {
        try {
            reader = new RequestReader(clientSocket.getInputStream());
            Request request = requestParser.parse(reader);
            Response response = responseWriter.getResponse(request, router);
            responseWriter.sendResponse(response, clientSocket.getOutputStream());
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() throws IOException {
        clientSocket.close();
        reader.close();
    }
}
