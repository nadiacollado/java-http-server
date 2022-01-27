package httpserver.server;

import httpserver.SocketHandler;
import httpserver.request.RequestParser;
import httpserver.response.ResponseWriter;

public class RunServer {
    public static void main(String[] args) {
        int port = 5000;

        RequestParser requestParser = new RequestParser();
        ResponseWriter responseWriter = new ResponseWriter();
        SocketHandler socketHandler = new SocketHandler(requestParser, responseWriter);
        Server server = new Server(socketHandler);
        server.start(port);
    }
}
