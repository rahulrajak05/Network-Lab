import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) throws IOException {
        String hostname = "localhost";
        int port = 12345;
        String message = "Hello, Server!";

        try (Socket socket = new Socket(hostname, port)) {
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            System.out.println("Sending: " + message);
            writer.println(message);
        } 
    }
}
