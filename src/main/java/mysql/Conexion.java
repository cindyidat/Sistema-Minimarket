package mysql;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    Connection cn;

    public Connection conectar() {

        try {

            Class.forName("com.mysql.jdbc.Driver");

            String url =
                    "jdbc:mysql://localhost:3306/bd_minimarket";

            String user = "root";
            String pass = "";

            cn = DriverManager.getConnection(
                    url,
                    user,
                    pass);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return cn;
    }
}