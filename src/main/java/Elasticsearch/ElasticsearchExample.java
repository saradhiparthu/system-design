package Elasticsearch;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ElasticsearchExample {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new org.apache.http.HttpHost("localhost", 9200, "http"))
        );

        // Index a document
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", "iPhone 15");
        jsonMap.put("price", 999);

        IndexRequest indexRequest = new IndexRequest("products")
                .id("1")
                .source(jsonMap);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println("Index Response: " + indexResponse.getResult());

        // Get the document
        GetRequest getRequest = new GetRequest("products", "1");
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        System.out.println("Document: " + getResponse.getSource());

        client.close();
    }
}
