package anagrafica.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoGridFsConfig {
    @Value("${spring.data.mongodb.uri}")
    private String connString;

    @Value("${spring.data.mongodb.database}")
    private String dbName;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(connString);
    }


    @Bean
    public MongoDatabase mongoDatabase(MongoClient client) {
        return client.getDatabase(dbName);
    }

    @Bean
    public GridFSBucket gridFSBucket(MongoDatabase database) {
        return GridFSBuckets.create(database);
    }
}
