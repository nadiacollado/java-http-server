package httpserver.handlers;

import httpserver.interfaces.IReader;
import httpserver.interfaces.IRequestParser;
import httpserver.interfaces.IResponseWriter;
import httpserver.interfaces.IRouter;
import httpserver.models.Request;
import httpserver.models.Response;
import httpserver.request.RequestReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private InputStream input;
    private OutputStream output;
    private IRequestParser requestParser;
    private IResponseWriter responseWriter;
    private IRouter router;
    private IReader reader;

    ClientHandler(InputStream input, OutputStream output, IRequestParser requestParser, IResponseWriter responseWriter, IRouter router) {
        this.input = input;
        this.output = output;
        this.requestParser = requestParser;
        this.responseWriter = responseWriter;
        this.router = router;
    }

    public void run() {
        try {
            reader = new RequestReader(input);
            Request request = requestParser.parse(reader);
            if (request != null) {
                Response response = responseWriter.getResponse(request, router);
                responseWriter.sendResponse(response, output);
            }
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() throws IOException {
        reader.close();
    }
}
