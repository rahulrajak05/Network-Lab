import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

// Frame class represents a data frame.
class Frame {
    private String data;

    public Frame(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Frame{data='" + data + "'}";
    }
}

// Sender class sends data frames to the receiver.
class Sender implements Runnable {
    private BlockingQueue<Frame> queue;

    public Sender(BlockingQueue<Frame> queue) {
        this.queue = queue;
    }

    public void sendFrame(Frame frame) {
        try {
            // Send the frame to the queue
            queue.put(frame);
            System.out.println("Sender: Sent frame -> " + frame);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Sender interrupted");
        }
    }

    @Override
    public void run() {
        // Simulating sending frames in an infinite loop
        for (int i = 1; i <= 5; i++) {
            sendFrame(new Frame("Data Frame " + i));
            try {
                Thread.sleep(1000);  // Delay to simulate time between sending frames
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

// Receiver class receives data frames from the sender.
class Receiver implements Runnable {
    private BlockingQueue<Frame> queue;

    public Receiver(BlockingQueue<Frame> queue) {
        this.queue = queue;
    }

    public void receiveFrame() {
        try {
            // Take the frame from the queue
            Frame frame = queue.take();
            System.out.println("Receiver: Received frame -> " + frame);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Receiver interrupted");
        }
    }

    @Override
    public void run() {
        // Continuously listen for incoming frames
        while (true) {
            receiveFrame();
        }
    }
}

// Main class that sets up the USP protocol communication.
public class UnrestrictedSimplexProtocol {
    public static void main(String[] args) {
        // Create a blocking queue for communication between sender and receiver
        BlockingQueue<Frame> queue = new ArrayBlockingQueue<>(10);

        // Create sender and receiver threads
        Sender sender = new Sender(queue);
        Receiver receiver = new Receiver(queue);

        Thread senderThread = new Thread(sender);
        Thread receiverThread = new Thread(receiver);

        // Start both threads
        senderThread.start();
        receiverThread.start();
    }
}
