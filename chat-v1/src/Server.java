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
            System.out.println("1- Start\n2- See all the logs\n0- Exit");
            String input = scanner.next();
            switch (input) {
                case "1":
                    scanner.reset();
                    start();
                    break;
                case "2":
                    writeLogs();
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
        int numOfClientsOnline = 0; //Fazer isto em lista
        boolean isOn = true;
        serverSocket = new ServerSocket(8666);
        System.out.println("Success " + serverSocket.getInetAddress() + serverSocket.getLocalPort());
        System.out.println("Waiting for clients...");

        while (isOn) {
            Socket clientSocket = serverSocket.accept();
            numOfClientsOnline++;
            ClientManager clientManager = new ClientManager(clientSocket, numOfClientsOnline);
            clientManager.run();
            if (!clientManager.thereIsUser()) {
                isOn = false;
            }
        }
    }

    private void writeLogs() {
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream("src/DataBases/logsClients.txt");

            String logs = readFromInputStream(fileInputStream);
            System.out.println(logs);

            fileInputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private static String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
