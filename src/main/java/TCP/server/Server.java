package TCP.server;

import java.io.*;
import java.net.*;

/*
 Core Java Client-Server implementation using Socket Programming. This example demonstrates a basic
 TCP communication model where the server listens for client requests and responds accordingly.
 */
public class Server {
    public static void main(String[] args) {
        final int PORT = 8080;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Handle client communication in a separate thread
                new Thread(() -> handleClient(clientSocket)).start();
            }

        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String receivedMessage;

            while ((receivedMessage = input.readLine()) != null) {
                System.out.println("Received from client: " + receivedMessage);

                // Send response
                output.println("Server Response: " + receivedMessage.toUpperCase());

                // Exit condition
                if ("exit".equalsIgnoreCase(receivedMessage)) {
                    System.out.println("Client disconnected.");
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Error communicating with client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Failed to close client socket: " + e.getMessage());
            }
        }
    }
}
