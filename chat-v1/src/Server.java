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

    private void start() throws IOException {

        serverSocket = new ServerSocket(8666);
        System.out.println("Sucess " + serverSocket);
        System.out.println("Waiting for clients...");
        int clt = 0;
        while (isOn) {
            Socket clientSocket = serverSocket.accept();
            ClientManager clientManager = new ClientManager(clientSocket);
            clt++;
            Thread threadClientManager = new Thread(clientManager);
            threadClientManager.start();
            // Adicionar list
            menu();
        }
    }

    public void menu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (isOn) {
            System.out.println("Menu");
            System.out.println("1-Start\n2-Check Logs\n0-Exist");
            String option = scanner.next();
            switch (option) {
                case "1":
                    start();
                    break;
                case "2":
                    checkAllLogs();
                    break;
                case "0":
                    isOn = false;
                    break;
                default:
                    System.out.println("Miss click?");
            }
        }
    }

    private void checkAllLogs() {
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream("src/DataBases/logsClients.txt");

            String temp = readFromInputStream(fileInputStream);
            System.out.println(temp);

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
