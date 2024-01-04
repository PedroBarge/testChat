import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public Server() {
    }

    public void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Server on");

        boolean serverOn=true;

        while (serverOn){
            Socket socket = serverSocket.accept();
            if (socket.isConnected()) {
                System.out.println("client on");
                ThreadClientManager threadClientManager = new ThreadClientManager(socket);
                threadClientManager.run();
            }
        }
    }
}
