import java.net.*;

public class UDPServer {
    public static void main(String[] args) throws Exception {
        int port = 5000;
        DatagramSocket socket = new DatagramSocket(port);  // Create a socket on port 5000
        byte[] receiveData = new byte[1024];  // Buffer to hold received data

        System.out.println("Server started. Waiting for client...");

        while (true) {
            // Receive packet from client
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);  // Receive message from client

            String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Client: " + clientMessage);

            // Prepare server response
            String responseMessage = "Server: " + clientMessage;  // Echo back the received message

            // Send response back to client
            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();
            DatagramPacket sendPacket = new DatagramPacket(responseMessage.getBytes(), responseMessage.length(), clientAddress, clientPort);
            socket.send(sendPacket);  // Send response
        }
    }
}
