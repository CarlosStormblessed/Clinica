package Conexion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private String host;
    private String port;
    private String userName;
    private String dbName;
    private String password;

    /**
     * Creates a new instance of Conexion
     */
    public Conexion() {
        
    }
    
    
    
    private void getCredenciales(){
        Properties properties= new Properties();
    try {
      properties.load(new FileInputStream(new File("./Configuracion/DBconfig.properties")));
      this.host = properties.getProperty("HOST");
      this.port = properties.getProperty("PORT");
      this.userName = properties.getProperty("USERNAME");
      this.dbName = properties.getProperty("DBNAME");
      this.password = properties.getProperty("PASSWORD");
      
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    }

    /**
     * Metodo que se conecta a la base de datos
     *
     * @return
     * @throws ConnectException
     * @throws SQLException
     */
    public Connection connect() throws ConnectException, SQLException {
        getCredenciales();
        Connection conn;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.dbName;
            conn = DriverManager.getConnection(url, this.userName, this.password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
        return conn;
    }

    /**
     * Metodo que se desconecta de la base de datos
     *
     * @param conn
     */
    public void disconnect(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Metodos gets y sets
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
