import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        int port = 5000;
        
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Waiting for a client...");
        
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            String received, serverMessage;
            while ((received = in.readLine()) != null) {
                System.out.println("Client: " + received);
                
                System.out.print("Enter your message: ");
                serverMessage = consoleReader.readLine();
            
                out.println("Server: " + serverMessage);
            }
            clientSocket.close();
        }
    }
}
