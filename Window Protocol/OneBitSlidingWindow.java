import java.util.Random;

public class OneBitSlidingWindow {
    static class Sender {
        private final String[] data;
        private Receiver receiver;
        private int currentPacketIndex = 0;

        public Sender(String[] data, Receiver receiver) {
            this.data = data;
            this.receiver = receiver;
        }

        public void sendData() {
            while (currentPacketIndex < data.length) {
                System.out.println("Sender: Sending packet " + currentPacketIndex + ": " + data[currentPacketIndex]);
                receiver.receiveData(data[currentPacketIndex], currentPacketIndex);
                currentPacketIndex++;
            }
        }
    }

    static class Receiver {
        private Acknowledgment ackSender;

        public Receiver(Acknowledgment ackSender) {
            this.ackSender = ackSender;
        }

        public void receiveData(String data, int packetIndex) {
            System.out.println("Receiver: Received packet " + packetIndex + ": " + data);
            boolean isError = new Random().nextDouble() < 0.1; // 10% chance of error
            if (isError) {
                System.out.println("Receiver: Error detected, packet " + packetIndex + " is corrupted.");
                ackSender.sendAck(packetIndex, false);
            } else {
                System.out.println("Receiver: Packet " + packetIndex + " received successfully.");
                ackSender.sendAck(packetIndex, true);
            }
        }
    }

    static class Acknowledgment {
        private Sender sender;
        private int lastAckReceived = -1;

        public Acknowledgment(Sender sender) {
            this.sender = sender;
        }

        public void sendAck(int packetIndex, boolean isAckReceived) {
            if (isAckReceived) {
                System.out.println("Sender: Acknowledgment for packet " + packetIndex + " received.");
            } else {
                System.out.println("Sender: Negative acknowledgment for packet " + packetIndex + " received. Retransmitting.");
                sender.sendData(); // Retransmit the packet if acknowledgment failed
            }
        }
    }

    public static void main(String[] args) {
        String[] data = {"Packet 1", "Packet 2", "Packet 3"};
        Acknowledgment ackSender = new Acknowledgment(null);
        Receiver receiver = new Receiver(ackSender);
        Sender sender = new Sender(data, receiver);

        ackSender = new Acknowledgment(sender); // Initialize acknowledgment
        sender.sendData();
    }
}
