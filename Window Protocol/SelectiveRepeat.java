import java.util.ArrayList;
import java.util.List;

public class SelectiveRepeat {
    static class Sender {
        private final String[] data;
        private Receiver receiver;
        private int windowSize = 4;
        private int nextSeqNum = 0;

        public Sender(String[] data, Receiver receiver) {
            this.data = data;
            this.receiver = receiver;
        }

        public void sendData() {
            while (nextSeqNum < data.length) {
                for (int i = nextSeqNum; i < Math.min(nextSeqNum + windowSize, data.length); i++) {
                    System.out.println("Sender: Sending packet " + i + ": " + data[i]);
                    receiver.receiveData(data[i], i);
                }
                receiver.receiveAck();
            }
        }

        public void slideWindow(List<Integer> ackedPackets) {
            for (int ack : ackedPackets) {
                System.out.println("Sender: Acknowledgment received for packet " + ack);
            }
            nextSeqNum = ackedPackets.get(ackedPackets.size() - 1) + 1;
            System.out.println("Sender: Sliding window to start from packet " + nextSeqNum);
        }
    }

    static class Receiver {
        private final Acknowledgment ackSender;
        private List<Integer> receivedPackets = new ArrayList<>();

        public Receiver(Acknowledgment ackSender) {
            this.ackSender = ackSender;
        }

        public void receiveData(String data, int packetIndex) {
            System.out.println("Receiver: Received packet " + packetIndex + ": " + data);
            boolean isError = new Random().nextDouble() < 0.1; // 10% chance of error
            if (isError) {
                System.out.println("Receiver: Error detected in packet " + packetIndex);
                ackSender.sendAck(packetIndex, false);
            } else {
                System.out.println("Receiver: Successfully received packet " + packetIndex);
                receivedPackets.add(packetIndex);
                ackSender.sendAck(packetIndex, true);
            }
        }

        public void receiveAck() {
            List<Integer> ackedPackets = new ArrayList<>(receivedPackets);
            receivedPackets.clear();
            ackSender.sendAck(ackedPackets);
        }
    }

    static class Acknowledgment {
        private final Sender sender;

        public Acknowledgment(Sender sender) {
            this.sender = sender;
        }

        public void sendAck(List<Integer> ackedPackets) {
            sender.slideWindow(ackedPackets);
        }

        public void sendAck(int packetIndex, boolean isAckReceived) {
            if (isAckReceived) {
                System.out.println("Sender: Acknowledgment for packet " + packetIndex + " received.");
            } else {
                System.out.println("Sender: Negative acknowledgment for packet " + packetIndex + ". Retransmitting.");
                sender.sendData();
            }
        }
    }

    public static void main(String[] args) {
        String[] data = {"Packet 1", "Packet 2", "Packet 3", "Packet 4", "Packet 5"};
        Acknowledgment ackSender = new Acknowledgment(null);
        Receiver receiver = new Receiver(ackSender);
        Sender sender = new Sender(data, receiver);

        ackSender = new Acknowledgment(sender); // Initialize acknowledgment
        sender.sendData();
    }
}
