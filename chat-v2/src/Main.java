import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Chat v2");
        menu();
    }

    private static void menu() throws IOException {
        boolean isOn = true;
        while (isOn) {
            System.out.println("Server Menu");
            System.out.println("+----------+");
            System.out.println("1- Start server\n" +
                    "2- Check Logs\n" +
                    "0- Exit");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.next();
            scanner.reset();
            switch (input) {
                case "1":
                    scanner.reset();
                    Server server = new Server();
                    server.startServer();
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
}