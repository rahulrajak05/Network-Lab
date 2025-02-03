import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SimplexStopAndWaitProtocol {
    
    // Sender class
    static class Sender {
        private BlockingQueue<String> sendQueue;
        private BlockingQueue<String> ackQueue;

        public Sender(BlockingQueue<String> sendQueue, BlockingQueue<String> ackQueue) {
            this.sendQueue = sendQueue;
            this.ackQueue = ackQueue;
        }

        public void send(String message) throws InterruptedException {
            System.out.println("Sender: Sending message: " + message);
            sendQueue.put(message); // Send message
            String ack = ackQueue.take(); // Wait for ACK
            System.out.println("Sender: Received ACK: " + ack);
        }
    }

    // Receiver class
    static class Receiver {
        private BlockingQueue<String> sendQueue;
        private BlockingQueue<String> ackQueue;

        public Receiver(BlockingQueue<String> sendQueue, BlockingQueue<String> ackQueue) {
            this.sendQueue = sendQueue;
            this.ackQueue = ackQueue;
        }

        public void receive() throws InterruptedException {
            while (true) {
                String message = sendQueue.take(); // Wait for message
                System.out.println("Receiver: Received message: " + message);
                // Simulate processing the message
                System.out.println("Receiver: Acknowledging message: " + message);
                ackQueue.put("ACK for: " + message); // Send acknowledgment
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Create blocking queues to simulate message transmission
        BlockingQueue<String> sendQueue = new ArrayBlockingQueue<>(1);
        BlockingQueue<String> ackQueue = new ArrayBlockingQueue<>(1);

        // Create sender and receiver
        Sender sender = new Sender(sendQueue, ackQueue);
        Receiver receiver = new Receiver(sendQueue, ackQueue);

        // Start receiver in a separate thread to listen for incoming messages
        Thread receiverThread = new Thread(() -> {
            try {
                receiver.receive();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        receiverThread.start();

        // Simulate the sender sending messages
        String[] messages = {"Message 1", "Message 2", "Message 3"};
        for (String message : messages) {
            sender.send(message);
            Thread.sleep(1000); // Simulate time delay between messages
        }

        // Let receiver finish before terminating
        receiverThread.interrupt();
    }
}
