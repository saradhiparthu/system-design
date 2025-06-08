package TCP.client;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

public class SSLClient {
    public static void main(String[] args) {
        final String SERVER_HOST = "localhost";
        final int SERVER_PORT = 8443;

        try {
            // Load the client's truststore
            KeyStore trustStore = KeyStore.getInstance("JKS");
            try (FileInputStream trustStoreStream = new FileInputStream("src/main/resources/client_truststore.jks")) {
                trustStore.load(trustStoreStream, "pulivarthi".toCharArray());
            }

            // Initialize TrustManagerFactory
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(trustStore);

            // Create SSL Context
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

            // Create SSL Socket
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            try (SSLSocket socket = (SSLSocket) socketFactory.createSocket(SERVER_HOST, SERVER_PORT);
                 BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
                 BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                System.out.println("Connected securely to server. Type 'exit' to quit.");

                String message;
                while (true) {
                    System.out.print("Client: ");
                    message = userInput.readLine();
                    output.println(message);

                    if ("exit".equalsIgnoreCase(message)) {
                        System.out.println("Client exiting...");
                        break;
                    }

                    String serverResponse = serverInput.readLine();
                    System.out.println(serverResponse);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
