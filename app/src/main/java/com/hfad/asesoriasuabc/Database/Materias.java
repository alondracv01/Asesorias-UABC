package com.hfad.asesoriasuabc.Database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class Materias {

    public static void insertMateriaAlumno(MongoCollection<Document> collection, Document alumno) {
        collection.insertOne(alumno);
        System.out.println("Se ha insertado un documento");
    }

    public static void insertMateriaAlumnos(MongoCollection<Document> collection, List<Document> newDocuments) {
        collection.insertMany(newDocuments, new InsertManyOptions().ordered(false));
        System.out.println("se han insertado "+newDocuments.size()+" documentos");
    }

    //READ OPERATIONS
    public static void findDocuments(MongoCollection<Document> collection, Bson query){
        List<Document> findUsingParameter = collection.find(query).into(new ArrayList<>());
        if(findUsingParameter.isEmpty()) {
            System.out.println("No se encontraron documentos");
        }else{
            System.out.println("Documentos encontrados:");
            for (Document doc : findUsingParameter) {
                System.out.println(doc.toJson());
            }
        }
    }

    public static void findOneDocument(MongoCollection<Document> collection, Bson query){
        Document doc = collection.find(query).first();
        if(doc==null){
            System.out.println("No se encontro ningun documento");
        } else{
            System.out.println("FindOne: " + doc.toJson());
        }
    }

    //UPDATE OPERATIONS
    public static void updateMateriasAlumno(MongoCollection<Document> collection, Bson query, Bson newData){
        UpdateResult updateResult=collection.updateOne(query, newData);
        System.out.println("Documentos modificados"+updateResult.getModifiedCount());
    }

    public static void updateManyDocuments(MongoCollection<Document> collection,  Bson query, Bson newData){
        UpdateResult updateResult=collection.updateMany(query,newData);
        System.out.println("Documentos modificados"+updateResult.getModifiedCount());
    }

    //DELETE OPERATIONS
    public static void deleteOneDocument(MongoCollection<Document> collection, Bson query){
        DeleteResult result = collection.deleteOne(query);
        System.out.println("Documentos eliminados"+result.getDeletedCount());
    }

    public static void deleteDocuments(MongoCollection<Document> collection, Bson query){
        DeleteResult result = collection.deleteMany(query);
        System.out.println("Documentos eliminados"+result.getDeletedCount());
    }
}

