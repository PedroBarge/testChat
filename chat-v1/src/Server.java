import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;

    public Server() {
    }

    boolean foda = true;

    public void start() {
        try {
            serverSocket = new ServerSocket(8666);
            System.out.println("Success " + serverSocket.getInetAddress() + serverSocket.getLocalPort());

            while (true) {
                System.out.println("Waiting for clients...");
                Socket clientSocket = serverSocket.accept();
                ClientManager clientManager = new ClientManager(clientSocket);
                clientManager.run();

                if (clientManager.thereIsUser()) {
                    clientSocket.close();
                    closeServer();
                    clientManager.interrupt();
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    private void closeServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("Server closed.");
            }
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
