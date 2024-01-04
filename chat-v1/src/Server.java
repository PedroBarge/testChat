import java.io.*;
import java.net.*;

public class Server {
    private ServerSocket serverSocket;
    private PrintWriter textOut;
    private BufferedReader textIn;

    public Server() {
    }

    public void start() throws IOException {
        int onlineClients = 0;
        boolean isOn = true;
        serverSocket = new ServerSocket(8666);
        System.out.println("Success " + serverSocket);
        System.out.println("Waiting for clients...");

        while (isOn) {
            Socket clientSocket = serverSocket.accept();
            ClientManager clientManager = new ClientManager(clientSocket);
            onlineClients++;
            Thread threadClientManager = new Thread(clientManager);
            System.out.println(onlineClients);
            threadClientManager.start();
        }
    }
}
