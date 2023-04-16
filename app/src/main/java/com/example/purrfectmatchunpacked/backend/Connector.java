package com.example.purrfectmatchunpacked.backend;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Connector {
    private String URI = "mysql://root:3MtP5VHUK3GAtU0U2zgm@containers-us-west-30.railway.app:7768/railway";
    private String username = "root";
    private String password = "3MtP5VHUK3GAtU0U2zgm";
    private static Connector connector;
    private static Connection connection;
    private Connector() throws SQLException, ClassNotFoundException {
        connectToDB();
    }

    public static Connector getConnector() throws SQLException, ClassNotFoundException {
        if (connector == null) {
            connector = new Connector();
        } return connector;
    }

    private void connectToDB() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(URI, username, password);
    }

    public ResultSet query(String query) throws SQLException {
        Statement SQLStatement = connection.createStatement();
        return SQLStatement.executeQuery(query);
    }

    public void update (String query) throws SQLException {
        Statement SQLStatement = connection.createStatement();
        SQLStatement.executeUpdate(query);
    }

    public String format (String literal){
        return '"' + literal + "'";
    }
}
