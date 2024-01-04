import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;
    private PrintWriter textOut;
    private BufferedReader textIn;

    public Server() {
    }

    boolean isOn = true;

    public void menu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (isOn) {
            System.out.println("Menu");
            System.out.println("1- Start\n0- Exit");
            String input = scanner.next();
            switch (input) {
                case "1":
                    start();
                    scanner.reset();
                    break;
                case "0":
                    isOn=false;
                    scanner.reset();
                    break;
                default:
                    System.out.println("Miss click");
            }
            scanner.reset();
        }
    }

    public void start() throws IOException {
        int onlineClients = 0;

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
