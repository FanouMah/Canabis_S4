/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;

/**
 * @author PC
 */
import java.sql.*;

public class Database {
    private String typeSql;
    private String jdbcUrl;
    private String username;
    private String password;
    private Connection connection;

    public Database(String typeSql) throws Exception {
        setTypeSql(typeSql);
    }

    public void openConnection(String username, String password, String projectName) throws Exception {
        try {
            generateConnection(this.typeSql, projectName);
            setUsername(username);
            setPassword(password);
            setConnection(DriverManager.getConnection(this.jdbcUrl, this.username, this.password));
        } 
        catch (Exception e) {
            throw e;
        }
    }

    private void generateConnection(String typeSql, String projectName) throws Exception{
        if ("oracle".equals(typeSql)) {
            setJdbcUrl("jdbc:oracle:thin:@localhost:1521:orcl");
            Class.forName("oracle.jdbc.OracleDriver");
        }
        else if ("postgresql".equals(typeSql)){
            setJdbcUrl("jdbc:postgresql://localhost:5432/"+projectName);
            Class.forName("org.postgresql.Driver");
        }
        else if ("mysql".equals(typeSql)){
            setJdbcUrl("jdbc:mysql://localhost:3306/"+projectName);
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        else{
            throw new Exception("Type de SQL non reconnu");
        }
    }

    public void closeConnection() throws Exception{
        try {
            if (this.connection != null) {
            this.connection.close();   
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void setTypeSql(String typeSql) {
        this.typeSql = typeSql;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
}
