package Conexion;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import Conexion.Conexion;


public class AuthBD {

    public String autUserStr(String user, String pass) {

        String result = "Error";

        Connection conn = null;
        Conexion conex = new Conexion();
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dato = "jdbc:mysql://" + conex.getHost() + ":" + conex.getPort() + "/" + conex.getDbName();

            conn = DriverManager.getConnection(dato, user, pass);

            if (conn != null) {
                result = "ok";
            }

        } catch (ClassNotFoundException e) {
            return result;
        } catch (SQLException e) {
            return result;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (Exception e2) {
                return result;
            }
        }
        return result;
    }
}
