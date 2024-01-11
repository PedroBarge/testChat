import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;

    public Server() {
    }

//    public void start() {
//        try {
//            serverSocket = new ServerSocket(8666);
//            System.out.println("Success " + serverSocket.getInetAddress() + serverSocket.getLocalPort());
//
//            while (true) {
//                System.out.println("Waiting for clients...");
//                Socket clientSocket = serverSocket.accept();
                    //Thread handleClient = new Thread(() -> handleClient(serverSocket))
//                ClientManager clientManager = new ClientManager(clientSocket);
//                clientManager.run();
//
//                if (clientManager.thereIsUser()) {
//                    clientSocket.close();
//                    closeServer();
//
//                    break;
//                }
//            }
//
//        } catch (IOException e) {
//            System.out.println(e);
//            e.printStackTrace();
//        }
//    }

    public void start() {
        try {
            serverSocket = new ServerSocket(8666);
            System.out.println("Success " + serverSocket.getInetAddress() + serverSocket.getLocalPort());

            while (true) {
                System.out.println("Waiting for clients...");
                Socket clientSocket = serverSocket.accept();

                // Cria uma nova thread para lidar com o cliente
                new Thread(() -> {
                    ClientManager clientManager = new ClientManager(clientSocket);
                    clientManager.run();

                    if (clientManager.thereIsUser()) {
                        try {
                            clientSocket.close();
                            closeServer();
                            clientManager.interrupt();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
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
