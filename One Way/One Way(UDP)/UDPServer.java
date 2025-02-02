import java.io.*;
import java.net.*;

public class UDPServer {
    public static void main(String[] args) throws IOException {
        int port = 12345;

        DatagramSocket socket = new DatagramSocket(port);
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        System.out.println("Server is listening on port " + port);
        socket.receive(packet); // Receive a packet from the client

        String message = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Received: " + message);
        
        socket.close();
    }
}
