package com.pmb.paymybuddy.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class DatasourceConfig {
    Properties properties;
    FileInputStream inputStream;
    Logger logger = Logger.getLogger(DatasourceConfig.class.getName());
    public DatasourceConfig() {
        properties = new Properties();
        
        try {
            File file = new File("src/main/resources/application.properties");
            inputStream = new FileInputStream(file);
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection() {
        Connection con=null;
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:"+properties.getProperty("port")+"/"+
                    properties.getProperty("database"),
                    properties.getProperty("username"),
                    properties.getProperty("password"));
        } catch (SQLException e) {
           throw new RuntimeException(e);
        }
        return con;
    }
    public void closeConnection(Connection con)  {
        if(con!=null){
            try {
                con.close();
                logger.info("Closing DB connection");
            } catch (SQLException e) {
                logger.info("Error while closing connection");
            }
        }
    }
}
