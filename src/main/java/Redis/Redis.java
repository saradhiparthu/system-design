package Redis;

import redis.clients.jedis.Jedis;

public class Redis {
    public static void main(String[] args) {
        try {
            // Connect to Redis server on localhost
            Jedis jedis = new Jedis("localhost", 6379);

            // Check if the connection is established
            System.out.println("Connection to server successful");

            // Set a key-value pair in Redis
            jedis.set("username", "john_doe");

            // Get the value for the key "username"
            String username = jedis.get("username");

            // Print the value
            System.out.println("Stored username in Redis: " + username);

            // Set an expiration time of 10 seconds for the key "username"
            jedis.expire("username", 10);

            // Wait for a while to observe expiration
            try {
                Thread.sleep(11000); // sleep for 11 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Try to get the value after expiration
            String expiredUsername = jedis.get("username");

            if (expiredUsername == null) {
                System.out.println("The key 'username' has expired.");
            }

            // Close the connection
            jedis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
