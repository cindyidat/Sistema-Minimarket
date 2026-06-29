package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.ResultSet;
import java.util.ArrayList;

import bean.BeanProveedor;

public class Sql_Proveedor {

    Conexion con = new Conexion();

    // LISTAR

    public ArrayList<BeanProveedor> listarProveedores() {

        ArrayList<BeanProveedor> lista =
                new ArrayList<BeanProveedor>();

        try {

            Connection cn =
                    con.conectar();

            String sql =
                    "SELECT * FROM proveedor";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()) {

                BeanProveedor p =
                        new BeanProveedor();

                p.setIdProveedor(
                        rs.getInt("idProveedor"));

                p.setRazonSocial(
                        rs.getString("razonSocial"));

                p.setRuc(
                        rs.getString("ruc"));

                p.setDireccion(
                        rs.getString("direccion"));

                p.setTelefono(
                        rs.getString("telefono"));

                lista.add(p);
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return lista;
    }

    // REGISTRAR

    public void registrarProveedor(
            BeanProveedor p) {

        try {

            Connection cn =
                    con.conectar();

            String sql =
            "INSERT INTO proveedor("
            + "razonSocial,"
            + "ruc,"
            + "direccion,"
            + "telefono)"
            + " VALUES(?,?,?,?)";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setString(1,
                    p.getRazonSocial());

            ps.setString(2,
                    p.getRuc());

            ps.setString(3,
                    p.getDireccion());

            ps.setString(4,
                    p.getTelefono());

            ps.executeUpdate();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    // BUSCAR

    public BeanProveedor buscarProveedor(
            int id) {

        BeanProveedor p =
                new BeanProveedor();

        try {

            Connection cn =
                    con.conectar();

            String sql =
                    "SELECT * FROM proveedor "
                    + "WHERE idProveedor=?";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                p.setIdProveedor(
                        rs.getInt("idProveedor"));

                p.setRazonSocial(
                        rs.getString("razonSocial"));

                p.setRuc(
                        rs.getString("ruc"));

                p.setDireccion(
                        rs.getString("direccion"));

                p.setTelefono(
                        rs.getString("telefono"));
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return p;
    }

    // ACTUALIZAR

    public void actualizarProveedor(
            BeanProveedor p) {

        try {

            Connection cn =
                    con.conectar();

            String sql =
            "UPDATE proveedor SET "
            + "razonSocial=?,"
            + "ruc=?,"
            + "direccion=?,"
            + "telefono=? "
            + "WHERE idProveedor=?";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setString(1,
                    p.getRazonSocial());

            ps.setString(2,
                    p.getRuc());

            ps.setString(3,
                    p.getDireccion());

            ps.setString(4,
                    p.getTelefono());

            ps.setInt(5,
                    p.getIdProveedor());

            ps.executeUpdate();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
 // ELIMINAR
 // Devuelve true si se eliminó correctamente.
 // Devuelve false si no se pudo eliminar porque el proveedor
 // tiene productos asociados (restricción de llave foránea).

 public boolean eliminarProveedor(
         int id) {

     boolean eliminado = false;

     try {

         Connection cn =
                 con.conectar();

         String sql =
                 "DELETE FROM proveedor "
                 + "WHERE idProveedor=?";

         PreparedStatement ps =
                 cn.prepareStatement(sql);

         ps.setInt(1, id);

         ps.executeUpdate();

         eliminado = true;

         ps.close();
         cn.close();

     } catch (SQLIntegrityConstraintViolationException e) {

         // El proveedor tiene productos asociados, no se puede eliminar.
         eliminado = false;

     } catch (Exception e) {

         e.printStackTrace();
         eliminado = false;
     }

     return eliminado;
 }
}