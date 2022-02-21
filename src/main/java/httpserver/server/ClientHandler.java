package httpserver.server;

import httpserver.interfaces.IRequestParser;
import httpserver.interfaces.IResponseWriter;
import httpserver.interfaces.IRouter;
import httpserver.models.Request;
import httpserver.models.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket clientSocket;
    IRequestParser requestParser;
    IResponseWriter responseWriter;
    IRouter router;

    ClientHandler(Socket clientSocket, IRequestParser requestParser, IResponseWriter responseWriter, IRouter router) {
        this.clientSocket = clientSocket;
        this.requestParser = requestParser;
        this.responseWriter = responseWriter;
        this.router = router;
    }

    @Override
    public void run() {
        Request request = null;
        Response response = null;
        try {
            request = processClientRequests(clientSocket.getInputStream());
            response = processServerResponse(request);
            sendResponse(response, clientSocket.getOutputStream());
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Request processClientRequests(InputStream clientInputStream) throws IOException {
        Request request = requestParser.parse(clientInputStream);
        return request;
    }

    public Response processServerResponse(Request request) throws IOException {
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

    public void close() throws IOException {
        clientSocket.close();
    }
}
