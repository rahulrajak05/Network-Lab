import java.io.*;
import java.net.*;

public class UDPClient {
    public static void main(String[] args) throws IOException {
        String hostname = "localhost";
        int port = 12345;
        String message = "Hello, Server!";

        DatagramSocket socket = new DatagramSocket();
        byte[] buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(hostname), port);
        
        System.out.println("Sending: " + message);
        socket.send(packet);
        socket.close();
    }
}
