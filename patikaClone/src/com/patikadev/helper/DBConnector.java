package com.patikadev.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private Connection connect=null;

    private Connection connectDb(){
        try {
            this.connect= DriverManager.getConnection(Config.DB_URL,Config.DB_USER_NAME,Config.DB_USER_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return this.connect;
    }

    public static Connection getInstance(){
        DBConnector db= new DBConnector();
        return db.connectDb();
    }
}
