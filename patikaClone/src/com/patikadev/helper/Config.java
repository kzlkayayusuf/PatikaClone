package com.patikadev.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static Properties getDbUserNameAndPassword(){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(".env");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        Properties properties = new Properties();
        try {
            properties.load(fileInputStream);
        } catch (
                IOException e) {
            System.out.println(e.getMessage());
        }
        return properties;
    }

    public static final String PROJECT_TITLE="Patika.Dev";
    public static final String DB_URL= "jdbc:mysql://localhost:3306/course?useSSL=false&amp&serverTimezone=UTC";
    public static final String DB_USER_NAME= getDbUserNameAndPassword().getProperty("DB_USERNAME");
    public static final String DB_USER_PASSWORD=getDbUserNameAndPassword().getProperty("DB_PASSWORD");

}
