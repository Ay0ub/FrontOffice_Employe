package org.dao;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class ConnexionMBD {
    private static final   String host = "localhost";
    private static final  int port = 27017;
    private static MongoClient mongoClient = null;
    private static DB mongoDatabase = null;


    private ConnexionMBD(String dbName) {
        mongoClient = new MongoClient(host,port);
        mongoDatabase =  mongoClient.getDB(dbName) ;
    }


    public static DB mdbConnexion(String dbname)
    {
        if (mongoClient == null)
            new ConnexionMBD(dbname);
        return mongoDatabase;
    }
}
