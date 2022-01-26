package httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class IO {
    static PrintWriter writer;
    static BufferedReader reader;


    public static void buildIOStream(Socket clientSocket) throws IOException {
        createWriter(clientSocket);
        createReader(clientSocket);
    }

    public static PrintWriter createWriter(Socket clientSocket) throws IOException {
        writer = new PrintWriter(clientSocket.getOutputStream(), true);
        return writer;
    }

    public static BufferedReader createReader(Socket clientSocket) throws IOException {
        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        return reader;
    }

    public static String receiveData() {
        try {
            String clientInput = reader.readLine();
            Utils.print("Client: " + clientInput);
            return clientInput;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void sendData(String data) {
        writer.println(data);
    }

    public static void echo() throws IOException {
        String clientData;
        while ((clientData = receiveData()) != null) {
            if (Utils.quit(clientData)) {
                close();
            }
            sendData(clientData);
        }
    }

    public static void close() throws IOException {
        reader.close();
        writer.close();
    }
}
