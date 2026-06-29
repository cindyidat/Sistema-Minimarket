package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bean.BeanCategoria;

public class Sql_Categoria {

    Conexion con = new Conexion();

    // LISTAR CATEGORIAS

    public ArrayList<BeanCategoria> listarCategorias() {

        ArrayList<BeanCategoria> lista =
                new ArrayList<BeanCategoria>();

        try {

            Connection cn =
                    con.conectar();

            String sql =
                    "SELECT * FROM categoria";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()) {

                BeanCategoria c =
                        new BeanCategoria();

                c.setIdCategoria(
                        rs.getInt("idCategoria"));

                c.setNombreCategoria(
                        rs.getString("nombreCategoria"));

                lista.add(c);
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR CATEGORIA

    public BeanCategoria buscarCategoria(
            int id) {

        BeanCategoria c =
                new BeanCategoria();

        try {

            Connection cn =
                    con.conectar();

            String sql =
                    "SELECT * FROM categoria "
                    + "WHERE idCategoria=?";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                c.setIdCategoria(
                        rs.getInt("idCategoria"));

                c.setNombreCategoria(
                        rs.getString("nombreCategoria"));
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return c;
    }

    // REGISTRAR

    public void registrarCategoria(
            BeanCategoria c) {

        try {

            Connection cn =
                    con.conectar();

            String sql =
                    "INSERT INTO categoria(nombreCategoria) "
                    + "VALUES(?)";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setString(
                    1,
                    c.getNombreCategoria());

            ps.executeUpdate();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    // ACTUALIZAR

    public void actualizarCategoria(
            BeanCategoria c) {

        try {

            Connection cn =
                    con.conectar();

            String sql =
                    "UPDATE categoria "
                    + "SET nombreCategoria=? "
                    + "WHERE idCategoria=?";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setString(
                    1,
                    c.getNombreCategoria());

            ps.setInt(
                    2,
                    c.getIdCategoria());

            ps.executeUpdate();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    // ELIMINAR

    public void eliminarCategoria(
            int id) {

        try {

            Connection cn =
                    con.conectar();

            String sql =
                    "DELETE FROM categoria "
                    + "WHERE idCategoria=?";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

}