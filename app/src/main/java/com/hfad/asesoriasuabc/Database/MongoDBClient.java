package com.hfad.asesoriasuabc.Database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class MongoDBClient {
    String uri = "mongodb://localhost:27017";
    String dbName="Asesorias";

    MongoClient mongoClient;
    MongoDatabase database;

    public MongoDBClient(){
        mongoClient = MongoClients.create(uri);
        database = mongoClient.getDatabase(dbName);
    }

    public MongoDatabase getDatabase(){
        return database;
    }

    public MongoCollection<Document> getCollection(String dbCollection){
        return database.getCollection(dbCollection);
    }
}
