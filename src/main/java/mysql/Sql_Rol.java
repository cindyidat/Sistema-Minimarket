package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bean.BeanRol;

public class Sql_Rol {

    Conexion con = new Conexion();

    // LISTAR ROLES

    public ArrayList<BeanRol> listarRoles() {

        ArrayList<BeanRol> lista =
                new ArrayList<BeanRol>();

        try {

            Connection cn =
                    con.conectar();

            String sql =
                    "SELECT * FROM rol";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()) {

                BeanRol r =
                        new BeanRol();

                r.setIdRol(
                        rs.getInt("idRol"));

                r.setNombreRol(
                        rs.getString("nombreRol"));

                lista.add(r);
            }

            rs.close();
            ps.close();
            cn.close();

        } catch(Exception e) {

            e.printStackTrace();
        }

        return lista;
    }
}