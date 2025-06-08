package MongoDB;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class MongoDB {
    public static void main(String[] args) {
        // 1. Connect to MongoDB
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        // 2. Get database
        MongoDatabase database = mongoClient.getDatabase("test");

        // 3. Get/Create collection
        MongoCollection<Document> collection = database.getCollection("students");

        // 4. Insert Document
        Document student = new Document("name", "Alice")
                .append("age", 22)
                .append("course", "Computer Science");
        collection.insertOne(student);
        System.out.println("Inserted: " + student.toJson());

        // 5. Read Document
        Document foundStudent = collection.find(Filters.eq("name", "Alice")).first();
        System.out.println("Found: " + foundStudent);

        // 6. Update Document
        collection.updateOne(Filters.eq("name", "Alice"), Updates.set("age", 23));
        Document updatedStudent = collection.find(Filters.eq("name", "Alice")).first();
        System.out.println("Updated: " + updatedStudent);

        // 7. Delete Document
        collection.deleteOne(Filters.eq("name", "Alice"));
        System.out.println("Deleted student named Alice");

        // Close connection
        mongoClient.close();
    }
}
