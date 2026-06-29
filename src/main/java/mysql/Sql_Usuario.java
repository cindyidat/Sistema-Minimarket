package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bean.BeanUsuario;
import org.mindrot.jbcrypt.BCrypt;

public class Sql_Usuario {

    Conexion con = new Conexion();

    public BeanUsuario validarUsuario(
            String usuario,
            String clave) {

        BeanUsuario bean = null;

        try {

            Connection cn = con.conectar();

            String sql =
                    "SELECT * FROM usuario "
                    + "WHERE usuario=? "
                    + "AND estado='ACTIVO'";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setString(1, usuario);

            ResultSet rs = ps.executeQuery();

            if (rs.next() && BCrypt.checkpw(clave, rs.getString("clave"))) {

                bean = new BeanUsuario();
                bean.setIdUsuario(rs.getInt("idUsuario"));
                bean.setNombre(rs.getString("nombre"));
                bean.setDni(rs.getString("dni"));
                bean.setUsuario(rs.getString("usuario"));
                bean.setClave(rs.getString("clave"));
                bean.setIdRol(rs.getInt("idRol"));
                bean.setTelefono(rs.getString("telefono"));
                bean.setCorreo(rs.getString("correo"));
                bean.setEstado(rs.getString("estado"));
            }

             else {
                System.out.println("USUARIO NO ENCONTRADO EN BD");
            }

            rs.close();
            ps.close();
            cn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bean;
    }

    // LISTAR TODOS

    public ArrayList<BeanUsuario> listarUsuarios() {

        ArrayList<BeanUsuario> lista =
                new ArrayList<BeanUsuario>();

        try {

            Connection cn = con.conectar();

            String sql =
                    "SELECT u.*, r.nombreRol "
                    + "FROM usuario u "
                    + "INNER JOIN rol r "
                    + "ON u.idRol = r.idRol "
                    + "ORDER BY u.idUsuario ASC";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                BeanUsuario u = new BeanUsuario();

                u.setIdUsuario(
                        rs.getInt("idUsuario"));

                u.setNombre(
                        rs.getString("nombre"));

                u.setDni(
                        rs.getString("dni"));

                u.setUsuario(
                        rs.getString("usuario"));

                u.setClave(
                        rs.getString("clave"));

                u.setIdRol(
                        rs.getInt("idRol"));

                u.setNombreRol(
                        rs.getString("nombreRol"));

                u.setTelefono(
                        rs.getString("telefono"));

                u.setCorreo(
                        rs.getString("correo"));

                u.setEstado(
                        rs.getString("estado"));

                lista.add(u);
            }

            rs.close();
            ps.close();
            cn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR POR ID

    public BeanUsuario buscarUsuario(int id) {

        BeanUsuario u = new BeanUsuario();

        try {

            Connection cn = con.conectar();

            String sql =
                    "SELECT * FROM usuario "
                    + "WHERE idUsuario=?";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                u.setIdUsuario(
                        rs.getInt("idUsuario"));

                u.setNombre(
                        rs.getString("nombre"));

                u.setDni(
                        rs.getString("dni"));

                u.setUsuario(
                        rs.getString("usuario"));

                u.setClave(
                        rs.getString("clave"));

                u.setIdRol(
                        rs.getInt("idRol"));

                u.setTelefono(
                        rs.getString("telefono"));

                u.setCorreo(
                        rs.getString("correo"));

                u.setEstado(
                        rs.getString("estado"));
            }

            rs.close();
            ps.close();
            cn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return u;
    }

    // REGISTRAR

    public void registrarUsuario(BeanUsuario u) {

        try {

            Connection cn = con.conectar();

            String sql =
                    "INSERT INTO usuario("
                    + "nombre,"
                    + "dni,"
                    + "usuario,"
                    + "clave,"
                    + "idRol,"
                    + "telefono,"
                    + "correo,"
                    + "estado) VALUES(?,?,?,?,?,?,?,?)";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getDni());
            ps.setString(3, u.getUsuario());
            ps.setString(4, BCrypt.hashpw(u.getClave(), BCrypt.gensalt()));
            ps.setInt(5, u.getIdRol());
            ps.setString(6, u.getTelefono());
            ps.setString(7, u.getCorreo());
            ps.setString(8, u.getEstado());

            ps.executeUpdate();

            ps.close();
            cn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ACTUALIZAR (datos personales y de contacto, NO incluye clave)

    public void actualizarUsuario(BeanUsuario u) {

        try {

            Connection cn = con.conectar();

            String sql =
                    "UPDATE usuario SET "
                    + "nombre=?,"
                    + "dni=?,"
                    + "usuario=?,"
                    + "idRol=?,"
                    + "telefono=?,"
                    + "correo=?,"
                    + "estado=? "
                    + "WHERE idUsuario=?";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getDni());
            ps.setString(3, u.getUsuario());
            ps.setInt(4, u.getIdRol());
            ps.setString(5, u.getTelefono());
            ps.setString(6, u.getCorreo());
            ps.setString(7, u.getEstado());
            ps.setInt(8, u.getIdUsuario());

            ps.executeUpdate();

            ps.close();
            cn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 // CAMBIAR CLAVE (hasheada)

    public void cambiarClave(int idUsuario, String nuevaClave) {

        try {

            Connection cn = con.conectar();

            String sql =
                    "UPDATE usuario SET clave=? "
                    + "WHERE idUsuario=?";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setString(1, BCrypt.hashpw(nuevaClave, BCrypt.gensalt()));
            ps.setInt(2, idUsuario);

            ps.executeUpdate();

            ps.close();
            cn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 // ELIMINAR

    public void eliminarUsuario(int id) {

        try {

            Connection cn = con.conectar();

            String sql =
                    "DELETE FROM usuario "
                    + "WHERE idUsuario=?";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

            ps.close();
            cn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
 // VALIDAR PIN DE SUPERVISOR
 // Recibe el usuario (login) y el PIN ingresados en el modal.
 // Devuelve el BeanUsuario del supervisor si:
 //   1. El usuario existe y está ACTIVO
 //   2. Su idRol es Administrador (1) o Jefe Inventario (2)
 //   3. El PIN coincide
 // Devuelve null si cualquier condición falla (sin detallar cuál,
 // por seguridad: no se debe revelar si el usuario existe o no).

 public BeanUsuario validarPinSupervisor(
         String usuarioSupervisor,
         String pin) {

     BeanUsuario bean = null;

     try {

         Connection cn = con.conectar();

         // El PIN ya no se compara directamente en el SQL porque
         // esta guardado como hash BCrypt. Primero se trae el hash
         // guardado y luego se compara en Java con BCrypt.checkpw.
         String sql =
                 "SELECT * FROM usuario "
                 + "WHERE usuario=? "
                 + "AND estado='ACTIVO' "
                 + "AND idRol IN (1,2)";

         PreparedStatement ps =
                 cn.prepareStatement(sql);

         ps.setString(1, usuarioSupervisor);

         ResultSet rs = ps.executeQuery();

         if (rs.next()) {

             String pinHash =
                     rs.getString("pin");

             if (pinHash != null
                     && pin != null
                     && BCrypt.checkpw(pin, pinHash)) {

                 bean = new BeanUsuario();

                 bean.setIdUsuario(
                         rs.getInt("idUsuario"));

                 bean.setNombre(
                         rs.getString("nombre"));

                 bean.setUsuario(
                         rs.getString("usuario"));

                 bean.setIdRol(
                         rs.getInt("idRol"));

                 bean.setEstado(
                         rs.getString("estado"));
             }
         }

         rs.close();
         ps.close();
         cn.close();

     } catch (Exception e) {
         e.printStackTrace();
     }

     return bean;
 }
}