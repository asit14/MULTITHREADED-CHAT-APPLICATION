import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connected to the chat server.");

            // Thread to listen for messages from the server
            new Thread(() -> {
                String serverMsg;
                try {
                    while ((serverMsg = in.readLine()) != null) {
                        System.out.println("Server: " + serverMsg);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            }).start();

            // Main thread to send user input
            String input;
            while ((input = userInput.readLine()) != null) {
                out.println(input);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
