import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) throws IOException {
        String serverIP = "localhost";
        int port = 5000;

        try (Socket socket = new Socket(serverIP, port)) {
            System.out.println("Connected to the server.");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while(true){ 
                System.out.print("Enter your message: ");
                message = consoleReader.readLine(); 
                if (message==null){
                    break;
                }
                out.println(message);
                System.out.println(in.readLine());
            }
            socket.close();
        }
    }
}
