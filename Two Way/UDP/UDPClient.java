import java.net.*;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) throws Exception {
        String serverIP = "localhost";  // Server's IP address
        int serverPort = 5000;  // Server's port
        DatagramSocket socket = new DatagramSocket();  // Create a socket to send and receive packets

        InetAddress serverAddress = InetAddress.getByName(serverIP);  // Get the server's IP address
        Scanner scanner = new Scanner(System.in);  // To read user input

        while (true) {
            System.out.print("Enter your message: ");
            String message = scanner.nextLine();  // Read the message from user input

            if ("exit".equalsIgnoreCase(message)) {
                break;  // Exit loop if user types "exit"
            }

            // Send message to server
            DatagramPacket sendPacket = new DatagramPacket(message.getBytes(), message.length(), serverAddress, serverPort);
            socket.send(sendPacket);

            // Receive response from server
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);  // Wait for server's response

            String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Server: " + serverResponse);  // Print server's response
        }

        socket.close();  // Close the socket when done
    }
}
