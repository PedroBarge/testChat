import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;
    private PrintWriter textOut;
    private BufferedReader textIn;

    public Server() {
    }

    private void start() throws IOException {
        boolean isOn = true;
        serverSocket = new ServerSocket(8666);
        System.out.println("Sucess " + serverSocket);
        System.out.println("Waiting for clients...");

        while (isOn) {
            Socket clientSocket = serverSocket.accept();
            ClientManager clientManager = new ClientManager(clientSocket);
            Thread threadClientManager = new Thread(clientManager);
            threadClientManager.start();
        }
    }

    public void menu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean on = true;
        while (on) {
            System.out.println("Menu");
            System.out.println("1-Start\n2-Exist");
            String option = scanner.next();
            switch (option) {
                case "1":
                    start();
                    break;
                case "2":
                    on = false;
                    break;
                default:
                    System.out.println("Miss click?");
            }
        }
    }
}
