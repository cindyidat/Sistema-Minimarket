package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bean.BeanContrato;

public class Sql_Contrato {

    Conexion con = new Conexion();

    // LISTAR HISTORIAL DE CONTRATOS DE UN TRABAJADOR

    public ArrayList<BeanContrato> listarContratosPorUsuario(int idUsuario) {

        ArrayList<BeanContrato> lista =
                new ArrayList<BeanContrato>();

        try {

            Connection cn =
                    con.conectar();

            String sql =
                    "SELECT * FROM contrato "
                    + "WHERE idUsuario=? "
                    + "ORDER BY fechaInicio DESC";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setInt(1, idUsuario);

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()) {

                BeanContrato c =
                        new BeanContrato();

                c.setIdContrato(
                        rs.getInt("idContrato"));

                c.setIdUsuario(
                        rs.getInt("idUsuario"));

                c.setTipoContrato(
                        rs.getString("tipoContrato"));

                c.setSueldo(
                        rs.getDouble("sueldo"));

                c.setFechaInicio(
                        rs.getString("fechaInicio"));

                c.setFechaFin(
                        rs.getString("fechaFin"));

                c.setEstado(
                        rs.getString("estado"));

                lista.add(c);
            }

            rs.close();
            ps.close();
            cn.close();

        } catch(Exception e) {

            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR CONTRATO VIGENTE DE UN TRABAJADOR

    public BeanContrato buscarContratoVigente(int idUsuario) {

        BeanContrato c = null;

        try {

            Connection cn =
                    con.conectar();

            String sql =
                    "SELECT * FROM contrato "
                    + "WHERE idUsuario=? "
                    + "AND estado='VIGENTE' "
                    + "LIMIT 1";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setInt(1, idUsuario);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                c = new BeanContrato();

                c.setIdContrato(
                        rs.getInt("idContrato"));

                c.setIdUsuario(
                        rs.getInt("idUsuario"));

                c.setTipoContrato(
                        rs.getString("tipoContrato"));

                c.setSueldo(
                        rs.getDouble("sueldo"));

                c.setFechaInicio(
                        rs.getString("fechaInicio"));

                c.setFechaFin(
                        rs.getString("fechaFin"));

                c.setEstado(
                        rs.getString("estado"));
            }

            rs.close();
            ps.close();
            cn.close();

        } catch(Exception e) {

            e.printStackTrace();
        }

        return c;
    }

    // CERRAR CONTRATO VIGENTE (lo marca como FINALIZADO)

    public void cerrarContratoVigente(int idUsuario, String fechaFin) {

        try {

            Connection cn =
                    con.conectar();

            String sql =
                    "UPDATE contrato SET "
                    + "estado='FINALIZADO', "
                    + "fechaFin=? "
                    + "WHERE idUsuario=? "
                    + "AND estado='VIGENTE'";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setString(1, fechaFin);
            ps.setInt(2, idUsuario);

            ps.executeUpdate();

            ps.close();
            cn.close();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    // REGISTRAR NUEVO CONTRATO (cierra el anterior y crea uno nuevo VIGENTE)

    public void registrarContrato(BeanContrato c) {

        try {

            // 1. Cerrar el contrato vigente anterior (si existe)
            cerrarContratoVigente(
                    c.getIdUsuario(),
                    c.getFechaInicio());

            // 2. Insertar el nuevo contrato como VIGENTE
            Connection cn =
                    con.conectar();

            String sql =
                    "INSERT INTO contrato("
                    + "idUsuario,"
                    + "tipoContrato,"
                    + "sueldo,"
                    + "fechaInicio,"
                    + "estado)"
                    + " VALUES(?,?,?,?,'VIGENTE')";

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setInt(1, c.getIdUsuario());
            ps.setString(2, c.getTipoContrato());
            ps.setDouble(3, c.getSueldo());
            ps.setString(4, c.getFechaInicio());

            ps.executeUpdate();

            ps.close();
            cn.close();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
}