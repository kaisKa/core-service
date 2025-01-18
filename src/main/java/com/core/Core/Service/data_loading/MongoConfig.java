package com.core.Core.Service.data_loading;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.core.Core.Service.data_loading")
public class MongoConfig {


        @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://admin:password@localhost:27017/configdb?authSource=admin");
//        MongoClientSettings settings = MongoClientSettings.builder()
//                .applyConnectionString(new ConnectionString("mongodb://admin:password@localhost:27017/configdb?authMechanism=SCRAM-SHA-1"))
//                .credential(MongoCredential.createScramSha1Credential("admin", "admin", "password".toCharArray()))
//                .build();
//        return MongoClients.create(settings);

    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "configdb");
    }
}
