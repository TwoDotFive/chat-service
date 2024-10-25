package xyz.fanpool.chat_service.common.config.mongo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(@Value("${spring.data.mongodb.uri}") String uri) {
        return new SimpleMongoClientDatabaseFactory(uri);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }
}