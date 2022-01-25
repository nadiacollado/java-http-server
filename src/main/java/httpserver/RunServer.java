package httpserver;

public class RunServer {
    public static void main(String[] args) {
        int port = 5000;

        ServerSocketWrapper socketWrapper = new ServerSocketWrapper();
        Server server = new Server(socketWrapper);
        server.start(port);
    }
}
