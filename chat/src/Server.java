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
        this.serverSocket = new ServerSocket(portNumber);
        this.newConnection = this.serverSocket.accept();
        System.out.println("Client connected");
        InetAddress clientAddress = this.newConnection.getInetAddress();
        String adressUser = clientAddress.getHostAddress();
        int portUser = this.newConnection.getLocalPort();
        incomeInfoFromUser(adressUser, portUser);
        String info = "IP: " + adressUser + " Port: " + portUser;

        try {
            FileWriter fw = new FileWriter("src/bd/logs.txt", true);
            fw.write(info + "\n");
            fw.close();
        } catch (IOException var7) {
            System.out.println("An error occurred writting to the DB.");
            var7.printStackTrace();
        }

        this.textOut = new PrintWriter(this.newConnection.getOutputStream(), true);
        this.textIn = new BufferedReader(new InputStreamReader(this.newConnection.getInputStream()));
        this.runServer();
    }

    private void runServer() throws IOException {
        boolean isRuning = true;
        this.textOut.println("You managed to connect to the server!");
        this.textOut.println("Here are some tips:");
        this.textOut.println("=> If you write \"hello server\" it responds to you.\n=> If you write something in the terminal it sends it to the server.\n=> When you type \"exit\" or \"quit\" it ends the connection.\n");

        String msgIncome;
        String adressUser;
        int portUser;
        for (; isRuning; incomeInfoFromUser(msgIncome, adressUser, portUser)) {
            this.textOut.println();
            this.textOut.print("Server: ");
            this.textOut.println("Write something");
            msgIncome = this.textIn.readLine();
            InetAddress clientAddress = this.newConnection.getInetAddress();
            adressUser = clientAddress.getHostAddress();
            portUser = this.newConnection.getLocalPort();
            if (msgIncome.contains("hello server")) {
                this.textOut.print("Server: ");
                this.textOut.println("hello client");
            }

            if (msgIncome.contains("quit") || msgIncome.contains("exit")) {
                this.textOut.println("Exiting...");
                this.stop();
                isRuning = false;
            }

            if (!msgIncome.contains("hello server") && !msgIncome.contains("quit")) {
                this.textOut.print("Server: ");
                this.textOut.println("Listening...");
            }
        }

    }

    private static void incomeInfoFromUser(String msgIncome, String adressUser, int portUser) {
        System.out.println("User said: " + msgIncome);
        System.out.println("IP: " + adressUser);
        System.out.println("Port: " + portUser);
        System.out.println();
    }

    private static void incomeInfoFromUser(String adressUser, int portUser) {
        System.out.println("IP: " + adressUser);
        System.out.println("Port: " + portUser);
        System.out.println();
    }

    private void stop() throws IOException {
        this.textIn.close();
        this.textOut.close();
        this.newConnection.close();
        this.serverSocket.close();
    }
}
