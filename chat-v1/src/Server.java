import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;

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
                    isOn = false;
                    scanner.reset();
                    break;
                default:
                    System.out.println("Miss click");
            }
            scanner.reset();
        }
    }

    private void start() throws IOException {
        boolean isOn = true;
        serverSocket = new ServerSocket(8666);
        System.out.println("Success " + serverSocket);
        System.out.println("Waiting for clients...");

        while (isOn) {
            Socket clientSocket = serverSocket.accept();
            ClientManager clientManager = new ClientManager(clientSocket);
            Thread threadClientManager = new Thread(clientManager);
            threadClientManager.start();
        }
    }

}
