import java.io.*;
import java.net.*;

public class Server {
    private ServerSocket serverSocket;
    private Socket newConnection;
    private PrintWriter textOut;
    private BufferedReader textIn;

    public Server() {
    }

    public void startServer(int portNumber) throws IOException {


        serverSocket = new ServerSocket(portNumber);
        newConnection = serverSocket.accept();

        Client client = new Client();
        client.newClient(newConnection);

        textOut = new PrintWriter(newConnection.getOutputStream(), true);
        textIn = new BufferedReader(new InputStreamReader(newConnection.getInputStream()));
        runServer();
    }

    private void runServer() throws IOException {
        boolean isRuning = true;
        msgPositive();

        String msgIncome;
        String adressUser;
        int portUser;
        for (; isRuning; incomeInfoFromUser(msgIncome, adressUser, portUser)) {
            textOut.println();
            textOut.print("Server: ");
            textOut.println("Write something");
            msgIncome = textIn.readLine();
            InetAddress clientAddress = newConnection.getInetAddress();
            adressUser = clientAddress.getHostAddress();
            portUser = newConnection.getLocalPort();

            isRuning = textResponseToUser(msgIncome, isRuning, adressUser, portUser);
        }

    }

    private void msgPositive() {
        textOut.println("You managed to connect to the server!");
        textOut.println("Here are some tips:");
        textOut.println("=> If you write \"hello server\" it responds to you.\n=> If you write something in the terminal it sends it to the server.\n=> When you type \"exit\" or \"quit\" it ends the connection.\n");
    }

    private boolean textResponseToUser(String msgIncome, boolean isRuning, String adressUser, int portUser) throws IOException {
        if (msgIncome.contains("hello server")) {
            textOut.print("Server: ");
            textOut.println("hello client");
        }

        if (msgIncome.contains("quit") || msgIncome.contains("exit")) {
            textOut.println("Exiting...");
            stop();
            isRuning = false;
        }

        if (msgIncome.contains("info")) {
            textOut.println("IP: " + adressUser);
            textOut.println("Port: " + portUser);
        }

        if (!msgIncome.contains("hello server") && !msgIncome.contains("quit")) {
            textOut.print("Server: ");
            textOut.println("Listening...");
        }
        return isRuning;
    }

    private void incomeInfoFromUser(String msgIncome, String adressUser, int portUser) {
        System.out.println("User said: " + msgIncome);
        System.out.println("IP: " + adressUser);
        System.out.println("Port: " + portUser);
        System.out.println();
    }


    private void stop() throws IOException {
        textIn.close();
        textOut.close();
        newConnection.close();
        serverSocket.close();
    }
}
