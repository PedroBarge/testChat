import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

public class ClientManager implements Runnable {
    private final Socket clientSocket;

    private final HashMap<String, InetAddress> ipToNameList = new HashMap<String, InetAddress>();

    public ClientManager(Socket socketClient) {
        this.clientSocket = socketClient;
    }

    @Override
    public void run() {
        try {
            PrintWriter textOut = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader textIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            textOut.println("Client connected.");
            InetAddress clientAddress = clientSocket.getInetAddress();
            String adressUser = clientAddress.getHostAddress();
            int portUser = clientSocket.getLocalPort();
            textOut.println("Please insert name: ");
            String nameClient = textIn.readLine();
            String info = "Name: " + nameClient + " IP: " + adressUser + " Port: " + portUser;
            ipToNameList.put(nameClient, clientAddress);

            System.out.println(info);
            try {
                FileWriter fw = new FileWriter("src/DataBases/logsClients.txt", true);
                fw.write("New connection" + "\n");
                fw.write(info + "\n");
                fw.close();
            } catch (IOException error) {
                System.out.println("An error occurred writting to the DB.");
                error.printStackTrace();
            }

            welcomeText(textOut);
            boolean isRuning = true;
            clientIsOnTheServer(isRuning, textIn, textOut);

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clientIsOnTheServer(boolean isRuning, BufferedReader textIn, PrintWriter textOut) throws IOException {
        while (isRuning) {
            textOut.println("Write here: ");
            String msgIncome = textIn.readLine();
            textOut.println();
            if (msgIncome.contains("hello server")) {
                textOut.print("Server: ");
                textOut.println("hello client");
            }

            if (msgIncome.contains("quit") || msgIncome.contains("exit")) {
                textOut.println("Exiting...");
                isRuning = false;
                stop();
            }

            if (!msgIncome.contains("hello server") && !msgIncome.contains("quit")) {
                textOut.print("Server: ");
                textOut.println("Listening...");
            }
            textOut.println();
        }
    }

    private void welcomeText(PrintWriter textOut) {
        textOut.println("You managed to connect to the server!");
        textOut.println("Here are some tips:");
        textOut.println("=> If you write \"hello server\" it responds to you.\n=> If you write something in the terminal it sends it to the server.\n=> When you type \"exit\" or \"quit\" it ends the connection.\n");
    }


    private void stop() throws IOException {
        clientSocket.close();
    }
}
