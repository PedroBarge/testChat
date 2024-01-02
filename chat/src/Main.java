import java.io.IOException;

public class Main {
    public Main() {
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Chat");
        Server server = new Server();
        server.startServer(8666);
    }
}