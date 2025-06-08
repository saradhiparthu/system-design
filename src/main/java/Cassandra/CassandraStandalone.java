package Cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;

import java.net.InetSocketAddress;
import java.util.UUID;

public class CassandraStandalone {
    public static void main(String[] args) {
        try (CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("127.0.0.1", 9042))
                .withLocalDatacenter("datacenter1") // Check your cassandra.yaml for the datacenter name
                .build()) {

            // Create keyspace
            session.execute("CREATE KEYSPACE IF NOT EXISTS testks " +
                    "WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1}");

            // Use keyspace
            session.execute("USE testks");

            // Create table
            session.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id UUID PRIMARY KEY, name TEXT, age INT)");

            // Insert data
            UUID userId = UUID.randomUUID();
            session.execute(SimpleStatement.builder("INSERT INTO users (id, name, age) VALUES (?, ?, ?)")
                    .addPositionalValues(userId, "Alice", 30)
                    .build());

            // Query data
            ResultSet rs = session.execute("SELECT * FROM users");
            for (Row row : rs) {
                System.out.printf("User: id=%s, name=%s, age=%d%n",
                        row.getUuid("id"), row.getString("name"), row.getInt("age"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
