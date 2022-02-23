package httpserver.server;

import httpserver.handlers.SocketHandler;
import httpserver.request.RequestBuilder;
import httpserver.request.RequestParser;
import httpserver.response.ResponseWriter;
import httpserver.router.Router;

public class  RunServer {
    public static void main(String[] args) {
        int port = 5000;

        RequestBuilder requestBuilder = new RequestBuilder();
        RequestParser requestParser = new RequestParser(requestBuilder);
        ResponseWriter responseWriter = new ResponseWriter();
        Router router = new Router();
        SocketHandler socketHandler = new SocketHandler(requestParser, responseWriter, router);
        Server server = new Server(socketHandler);
        server.start(port);
    }
}
