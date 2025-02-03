import java.util.Random;

public class SimplexProtocol {

    // Simulates the Sender
    static class Sender {
        String data;
        Receiver receiver;

        public Sender(String data, Receiver receiver) {
            this.data = data;
            this.receiver = receiver;
        }

        public void sendData() {
            System.out.println("Sender: Sending data: " + data);
            receiver.receiveData(data);
        }
    }

    // Simulates the Receiver
    static class Receiver {
        static final double ERROR_RATE = 0.1; // 10% chance of error
        Acknowledgment ackSender;

        public Receiver(Acknowledgment ackSender) {
            this.ackSender = ackSender;
        }

        public void receiveData(String data) {
            System.out.println("Receiver: Received data: " + data);
            // Simulate a noisy channel with errors
            if (isErrorOccurred()) {
                System.out.println("Receiver: Error detected. Sending negative acknowledgment.");
                ackSender.sendAck(false); // Negative acknowledgment (error)
            } else {
                System.out.println("Receiver: Data received correctly. Sending positive acknowledgment.");
                ackSender.sendAck(true);  // Positive acknowledgment (no error)
            }
        }

        // Simulate error with a random chance
        private boolean isErrorOccurred() {
            return new Random().nextDouble() < ERROR_RATE;
        }
    }

    // Simulates Acknowledgment process
    static class Acknowledgment {
        Sender sender;

        public Acknowledgment(Sender sender) {
            this.sender = sender;
        }

        public void sendAck(boolean isAckReceived) {
            if (isAckReceived) {
                System.out.println("Sender: Acknowledgment received. Data transmission successful.");
            } else {
                System.out.println("Sender: Negative acknowledgment. Retransmitting data.");
                sender.sendData(); // Retransmit data on failure
            }
        }
    }

    public static void main(String[] args) {
        // Initialize components
        Acknowledgment ackSender = new Acknowledgment(null);
        Receiver receiver = new Receiver(ackSender);
        Sender sender = new Sender("Hello, this is a test message!", receiver);
        
        // Set up the acknowledgment after sender is created
        ackSender = new Acknowledgment(sender);

        // Start sending data
        sender.sendData();
    }
}
