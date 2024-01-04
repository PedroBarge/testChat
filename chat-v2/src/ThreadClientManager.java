import java.net.Socket;

public class ThreadClientManager extends Thread {
    private final Socket clientSocket;

    public ThreadClientManager(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        super.run();
    }
}
