package com.sp23.koifishproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.sp23.koifishproject.repository.mongo")
public class MongoConfig {
}
