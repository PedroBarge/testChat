import java.io.*;
import java.net.*;

public class Server {
    private ServerSocket serverSocket;
    private PrintWriter textOut;
    private BufferedReader textIn;

    public Server() {
    }

    public void start() throws IOException {
        boolean isOn = true;
        serverSocket = new ServerSocket(8666);
        System.out.println("Sucess " + serverSocket);
        System.out.println(" Waiting for clients...");

        while (isOn){
            Socket clientSocket =serverSocket.accept();
            ClientManager clientManager = new ClientManager(clientSocket);
            Thread threadClientManager = new Thread(clientManager);
            threadClientManager.start();
        }
    }
}
