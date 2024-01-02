import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public void newClient(Socket newConnection) {
        System.out.println("Client connected");
        InetAddress clientAddress = newConnection.getInetAddress();
        String adressUser = clientAddress.getHostAddress();
        int portUser = newConnection.getLocalPort();
        incomeInfoFromUser(adressUser, portUser);
        String info = "IP: " + adressUser + " Port: " + portUser;

        try {
            FileWriter fw = new FileWriter("src/bd/logs.txt", true);
            fw.write(info + "\n");
            fw.close();
        } catch (IOException error) {
            System.out.println("An error occurred writting to the DB.");
            error.printStackTrace();
        }
    }

    private static void incomeInfoFromUser(String adressUser, int portUser) {
        System.out.println("IP: " + adressUser);
        System.out.println("Port: " + portUser);
        System.out.println();
    }
}
