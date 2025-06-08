package TCP.client;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        final String SERVER_HOST = "localhost";
        final int SERVER_PORT = 8080;

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connected to server. Type messages to send. Type 'exit' to quit.");

            String message;
            while (true) {
                System.out.print("Client: ");
                message = userInput.readLine();
                output.println(message);

                if ("exit".equalsIgnoreCase(message)) {
                    System.out.println("Client exiting...");
                    break;
                }

                // Receive response from server
                String serverResponse = serverInput.readLine();
                System.out.println(serverResponse);
            }

        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
