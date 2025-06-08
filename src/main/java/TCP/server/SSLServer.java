package TCP.server;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

public class SSLServer {
    public static void main(String[] args) {
        final int PORT = 8443;

        try {
            // Load the keystore with the server certificate
            KeyStore keyStore = KeyStore.getInstance("JKS");
            try (FileInputStream keyStoreStream = new FileInputStream("src/main/resources/server_keystore.jks")) {
                keyStore.load(keyStoreStream, "pulivarthi".toCharArray());
            }

            // Initialize the KeyManagerFactory with the keystore
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, "pulivarthi".toCharArray());

            // Create SSL Context
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

            // Create SSL Server Socket
            SSLServerSocketFactory serverSocketFactory = sslContext.getServerSocketFactory();
            SSLServerSocket serverSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(PORT);

            System.out.println("SSL Server started on port " + PORT);

            while (true) {
                try (SSLSocket socket = (SSLSocket) serverSocket.accept();
                     BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                    System.out.println("Client connected: " + socket.getInetAddress());

                    String receivedMessage;
                    while ((receivedMessage = input.readLine()) != null) {
                        System.out.println("Received from client: " + receivedMessage);
                        output.println("Server Response: " + receivedMessage.toUpperCase());

                        if ("exit".equalsIgnoreCase(receivedMessage)) {
                            System.out.println("Client disconnected.");
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
