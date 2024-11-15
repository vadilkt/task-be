package com.essai.taskmanagament.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "taskmanagement";
    }

    @Override
    public MongoCustomConversions customConversions(){
        return new MongoCustomConversions(Arrays.asList(
                new ZonedDateTimeWriteConverter(),
                new ZonedDateTimeReadConverter()
        ));
    }
}
