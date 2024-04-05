package com.pagonext.sbreadingwriting.config;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.pagonext.sbreadingwriting.domain.repository.primary.TripRepository;

@Configuration
@EnableMongoRepositories(basePackageClasses = TripRepository.class, mongoTemplateRef = "primaryMongoTemplate")
@EnableConfigurationProperties
public class PrimaryConfig {

    @Bean(name = "primaryProperties")
    @ConfigurationProperties(prefix = "spring.data.mongodb.primary")
    @Primary
    public MongoProperties primaryProperties() {
        return new MongoProperties();
    }

    @Bean(name = "primaryMongoClient")
    @Primary
    public MongoClient mongoClient(@Qualifier("primaryProperties") MongoProperties mongoProperties) {


        // MongoCredential credential = MongoCredential.createCredential(mongoProperties.getUsername(),
        //         mongoProperties.getDatabase(), mongoProperties.getPassword().toString().toCharArray());

        // return MongoClients.create(MongoClientSettings.builder()
        //         .applyToClusterSettings(builder->builder
        //                 .hosts(singletonList(new ServerAddress(mongoProperties.getHost(), mongoProperties.getPort()))))
        //         .credential(credential)
        //         .build());

        String connectionString = MessageFormat.format("mongodb://{0}:{1}@{2}:{3}/",
        mongoProperties.getUsername(), new String( mongoProperties.getPassword()), mongoProperties.getHost(), mongoProperties.getPort().toString());

        return MongoClients.create(connectionString);

    }

    @Primary
    @Bean(name = "primaryMongoDBFactory")
    public MongoDatabaseFactory mongoDatabaseFactory(@Qualifier("primaryMongoClient") MongoClient mongoClient,
            @Qualifier("primaryProperties") MongoProperties mongoProperties) {
                
                
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase());
    }

    @Primary
    @Bean(name = "primaryMongoTemplate")
    public MongoTemplate mongoTemplate(@Qualifier("primaryMongoDBFactory") MongoDatabaseFactory mongoDatabaseFactory) {

        return new MongoTemplate(mongoDatabaseFactory);
    }
}
