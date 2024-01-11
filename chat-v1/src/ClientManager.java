import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClientManager extends Thread {
    private final Socket clientSocket;

    public ClientManager(Socket socketClient) {
        this.clientSocket = socketClient;
    }

    int numUser = 0;

    @Override
    public void run() {
        try {
            PrintWriter textOut = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader textIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            textOut.println("Client connected.");
            InetAddress clientAddress = clientSocket.getInetAddress();
            String adressUser = clientAddress.getHostAddress();
            int portUser = clientSocket.getLocalPort();

            textOut.println("Insert name");
            String name = textIn.readLine();
            String info = "IP: " + adressUser + " Port: " + portUser;

            System.out.println("Name: " + name + "\n" + info);
            try {
                FileWriter fw = new FileWriter("src/DataBases/logsClients.txt", true);
                fw.write("New connection" + "\n");
                fw.write(info + "\n");
                fw.close();
            } catch (IOException error) {
                System.out.println("An error occurred writting to the DB.");
                System.out.println(error);
                error.printStackTrace();
            }

            numUser++;
            welcomeText(textOut);

            boolean isRuning = true;
            clientIsOnTheServer(isRuning, textIn, textOut, name);
            //stop();
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    private void welcomeText(PrintWriter textOut) {
        textOut.println("You managed to connect to the server!");

        textOut.println("Here are some tips:");
        textOut.println("=> If you write \"hello server\" it responds to you.\n=> If you write something in the terminal it sends it to the server.\n=> When you type \"exit\" or \"quit\" it ends the connection.\n");
    }

    private void clientIsOnTheServer(boolean isRuning, BufferedReader textIn, PrintWriter textOut, String adressUser) throws IOException {

        while (isRuning) {
            textOut.println("Write here: ");
            String msgIncome = textIn.readLine();
            if (msgIncome.contains("hello server")) {
                textOut.print("Server: ");
                textOut.println("hello client");
            }

            if (msgIncome.contains("quit") || msgIncome.contains("exit")) {
                textOut.println("Exiting...");
                numUser--;
                thereIsUser();
                clientSocket.close();
                isRuning = false;
            }

            if (!msgIncome.contains("hello server") && !msgIncome.contains("quit")) {
                textOut.print("Server: ");
                textOut.println("Listening...");
            }
            textOut.println();
            incomeInfoFromUser(msgIncome, adressUser);
            System.out.println("Is connected? " + clientSocket.isConnected());
            System.out.println("Is closed? " + clientSocket.isClosed());
        }
    }

    private void incomeInfoFromUser(String msgIncome, String adressUser) {
        System.out.println("User Name: " + adressUser);
        System.out.println("User said: " + msgIncome);
        System.out.println();
    }

    public boolean thereIsUser() {
        if (clientSocket.isClosed() && numUser == 0) {
            return true;
        }
        return false;
    }
}
