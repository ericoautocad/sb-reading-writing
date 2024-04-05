package com.pagonext.sbreadingwriting.config;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.pagonext.sbreadingwriting.domain.repository.secondary.TripChosenRepository;

@Configuration
@EnableMongoRepositories(basePackageClasses = TripChosenRepository.class, mongoTemplateRef = "secondaryMongoTemplate")
@EnableConfigurationProperties
public class SecondaryConfig {

    @Bean(name = "secondaryProperties")
    @ConfigurationProperties(prefix = "spring.data.mongodb.secondary")
    public MongoProperties secondaryProperties() {
        return new MongoProperties();
    }

    @Bean(name = "secondaryMongoClient")
    public MongoClient mongoClient(@Qualifier("secondaryProperties") MongoProperties mongoProperties) {

        // MongoCredential credential = MongoCredential.createCredential(mongoProperties.getUsername(),
        //         mongoProperties.getAuthenticationDatabase(), mongoProperties.getPassword());

        // return MongoClients.create(MongoClientSettings.builder()
        //         .applyToClusterSettings(builder -> builder
        //                 .hosts(singletonList(new ServerAddress(mongoProperties.getHost(), mongoProperties.getPort()))))
        //         .credential(credential)
        //         .build());

        String connectionString = MessageFormat.format("mongodb://{0}:{1}@{2}:{3}/",
        mongoProperties.getUsername(), new String( mongoProperties.getPassword()), mongoProperties.getHost(), mongoProperties.getPort().toString());

        return MongoClients.create(connectionString);
    }

    @Bean(name = "secondaryMongoDBFactory")
    public MongoDatabaseFactory mongoDatabaseFactory(@Qualifier("secondaryMongoClient") MongoClient mongoClient,
            @Qualifier("secondaryProperties") MongoProperties mongoProperties) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase());
    }

    @Bean(name = "secondaryMongoTemplate")
    public MongoTemplate mongoTemplate(
            @Qualifier("secondaryMongoDBFactory") MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }
}
