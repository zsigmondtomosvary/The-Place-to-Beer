package at.ac.tuwien.sepm.assignment.individual.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.*;

public class ConnectDb {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static Connection connection = null;

    public static void main(String[] args) throws Exception {

        LOG.info("Create Database");

        Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:h2:~/sepm;INIT=RUNSCRIPT FROM 'classpath:sql/createAndInsert.sql'",
            "dbUser",
            "dbPassword"
        );
        connection.close();

    }

    private static class DatabaseManagerHolder{
        private static final ConnectDb INSTANCE = new ConnectDb();
    }

    public static ConnectDb getInstance(){
        return DatabaseManagerHolder.INSTANCE;
    }

    public static Connection getConnection() throws SQLException {

        if(connection == null){
            LOG.info("Creating connection");
            openConnection();
        }
        return connection;

    }

    private static void openConnection(){
        if(connection == null) {
            try {
                Class.forName("org.h2.Driver");
            } catch (Exception e) {
                LOG.info("Failed to load org.h2.Driver");
                return;
            }

            try {
                connection = DriverManager.getConnection(
                    "jdbc:h2:~/sepm",
                    "dbUser",
                    "dbPassword");
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                LOG.info("Failed to set connection!");
                return;
            }
        }
        LOG.info("Connection set!");

    }

    public static void closeConnection(){
        if(connection != null) {
            LOG.info("Closing connection");
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.info("An error occoured while closing the connection: " + e.getMessage());
            }
        }
    }

}
